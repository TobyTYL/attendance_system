package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import edu.duke.ece651.team1.client.view.*;

/**
 * The ApplicationController class serves as the entry point for the
 * application's user interface.
 * It coordinates the flow between different parts of the application, such as
 * login/signup process
 * and navigating to the main menu. It utilizes various controller and view
 * classes to manage user
 * interactions and application logic.
 */
public class ApplicationController {
    BufferedReader inputReader;
    final PrintStream out;
    LoginSignupController loginSignupController;
    
    // ProfessorMainMenuController professorMainMenuController;
    // StudentController studentController;

    // LoginSignUpView loginSignUpView;
    // MainMenuView mainMenuView;
    /**
     * Constructs an ApplicationController with the specified input reader and
     * output stream.
     * Initializes the login/signup controller, main menu controller, and student
     * controller
     * with the given input and output streams to manage user interaction.
     * 
     * @param inputReader The BufferedReader used to read input from the user.
     * @param out         The PrintStream used to print output to the user.
     */

    public ApplicationController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.loginSignupController = new LoginSignupController(inputReader, out);
       
    }

    private CourseController getCourseController(String message){
        JSONObject jsonObject = new JSONObject(message);
        String role = jsonObject.getString("role");
        int id = jsonObject.getInt("id");
        CourseController courseController = new CourseController(role, id, inputReader, out);
        return courseController;
    }

    /**
     * Starts the application by first prompting the user to authenticate or
     * register.
     * Continues to the main menu upon successful authentication. This is the main
     * loop
     * for the application, allowing the user to interact with different
     * functionalities
     * based on their choices.
     */

    public void startApplication() {
        String loginMessage;
        while (true) {
            try {
                loginMessage = loginSignupController.authenticateOrRegister();
                //login success
                if(!loginMessage.equals("LoginFailed")){
                    break;
                    }
                }
            catch (Exception e) {
                out.println("Exception happpened during login SignUp because: " + e.getMessage());
            }
        }
       
        try{
            getCourseController(loginMessage).startCourseMainMenu();
        }catch(Exception e){
            out.println("Course menue error because "+e.getMessage());
        }
        
        

    }
}
