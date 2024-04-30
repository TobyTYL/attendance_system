//  package edu.duke.ece651.team1.enrollmentApp.controller;
//  import javafx.scene.control.*;
//  import javafx.stage.Stage;
//  import javafx.application.Platform;
//  import javafx.event.ActionEvent;
//  import javafx.fxml.FXMLLoader;
//  import javafx.scene.Scene;
//  import javafx.scene.Parent;

//  import org.junit.jupiter.api.AfterEach;
//  import org.junit.jupiter.api.BeforeEach;
//  import org.junit.jupiter.api.Test;
//  import org.junit.jupiter.api.extension.ExtendWith;
//  import org.mockito.InjectMocks;
//  import org.mockito.Mock;
//  import org.mockito.MockitoAnnotations;
//  import org.testfx.framework.junit5.ApplicationExtension;

//  import java.io.BufferedReader;
//  import java.io.File;
//  import java.io.FileNotFoundException;
//  import java.io.IOException;
//  import java.nio.charset.StandardCharsets;
//  import java.nio.file.Files;
//  import java.nio.file.Path;
//  import java.nio.file.Paths;
//  import java.util.Comparator;

//  import static org.mockito.ArgumentMatchers.any;
//  import static org.mockito.ArgumentMatchers.anyString;
//  import static org.mockito.Mockito.*;
//  import static org.junit.jupiter.api.Assertions.*;
//  import static org.testfx.api.FxAssert.verifyThat;

//  public class BatchStudentEnrollController_javafxTest {
//     private BatchStudentEnrollController_javafx controller;
//      private Path tempDir;

//      @BeforeEach
//      void setUp() throws Exception {
//          controller = new BatchStudentEnrollController_javafx();
//          controller.userInputText = new TextField();
//          controller.importResult = new Label();
//          tempDir = Files.createTempDirectory("testDir");
//      }

//      @AfterEach
//      void tearDown() throws Exception {
//          // Delete temporary files and directory after tests
//          Files.walk(tempDir)
//              .sorted(Comparator.reverseOrder())
//              .map(Path::toFile)
//              .forEach(File::delete);
//      }

//      @Test
//      void testOnImportClickFileNotFound() {
//          String filename = "nonexistent.csv";
//          controller.userInputText.setText(filename);
//          controller.onImportClick(null);
//          assertTrue(controller.importResult.getText().contains("The specified file was not found"));
//      }

//      @Test
//      void testOnImportClickSuccess() throws IOException {
//          String filename = "existing.csv";
//          Path file = Files.createFile(tempDir.resolve(filename));
//          Files.write(file, "Header\nData1\nData2".getBytes(StandardCharsets.UTF_8));

//          controller.userInputText.setText(tempDir.resolve(filename).toString());
//          controller.onImportClick(null);
//          assertTrue(controller.importResult.getText().contains("Batch enrollment process completed successfully"));
//      }

//      @Test
//      void testOnImportClickIOError() {
//          // Simulate an IO error by using a file that cannot be read
//          String filename = "error.csv";
//          Path file = tempDir.resolve(filename);

//          controller.userInputText.setText(file.toString());
//          controller.onImportClick(null);
//          assertTrue(controller.importResult.getText().contains("An error occurred while reading the file"));
//      }

//  }
