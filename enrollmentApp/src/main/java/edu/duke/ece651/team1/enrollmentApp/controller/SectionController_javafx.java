package edu.duke.ece651.team1.enrollmentApp.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
/**
 * Controller class responsible for handling user interactions related to managing sections.
 * This class controls the navigation between different section management functionalities.
 */
public class SectionController_javafx {
    @FXML
    private Button addSectionButton;
    @FXML
    private Button updateSectionButton;
    @FXML
    private Button removeSectionButton;
    @FXML
    private Button returnToClassButton;
    @FXML
    private Button updateClassNameButton;
    /**
     * Handles the action of clicking the "Add Section" button.
     * Loads the Add Section view.
     */
    @FXML
    private void onAddSectionClick() {//when adding a section, all professor names are shown
        loadFXML("/AddSection.fxml");
    }
    /**
     * Handles the action of clicking the "Update Section" button.
     * Loads the Update Section view.
     */
    @FXML
    private void onUpdateSectionClick() {
        loadFXML("/UpdateSection.fxml");
    }
    /**
     * Handles the action of clicking the "Remove Section" button.
     * Loads the Remove Section view.
     */
    @FXML
    private void onRemoveSectionClick() {
        loadFXML("/RemoveSection.fxml");
    }
     /**
     * Handles the action of clicking the "Return to Class" button.
     * Returns to the Course Management Navigation view.
     */
    @FXML
    private void onReturnToClassClick() {
        loadFXML("/CourseMgmtNavi.fxml");
    }
    /**
     * Handles the action of clicking the "Update Class Name" button.
     * Loads the Change Class Name view.
     */
    @FXML
    private void onUpdateClassNameClick() {
        loadFXML("/ChangeClassName.fxml");
    }
    /**
     * Loads the specified FXML file and sets it as the scene of the stage.
     * 
     * @param fxmlPath The path of the FXML file to load.
     */
    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) addSectionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
