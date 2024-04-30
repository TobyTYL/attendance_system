package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

import edu.duke.ece651.team1.client.model.CourseDetail;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.checkerframework.checker.units.qual.min;
import org.json.*;
import org.springframework.ui.Model;
import edu.duke.ece651.team1.shared.*;
import org.slf4j.*;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {
    @Autowired
    CourseService courseService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    /**
     * Retrieves and displays all courses based on the user's role and ID.
     * fetches the courses using the CourseService,
     * and adds them to the model along with user role and ID for display.
     *
     * @param role  the role of the user (e.g., student, teacher) to tailor the
     *              fetched courses
     * @param id    the ID of the user, used to fetch specific courses relevant to
     *              the user
     * @param model the Spring Model object to pass data to the view
     * @return the name of the view to render, showing all courses
     */
    @GetMapping("course/allcourses/{role}/{id}")
    public String getCourses(@PathVariable String role, @PathVariable int id, Model model) {
        logger.info("course controller begin");
        List<CourseDetail> courses = courseService.getCourses(role, id);
        model.addAttribute("courses", courses);
        model.addAttribute("role", role);
        model.addAttribute("id", id);
        return "course";
    }

}
