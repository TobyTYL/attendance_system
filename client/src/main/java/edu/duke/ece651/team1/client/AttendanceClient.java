package edu.duke.ece651.team1.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import edu.duke.ece651.team1.shared.AttendanceRecord;

import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;

public class AttendanceClient {
    private final AttendanceRecord record;
    BufferedReader inputReader;
    final PrintStream out;
    private RestTemplate restTemplate = new RestTemplate();;

    public AttendanceClient(AttendanceRecord record, BufferedReader inputReader, PrintStream out) {
        this.record = record;
        this.inputReader = inputReader;
        this.out = out;
    }

    public AttendanceClient(BufferedReader inputReader, PrintStream out) {
        this(new AttendanceRecord(LocalDate.now()), inputReader, out);
    }

    public String readStatusOption(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("End of input reached");
        }
        s = s.toUpperCase();
        if (!s.equals("A") && !s.equals("P") && !s.equals("T")) {
            throw new IllegalArgumentException("That action is invalid: it does not have the correct format.");
        }
        return s;
    }

    public Iterable<Student> getRoaster() {
        // RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<List<Student>>() {
        };
        String url = "http://vcm-37154.vm.duke.edu:8080/api/students/allStudents";
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
            url,
                HttpMethod.GET,
                null,
                responseType);
        List<Student> students = responseEntity.getBody();
        return students;
    }

    public void sendAttendanceRecord(AttendanceRecord record){
         String url = "http://vcm-37154.vm.duke.edu:8080/api/attendance/";
         ParameterizedTypeReference<AttendanceRecord> responseType = new ParameterizedTypeReference<AttendanceRecord>() {
        };
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordToJsonString = serializer.serialize(record);
         HttpEntity<String> requestEntity = new HttpEntity<>(recordToJsonString);
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if(response.getStatusCode()!=HttpStatus.OK){
            out.println(response.getBody());
        }
    }

    public void startAttendance() throws IOException {
        Iterable<Student> students = getRoaster();
        out.println("Welcome to take Attendance for session: " + record.getSessionDate());
        record.initializeFromRoaster(students);
        for (Student s : students) {
            out.println("Student Name: " + s.getDisPlayName());
            while (true) {
                try {
                    String statusOption = readStatusOption("Press 'P' to mark Present, 'A' to mark Absent:");
                    if (statusOption.equals("P")) {
                        record.markPresent(s);
                        out.println("You sucessfully marked " + s.getDisPlayName() + " to Present");
                        break;
                    } else if (statusOption.equals("A")) {
                        record.markAbsent(s);
                        out.println("You sucessfully marked " + s.getDisPlayName() + " to Absent");
                        break;
                    } else {
                        throw new IllegalArgumentException("That action is in valid here");
                    }
                } catch (IllegalArgumentException e) {
                    out.println("Please try again " + e.getMessage());
                }
            }
        }
       sendAttendanceRecord(record);
        out.println("Attendance marking completed.");
        out.println("Attendance records List blow:");
        out.println(record.displayAttendance());
    }
}
