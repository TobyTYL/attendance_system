//package edu.duke.ece651.team1.enrollmentApp;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import edu.duke.ece651.team1.client.*;
//import edu.duke.ece651.team1.enrollmentApp.controller.ApplicationController;
//import edu.duke.ece651.team1.shared.*;
//import edu.duke.ece651.team1.enrollmentApp.view.*;
//
//
///**
// * The main class of the application that initializes the application and starts the main menu.
// */
//public class App {
//    private final BufferedReader inputReader;
//    private final PrintStream out;
//    private final ApplicationController applicationController;
//
//    // Dependency injection through the constructor
//    /**
//     *  Constructor to initialize the application with necessary dependencies.
//     * @param inputReader
//     * @param out
//     * @param applicationController
//     */
//    public App(BufferedReader inputReader, PrintStream out, ApplicationController applicationController) {
//        this.inputReader = inputReader;
//        this.out = out;
//        this.applicationController = applicationController;
//    }
//    /**
//     * Starts the application by calling the startApplication method of the ApplicationController.
//     */
//    public void run() {
//        try {
//            applicationController.startApplication();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * Main method to start the application.
//     * @param args
//     */
//    public static void main(String[] args) {
//        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
//        PrintStream out = System.out;
//        ApplicationController applicationController = new ApplicationController(inputReader, out);
//        new App(inputReader, out, applicationController).run();
//    }
//}
