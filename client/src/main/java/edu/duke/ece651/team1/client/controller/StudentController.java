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
import edu.duke.ece651.team1.shared.Student;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StudentController {
    // legal name, email, display name
    BufferedReader inputReader;
    final PrintStream out;
    StudentView studentView;
    RestTemplate restTemplate;

    public StudentController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.studentView = new StudentView(inputReader, out);
        this.restTemplate = new RestTemplate();
    }

    public void startStudentMenu() {
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
                } else if (option.equals("exit")) {
                    return;
                } else {
                    out.println("Invalid option. Please select a valid option.");
                }
            } catch (IOException e) {
                out.println("Student Menu error because " + e.getMessage());
            }
        }
    }

    private void addStudent() throws IOException {
        String studentName = studentView.readStudentName();
        String displayName = studentView.readStudentDisplayName();
        String email = studentView.readStudentEmail();
        Student newStudent = new Student(studentName, displayName, email);
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() + "/api/students/addStudent";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        HttpEntity<Student> requestEntity = new HttpEntity<>(newStudent, headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class);
        out.println("Student added successfully.");
    }

    private void addSingleStudent(Student newStudent) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() + "/api/students/addStudent";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        HttpEntity<Student> requestEntity = new HttpEntity<>(newStudent, headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class);
    }

    private void removeStudent() throws IOException {
        String studentName = studentView.readStudentName();
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() + "/api/students/removeStudent/" + studentName;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers); // 不需要发送任何请求主体
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                Void.class);
        out.println("Student removed successfully.");
    }

    public void loadFromCSV() throws IOException {
        // todo cz

        // String fileName = ViewUtils.getUserInput("please put your file under src/input folder and input a file name (including .csv extension)",
        //         "",
        //         inputReader,
        //         out,
        //         s -> true);
        Iterable<Student> students;
        String filedirectory = "src/input/";
        String fileName = studentView.getFileName();
        String fullPath = filedirectory+fileName;
        while (true) {
            try {

                // Path defaultPath = Paths.get("src","main","java","edu","duke","ece651","team1","client","resources");
                // Path path = defaultPath.resolve(fileName);
    
                students = readCSV(fullPath);
                break;
            
                // out.println(count + " of records from csv imported successfully");
                
            }
            catch (Exception e) {
                out.println("import from csv failed: " + e.getMessage());
            }
            
        }
        sendRoster(students);
       
       
    }

    public void sendRoster(Iterable<Student> students){
        // int count = 0;
        studentView.showLoadSuccessMessage();
        studentView.displayStudentList(students);
        for (Student student: students) {
            addSingleStudent(student);
            // count++;
        }
    }

    private List<Student> getStudentsFromServer() {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() + "/api/students/allStudents";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Student>>() {});
        return responseEntity.getBody();
    }

    public static Iterable<Student> readCSV(String fileName) throws IOException {
        java.nio.file.Path path = Paths.get(fileName);
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        Relation r;

        try {
            // could be2 or 3 columns
            // legal name, email, display name
            // 1st column is guaranteed to be legal name
            r = CSVHandler.readFromCSVwithoutHeader(Tuple.of("column1", "column2", "column3"),
                    Tuple.of(Tuple.STRING, Tuple.STRING, Tuple.STRING) ,
                    reader);

        }
        catch (IllegalArgumentException iae) {
            try {
                reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
//                System.out.println("testing case 2");
                r = CSVHandler.readFromCSVwithoutHeader(Tuple.of("column1", "column2"),
                        Tuple.of(Tuple.STRING, Tuple.STRING),
                        reader);
//                System.out.println("case 2 was successful");
            }

            catch(IllegalArgumentException iae2) {
                throw iae2;
            }

        }

//        System.out.println("read " + r.size() + " lines");

        List<Student> res = new ArrayList<>();
        // empty relation
        if (!r.iterator().hasNext()) return res;

        // check first line
        Tuple t = r.iterator().next();
        int displayNameIdx, emailIdx;
        boolean containsHeader;
        if (t.size() == 2) {
//            System.out.println("trying to understand header contents");
            displayNameIdx = 2;
            emailIdx = 1;
            containsHeader =((String) t.get(1) ).toLowerCase().contains("email");
//            System.out.println("t[1]: " +t.get(1));
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

//        System.out.println("contains header: " + containsHeader);

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
                }
                else {
                    displayName = t.size() == 2 ? legalName : (String) t.get(2);
                }
                if (!EmailUtils.checkEmail(email)) {
                    throw new IllegalArgumentException("invalid email format: " + email);
                }
                res.add(new Student(legalName, displayName, email));
            }

        return res;
    }

    private void addStudentDisplayName() throws IOException {
        String studentName = studentView.readStudentName();
        String displayName = studentView.readStudentDisplayName();
        //todo
    }




}