package edu.duke.ece651.team1.client.view;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.util.List;
import org.json.*;

import com.google.gson.JsonObject;

import java.io.IOException;

public class CourseView {
    BufferedReader inputReader;
    private final PrintStream out;

    public CourseView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showWelcomeMessage(String role) {
        out.println("Welcome, " + role + "! Ready to manage your courses?");
        out.println("Choose a course to begin!");
    }

    public void showNoCourseMessge(String role) {
        if ("Professor".equalsIgnoreCase(role)) {
            out.println("No courses available for you to manage at this time.");
        } else if ("Student".equalsIgnoreCase(role)) {
            out.println("You are not currently enrolled in any courses.");
        }
    }

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
