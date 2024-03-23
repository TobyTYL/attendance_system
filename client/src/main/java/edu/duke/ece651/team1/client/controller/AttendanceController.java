package edu.duke.ece651.team1.client.controller;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.*;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
public class AttendanceController {
    BufferedReader inputReader;
    final PrintStream out;
    AttendanceView attendanceView;
    RestTemplate restTemplate;
    public AttendanceController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.attendanceView = new AttendanceView(inputReader, out);
        this.restTemplate = new RestTemplate();
    }

    public void startAttendanceMenue(){
       while (true) {
        try{
            attendanceView.showAttendanceManageOption();
            String option = attendanceView.readAttendanceOption();
            if(option.equals("take")){
                startAttendance();
            }else if(option.equals("modify")){

            }else if(option.equals("export")){

            }else{
            //back to main menue
                return;
            }
        }catch(Exception e){

        }
           
       }
    }

    private Iterable<Student> getRoaster() {
        // RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<List<Student>>() {
        };
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() +"/api/students/allStudents";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
            url,
                HttpMethod.GET,
                requestEntity,
                responseType);
        List<Student> students = responseEntity.getBody();
        return students;
    }

    private HttpHeaders getSessionTokenHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        return headers;
    }


    private void sendAttendanceRecord(AttendanceRecord record){
         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()+"/api/attendance/record";
         ParameterizedTypeReference<AttendanceRecord> responseType = new ParameterizedTypeReference<AttendanceRecord>() {
        };
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordToJsonString = serializer.serialize(record);
         HttpEntity<String> requestEntity = new HttpEntity<>(recordToJsonString,getSessionTokenHeaders());
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if(response.getStatusCode()!=HttpStatus.OK){
            out.println(response.getBody());
        }
    }
    public void startAttendance() throws IOException {
        Iterable<Student> students = getRoaster();
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        attendanceView.showTakeAttendanceMenu(record.getSessionDate().toString());
        record.initializeFromRoaster(students);
        for (Student s : students) {
            while (true) {
                try {
                    String statusOption = attendanceView.promptForStudentAttendance(s.getDisPlayName());
                    if (statusOption.equals("P")) {
                        record.markPresent(s);
                        attendanceView.showMarkSuccessMessage(s.getDisPlayName(), "Present");
                        break;
                    } else if (statusOption.equals("A")) {
                        record.markAbsent(s);
                        attendanceView.showMarkSuccessMessage(s.getDisPlayName(), "Absent");
                        break;
                    } else {
                        throw new IllegalArgumentException("That Attendance mark option is in valid,");
                    }
                } catch (IllegalArgumentException e) {
                    out.println("Invalid option. Please select A for Absent or P for present.");
                    
                }
            }
        }
       sendAttendanceRecord(record);
       attendanceView.showAttenceFinishMessage(record);
       
    }

    public void fetchAttendance(){
        try{
            out.println("Enter the date for which you want to fetch attendance records (YYYY-MM-DD):");
            String dateInput = inputReader.readLine();
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/fetch?date=" + date;
            HttpHeaders headers = getSessionTokenHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            
            if(response.getStatusCode() == HttpStatus.OK) {
                // the server sends back a JSON string of attendance records
                String attendanceData = response.getBody();
                out.println("Attendance records for " + dateInput + ":");
                out.println(attendanceData); // You may want to format this or deserialize it to show in a user-friendly manner
            } else {
                out.println("Failed to get previous attendance records.");
            }
        }
        catch(Exception e){
            out.println("Error getting previous attendance records: " + e.getMessage());
        }
    }

    public void modifyAttendance(){
        try {
        out.println("Enter the student name you want to modify attendance:");
        String studentName = inputReader.readLine();
        out.println("Enter the new attendance status (Present/Absent/Tardy):");
        String statusInput = inputReader.readLine();
        AttendanceStatus status = AttendanceStatus.valueOf(statusInput.toUpperCase());

        HttpHeaders headers = getSessionTokenHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("studentName", studentName);
        map.add("status", status.toString());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/modify";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            out.println("Attendance updated successfully.");
        } else {
            out.println("Failed to update attendance.");
        }
    } catch (Exception e) {
        out.println("Error modifying attendance: " + e.getMessage());
    }
    }
   

}
