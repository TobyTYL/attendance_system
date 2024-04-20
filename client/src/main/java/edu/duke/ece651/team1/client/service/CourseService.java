package edu.duke.ece651.team1.client.service;
import edu.duke.ece651.team1.client.model.*;
import org.springframework.stereotype.Service;
import org.springframework.core.ParameterizedTypeReference;
import java.util.*;
import edu.duke.ece651.team1.client.controller.*;
import org.json.*;

@Service
public class CourseService {

     /**
     * Retrieves the list of courses based on the user's role (Professor or Student).
     *
     * @param role The role of the user (Professor or Student).
     * @return A list of course details in JSON format.
     */
    public List<CourseDetail> getCourses(String role,int id) {
        String userTypePath = role.equals("Professor") ? "professor" : "student";
        List<String> jsonStrings = fetchCourses(userTypePath,id);
        List<CourseDetail> courseDetails = new LinkedList<>();
        for (String jsonString : jsonStrings) {
            JSONObject course = new JSONObject(jsonString);
            CourseDetail courseDetail = new CourseDetail(course.getInt("courseId"),  course.getInt("sectionId"), course.getString("courseName"));
            courseDetails.add(courseDetail);
        }
        return courseDetails;

    }
    /**
     * Fetches the list of courses from the backend service.
     *
     * @param userTypePath The path indicating the user type (professor or student).
     * @return A list of course details in JSON format.
     */
    public List<String> fetchCourses(String userTypePath,int id) {
        ParameterizedTypeReference<List<String>> responseType =new ParameterizedTypeReference<List<String>>() {
        };
        String url = String.format("http://%s:%s/api/class/%s/allclasses/?%sId=%d",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), userTypePath, userTypePath, id);
        return ApiService.executeGetRequest(url, responseType);
    }
}
