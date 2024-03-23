package edu.duke.ece651.team1.client.controller;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.*;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceRecordExporter;
import edu.duke.ece651.team1.shared.AttendanceRecordExporterFactory;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.PublicKey;
import java.time.LocalDate;

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

    public void startAttendanceMenue() throws IOException {
        while (true) {
            try {
                attendanceView.showAttendanceManageOption();
                String option = attendanceView.readAttendanceOption();
                if (option.equals("take")) {
                    startAttendance();
                } else if (option.equals("modify")) {

                } else if (option.equals("export")) {
                    startExport();
                } else {
                    // back to main menue
                    return;
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for Attendance Management Menue");

            }

        }
    }

    private Iterable<Student> getRoaster() {
        // RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<List<Student>>() {
        };
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/allStudents";
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

    private HttpHeaders getSessionTokenHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        return headers;
    }

    private List<String> getRecordDates() {
        ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<List<String>>() {
        };
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record-dates";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                responseType);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to fetch record dates: " + response.getStatusCode());
        }
        return response.getBody();
    }

    private AttendanceRecord getAttendanceRecord(String sessionDate) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record/" + sessionDate;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(
                    "Failed to fetch record of " + sessionDate + " because" + response.getStatusCode());
        }
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        return serializer.deserialize(response.getBody());
    }

    private void sendAttendanceRecord(AttendanceRecord record) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record";
        ParameterizedTypeReference<AttendanceRecord> responseType = new ParameterizedTypeReference<AttendanceRecord>() {
        };
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordToJsonString = serializer.serialize(record);
        HttpEntity<String> requestEntity = new HttpEntity<>(recordToJsonString, getSessionTokenHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
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

    public void startExport() throws IOException {
        while (true) {
            List<String> dates = getRecordDates(); 
            String dateOrBack = attendanceView.readExportDateFromPrompt(dates);
            if ("back".equals(dateOrBack)) {
                return; 
            } else {
                while (true) {
                    //get record of that date
                    AttendanceRecord record = getAttendanceRecord(dateOrBack);
                    String formatOrBack = attendanceView.readFormtFromPrompt();
                    if ("back".equals(formatOrBack)) {
                        break;
                    } else {
                        AttendanceRecordExporterFactory factory = new AttendanceRecordExporterFactory();
                        String filePath = "src/data/";
                        String fileName = "Attendance-"+dateOrBack;
                        AttendanceRecordExporter exporter;
                        if(formatOrBack.equals("json")){
                            exporter = factory.createExporter("json");
                        }else if(formatOrBack.equals("xml")){
                            exporter = factory.createExporter("xml");
                        }else{
                            exporter = factory.createExporter("csv");
                        }  
                        exporter.exportToFile(record,fileName, filePath);
                        attendanceView.showExportSuccessMessage(fileName+"."+formatOrBack);
                        }
                        return; 
                    }
                }
            }
        }
}


