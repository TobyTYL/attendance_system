package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;

import javax.swing.Spring;

import java.io.IOException;
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

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentMainMenuController {
    /**
     * Displays the course menu for a student, showing options available for a specific class and section.
     *
     * @param classname the name of the class
     * @param classId the ID of the class
     * @param sectionId the ID of the section
     * @param model the Spring Model object to pass data to the view
     * @return the name of the view to render
     */
    @GetMapping("/student/class/{classname}/{classId}/{sectionId}")
    public String showCourseMenu(@PathVariable String classname, @PathVariable int sectionId,@PathVariable int classId, Model model) {
        model.addAttribute("className", classname);
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("classId", classId);
        return "studentMenue";
    }
}