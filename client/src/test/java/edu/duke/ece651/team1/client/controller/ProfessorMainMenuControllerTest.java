package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import edu.duke.ece651.team1.client.model.*;
@ContextConfiguration(classes = { ProfessorMainMenuController.class })
@ExtendWith(SpringExtension.class)
public class ProfessorMainMenuControllerTest {
    @Autowired
    private ProfessorMainMenuController controller;
    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ExtendedModelMap();
    }

    @Test
    public void testShowCourseMenu() {
        String classname = "Software Engineering";
        int sectionId = 1;
        String viewName = controller.showCourseMenu(classname, sectionId, model);
        assertEquals("professormenue", viewName);
        assertEquals(classname, model.getAttribute("className"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertEquals(UserSession.getInstance().getUid(), model.getAttribute("uid"));
    }
}
