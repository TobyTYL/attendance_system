package edu.duke.ece651.team1.client.controller;
import edu.duke.ece651.team1.client.model.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
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
        model.addAttribute("uid", UserSession.getInstance().getUid());
        return "professormenue";
    }

}
