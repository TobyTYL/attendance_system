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

    @GetMapping("course/allcourses/{role}/{id}")
    public String getCourses(@PathVariable String role, @PathVariable int id, Model model){
        logger.info("course controller begin");
        List<CourseDetail> courses = courseService.getCourses(role, id);
        model.addAttribute("courses", courses);
        model.addAttribute("role", role);
        model.addAttribute("id", id);
        return "course";
    }
   
    

}
