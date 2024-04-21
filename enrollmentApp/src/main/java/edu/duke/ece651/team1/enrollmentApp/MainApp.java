 package edu.duke.ece651.team1.enrollmentApp;

// import javafx.application.Application;
// import javafx.stage.Stage;
// import edu.duke.ece651.team1.enrollmentApp.controller.MainMenuController;

// public class MainApp extends Application {
//     @Override
//     public void start(Stage primaryStage) {
//         MainMenuController mainMenuController = new MainMenuController();
//         mainMenuController.startMainMenu(primaryStage);
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }
/*
 * This Java source file was generated by the Gradle 'init' task.
 */

 import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import edu.duke.ece651.ww202.javaFX.controller.*;
 public class MainApp extends Application{
     @Override
     public void start(Stage primaryStage) throws IOException{
      try {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml")); // Update the path to your actual FXML file location
        
        // Set the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
     }
 
     public static void main(String[] args) {
       launch(args);
     }
 }
 



