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
    /**
     * Constructs a StudentController with the specified input reader and output stream.
     * @param inputReader A BufferedReader to read user input.
     * @param out A PrintStream for outputting text to the console.
     */
    public StudentController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.studentView = new StudentView(inputReader, out);
        this.restTemplate = new RestTemplate();
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
                if (option.equals("add")) {
                    addStudent();
                } else if (option.equals("remove")) {
                    removeStudent();
                } else if (option.equals("load")) {
                    loadFromCSV();
                } else if (option.equals("edit")) {
                    editStudentDisplayName();
                    // todo editStudentDisplayName();
                } else {
                    //back to main meune
                    return;
                } 
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for Student Management Menue");
            }
        }
    }
    /**
     * Adds a new student based on user input. Checks for the existence of the student before adding.
     * @throws IOException If an input or output exception occurred.
     */
    private void addStudent() throws IOException {
        String studentName = studentView.readStudentName();
        String displayName = studentView.readStudentDisplayName();
        String email = studentView.readStudentEmail();
        Student newStudent = new Student(studentName, displayName, email);
        if(checkStudentExists(studentName)){
            out.println("Student Already exists!");
            return;
        }
        
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/student/"+studentName;
        HttpEntity<Student> requestEntity = new HttpEntity<>(newStudent, getSessionTokenHeaders());
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class);
        if(responseEntity.getStatusCode()==HttpStatus.OK){
            out.println("Student added successfully.");
        }else{
            out.println("Student added failed");
        }
       
    }
    /**
     * Checks if a student already exists in the system.
     * @param studentName The name of the student to check.
     * @return True if the student exists, false otherwise.
     */
    public boolean checkStudentExists(String studentName){
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
        + "/api/students/student/exists/"+studentName;
        HttpEntity<Student> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                Boolean.class);
        return responseEntity.getBody();

    }

  
    /**
     * Removes a student from the system based on user input.
     * @throws IOException If an input or output exception occurred.
     */
    private void removeStudent() throws IOException {
        String studentName = studentView.readStudentName();
        if(!checkStudentExists(studentName)){
            out.println("The Student you want to remove does not exists");
            return;
        }
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/student/"+studentName;
        HttpEntity<Void> requestEntity = new HttpEntity<>(getSessionTokenHeaders()); 
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class);
        if(responseEntity.getStatusCode()==HttpStatus.OK){
            studentView.showSuccessRemoveMessage(studentName);
        }else{
            out.println("Remove Student: "+studentName+" failed");
        }
        

    }
    /**
     * Edits the display name of an existing student.
     * @throws IOException If an input or output exception occurred.
     */
    private void editStudentDisplayName() throws IOException {
        String studentName = studentView.readStudentName();
        String newDisplayName = studentView.readStudentDisplayName();
        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Cookie", UserSession.getInstance().getSessionToken());
        HttpEntity<String> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/student/displayname/" + studentName + "/" + newDisplayName;
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        if (responseEntity.getStatusCode()==HttpStatus.OK) {
            studentView.showSuccessEditNameMessage(studentName, newDisplayName);
        }else if(responseEntity.getStatusCode()==HttpStatus.NOT_FOUND){
            studentView.showStudentNotFoundMessage("edit dispaly name");
        }else{
            out.println("Error happen in server when try to edit display Name ");
        }
    }
    /**
     * Loads students from a CSV file and sends the loaded roster to the server.
     * @throws IOException If an error occurs while reading the file or sending data.
     */
    public void loadFromCSV() throws IOException {

        Iterable<Student> students;
        String filedirectory = "src/input/";

        while (true) {
            try {
                String fileName = studentView.getFileName();
                String fullPath = filedirectory + fileName;
                students = readCSV(fullPath);
                studentView.showLoadSuccessMessage();
                studentView.displayStudentList(students);
                break;
            } catch (Exception e) {
                out.println("import from csv failed: " + e.getMessage());
            }

        }
        sendRoster(students);

    }
    /**
     * Utility method to obtain HTTP headers containing the session token.
     * @return HttpHeaders with session token included.
     */
    private HttpHeaders getSessionTokenHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        return headers;
    }
    /**
     * Sends the student roster to the server.
     * @param students Iterable collection of Student objects to send.
     */
    public void sendRoster(Iterable<Student> students) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/roster";
        ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<List<Student>>() {
        };
        HttpEntity<Iterable<Student>> requestEntity = new HttpEntity<>(students, getSessionTokenHeaders());
        ResponseEntity<String> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
           String.class);
    }
    /**
     * Retrieves a list of all students from the server.
     * @return A list of Student objects.
     */
    private List<Student> getStudentsFromServer() {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/allStudents";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Student>>() {
                });
        return responseEntity.getBody();
    }
    /**
     * Reads student information from a CSV file and returns it as a list of Student objects.
     * @param fileName The path to the CSV file.
     * @return An iterable collection of Student objects read from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public static Iterable<Student> readCSV(String fileName) throws IOException {
        java.nio.file.Path path = Paths.get(fileName);
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        Relation r;

        try {

            r = CSVHandler.readFromCSVwithoutHeader(Tuple.of("column1", "column2", "column3"),
                    Tuple.of(Tuple.STRING, Tuple.STRING, Tuple.STRING),
                    reader);

        } catch (IllegalArgumentException iae) {
            try {
                reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                // System.out.println("testing case 2");
                r = CSVHandler.readFromCSVwithoutHeader(Tuple.of("column1", "column2"),
                        Tuple.of(Tuple.STRING, Tuple.STRING),
                        reader);
                // System.out.println("case 2 was successful");
            }

            catch (IllegalArgumentException iae2) {
                throw iae2;
            }

        }

        // System.out.println("read " + r.size() + " lines");

        List<Student> res = new ArrayList<>();
        // empty relation
        if (!r.iterator().hasNext())
            return res;

        // check first line
        Tuple t = r.iterator().next();
        int displayNameIdx, emailIdx;
        boolean containsHeader;
        if (t.size() == 2) {
            // System.out.println("trying to understand header contents");
            displayNameIdx = 2;
            emailIdx = 1;
            containsHeader = ((String) t.get(1)).toLowerCase().contains("email");
            // System.out.println("t[1]: " +t.get(1));
        } else {
            // t.size() == 3
            // check if contains header first
            containsHeader = ((String) t.get(1)).toLowerCase().contains("email")
                    || ((String) t.get(2)).toLowerCase().contains("email");
            if (containsHeader) {
                if (((String) t.get(1)).toLowerCase().contains("email")) {
                    emailIdx = 1;
                    displayNameIdx = 2;
                } else {
                    emailIdx = 2;
                    displayNameIdx = 1;
                }
            } else {
                if (EmailUtils.checkEmail((String) t.get(1))) {
                    emailIdx = 1;
                    displayNameIdx = 2;
                } else {
                    emailIdx = 2;
                    displayNameIdx = 1;
                }
            }
        }

        // System.out.println("contains header: " + containsHeader);

        // now parse each row
        boolean usedHeader = false;
        Iterator<Tuple> iter = r.iterator();

        while (iter.hasNext()) {
            t = iter.next();
            if (containsHeader && !usedHeader) {
                usedHeader = true;
                continue;
            }
            //
            String legalName = (String) t.get(0);
            String email = (String) t.get(emailIdx);
            String displayName;
            if (displayNameIdx == 1) {
                displayName = (String) t.get(1);
            } else {
                displayName = t.size() == 2 ? legalName : (String) t.get(2);
            }
            if (!EmailUtils.checkEmail(email)) {
                throw new IllegalArgumentException("invalid email format: " + email);
            }
            res.add(new Student(legalName, displayName, email));
        }

        return res;
    }
    /**
     * Adds a new student with a display name to the system.
     * @throws IOException If an input or output exception occurred.
     */
    private void addStudentDisplayName() throws IOException {
        String studentName = studentView.readStudentName();
        String displayName = studentView.readStudentDisplayName();
        // todo
    }

}