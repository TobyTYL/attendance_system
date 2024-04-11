package edu.duke.ece651.team1.enrollmentApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.enrollmentApp.controller.ApplicationController;
import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.enrollmentApp.view.*;



public class App {
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final ApplicationController applicationController;

    // Dependency injection through the constructor
    public App(BufferedReader inputReader, PrintStream out, ApplicationController applicationController) {
        this.inputReader = inputReader;
        this.out = out;
        this.applicationController = applicationController;
    }
    public void run() {
        try {
            applicationController.startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        ApplicationController applicationController = new ApplicationController(inputReader, out);
        new App(inputReader, out, applicationController).run();
    }
}
