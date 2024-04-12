package edu.duke.ece651.team1.client.view;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.util.List;
import org.json.*;

import java.io.IOException;

/**
 * The CourseView class provides methods for displaying course-related information to the user.
 */
public class CourseView {
    BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a CourseView object with the given input reader and output stream.
     *
     * @param inputReader The BufferedReader to read user input.
     * @param out         The PrintStream to display output to the user.
     */
    public CourseView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays a welcome message for the user.
     *
     * @param role The role of the user (e.g., Professor or Student).
     */
    public void showWelcomeMessage(String role) {
        out.println("Welcome, " + role + "! Ready to manage your courses?");
        out.println("Choose a course to begin!");
    }
    /**
     * Displays a message indicating that no courses are available for the user.
     *
     * @param role The role of the user (e.g., Professor or Student).
     */
    public void showNoCourseMessge(String role) {
        if ("Professor".equalsIgnoreCase(role)) {
            out.println("No courses available for you to manage at this time.");
        } else  {
            out.println("You are not currently enrolled in any courses.");
        }
    }
    /**
     * Displays the available courses and prompts the user to choose one.
     *
     * @param jsonStrings A list of JSON strings representing course information.
     * @return The JSONObject representing the chosen course, or null if the user chooses to exit.
     * @throws IOException If an I/O error occurs while reading user input.
     */
    // return sectionID
    public JSONObject displayCoursesAndPrompt(List<String> jsonStrings) throws IOException {
        while (true) {
            try {
                int index = 1;
                out.println("Available Courses:");
                for (String jsonString : jsonStrings) {
                    JSONObject course = new JSONObject(jsonString);
                    out.printf("%d: Course Name: %s, Section ID: %s%n",
                            index++,
                            course.getInt("courseId"),
                            course.getString("courseName"),
                            course.getInt("sectionId"));
                }
                out.println(index + ": Exit");
                int choose = ViewUtils.getUserOption(inputReader, out, index);
                if(choose == index){
                    return null;
                }
                return new JSONObject(jsonStrings.get(choose - 1));
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for cousr choose");
            }

        }

       

    }

}
