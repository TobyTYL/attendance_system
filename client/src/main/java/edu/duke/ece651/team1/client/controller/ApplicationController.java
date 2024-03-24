package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;

import org.springframework.web.client.RestTemplate;

import edu.duke.ece651.team1.client.view.*;

public class ApplicationController {
    BufferedReader inputReader;
    final PrintStream out;
    LoginSignupController loginSignupController;
    MainMenuController mainMenuController;
    StudentController studentController;
    
    // LoginSignUpView loginSignUpView;
    // MainMenuView mainMenuView;
   
    public ApplicationController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.loginSignupController = new LoginSignupController(inputReader, out);
        this.mainMenuController = new MainMenuController(inputReader, out);
        this.studentController = new StudentController(inputReader, out);
    }
    public void startApplication(){
       boolean authenticated = false;
       while (!authenticated) {
            authenticated = loginSignupController.authenticateOrRegister();
       }
       mainMenuController.startMainMenu();

    }
}
