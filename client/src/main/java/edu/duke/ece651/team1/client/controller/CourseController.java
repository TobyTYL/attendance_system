package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.CourseView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.json.*;


public class CourseController {
    private final String role;
    private final int id;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final CourseView view;
  

    public CourseController(String role, int id, BufferedReader inputReader, PrintStream out) {
        this.role = role;
        this.id = id;
        this.inputReader = inputReader;
        this.out = out;
        this.view = new CourseView(inputReader, out);
    }

    public void startCourseMainMenu() throws IOException {
        view.showWelcomeMessage(role);
        while (true) {
            List<String> courseDetailJson = getCourses(role);
            if (courseDetailJson.isEmpty()) {
                view.showNoCourseMessge(role);
                out.println("See you next time.");
                break;
            }
            try {
                JSONObject courseChosed = view.displayCoursesAndPrompt(courseDetailJson);
                if(courseChosed == null){
                    out.println("GoodBye!");
                    break;
                }
                int classId = courseChosed.getInt("courseId");
                String courseName =courseChosed.getString("courseName");
                int sectionId = courseChosed.getInt("sectionId");
                if (role.equals("Professor")) {
                    ProfessorMainMenuController nextController = new ProfessorMainMenuController(sectionId, classId, courseName, inputReader, out);
                    nextController.startMainMenu();
                } else {
                    StudentController nexController = new StudentController(inputReader, out, sectionId, classId, courseName, id);
                    nexController.startStudentMenu();
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for course selection.");
            }
        }
    }


    public List<String> getCourses(String role) {
        String userTypePath = role.equals("Professor") ? "professor" : "student";
        return fetchCourses(userTypePath);
    }

    public List<String> fetchCourses(String userTypePath) {
        ParameterizedTypeReference<List<String>> responseType =new ParameterizedTypeReference<List<String>>() {
        };
        String url = String.format("http://%s:%s/api/class/%s/allclasses/?%sId=%d",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), userTypePath, userTypePath, id);
        return ControllerUtils.executeGetRequest(url, responseType);
    }
}
