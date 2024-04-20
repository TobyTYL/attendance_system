package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import edu.duke.ece651.team1.client.view.MainMenuView;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.CourseService;
import edu.duke.ece651.team1.client.view.CourseView;

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
/**
 * The MainMenuController class manages the main menu of the application.
 * It directs the user to different sections of the application based on their
 * choice.
 */
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfessorMainMenuController {
    @GetMapping("/professor/class/{classname}/{sectionId}")
    public String showCourseMenu(@PathVariable String classname, @PathVariable int sectionId, Model model) {
        model.addAttribute("className", classname);
        model.addAttribute("sectionId", sectionId);
        return "professormenue";
    }

}
