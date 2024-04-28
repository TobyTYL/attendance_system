package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
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
    // legal name, email, display name
    @GetMapping("/student/class/{classname}/{classId}/{sectionId}")
    public String showCourseMenu(@PathVariable String classname, @PathVariable int sectionId,@PathVariable int classId, Model model) {
        model.addAttribute("className", classname);
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("classId", classId);
        return "studentMenue";
    }
}