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

/**
 * The CourseController class manages the course-related functionality of the application.
 * It handles operations such as retrieving course details based on the user's role (Professor or Student)
 * and navigating to the main menu of a selected course.
 * This controller interacts with the CourseView for user input and displays and utilizes a RestTemplate
 * for HTTP requests to the backend service.
 */
public class CourseController {
    private final String role;
    private final int id;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final CourseView view;
  
    /**
     * Constructs a CourseController with the specified role, user ID, input reader, and output stream.
     *
     * @param role         The role of the user (Professor or Student).
     * @param id           The ID of the user.
     * @param inputReader  The BufferedReader to read user input.
     * @param out          The PrintStream to output data to the user.
     */
    public CourseController(String role, int id, BufferedReader inputReader, PrintStream out) {
        this.role = role;
        this.id = id;
        this.inputReader = inputReader;
        this.out = out;
        this.view = new CourseView(inputReader, out);
    }
    /**
     * Starts the main menu for course selection based on the user's role.
     * It retrieves the list of available courses, displays them to the user, and prompts for course selection.
     * After selection, it navigates to the main menu of the chosen course.
     *
     * @throws IOException If an I/O error occurs during user interaction.
     */
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

     /**
     * Retrieves the list of courses based on the user's role (Professor or Student).
     *
     * @param role The role of the user (Professor or Student).
     * @return A list of course details in JSON format.
     */
    public List<String> getCourses(String role) {
        String userTypePath = role.equals("Professor") ? "professor" : "student";
        return fetchCourses(userTypePath);
    }
    /**
     * Fetches the list of courses from the backend service.
     *
     * @param userTypePath The path indicating the user type (professor or student).
     * @return A list of course details in JSON format.
     */
    public List<String> fetchCourses(String userTypePath) {
        ParameterizedTypeReference<List<String>> responseType =new ParameterizedTypeReference<List<String>>() {
        };
        String url = String.format("http://%s:%s/api/class/%s/allclasses/?%sId=%d",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), userTypePath, userTypePath, id);
        return ControllerUtils.executeGetRequest(url, responseType);
    }
}
