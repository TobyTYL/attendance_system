package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.model.utils.CSVHandler;
import edu.duke.ece651.team1.client.model.utils.EmailUtils;
import edu.duke.ece651.team1.client.model.utils.Relation;
import edu.duke.ece651.team1.client.model.utils.Tuple;
import edu.duke.ece651.team1.client.view.StudentView;
import edu.duke.ece651.team1.client.view.ViewUtils;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
/**
 * The StudentController class handles all student-related operations within the application.
 * It interfaces with the StudentView for presenting options and capturing input, and uses a RestTemplate to communicate with the server for data persistence.
 */
public class StudentController {
    // legal name, email, display name
    BufferedReader inputReader;
    final PrintStream out;
    StudentView studentView;
    RestTemplate restTemplate;
    private StudentReportController reportController;
    private NotificationController notificationController;
    int sectionId;
    int classId;
    String className;
    int studentId;
    /**
     * Constructs a StudentController with the specified input reader and output stream.
     * @param inputReader A BufferedReader to read user input.
     * @param out A PrintStream for outputting text to the console.
     */
    public StudentController(BufferedReader inputReader, PrintStream out, int sectionId, int classId, String className,int studentId) {
        this.inputReader = inputReader;
        this.out = out;
        this.studentView = new StudentView(inputReader, out);
        this.restTemplate = new RestTemplate();
        this.reportController = new StudentReportController(inputReader, out, sectionId, className, studentId);
        this.notificationController = new NotificationController(inputReader, out, classId, className, studentId);
       
    }
    /**
     * Launches the student menu and processes user choices for various student management operations.
     * @throws IOException If an input or output exception occurred.
     */
    public void startStudentMenu() throws IOException {
        while (true) {
            try {
                studentView.showStudentMenu();
                String option = studentView.readStudentOption();
                if (option.equals("notification")) {
                    notificationController.startNotificationMenue();
                } else if (option.equals("report")) {
                    reportController.startReportMenue();
                } 
                else {
                    return;
                } 
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for Student Management Menue");
            }
        }
    }


    
}