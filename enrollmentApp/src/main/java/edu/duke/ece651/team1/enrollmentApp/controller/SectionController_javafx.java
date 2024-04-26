package edu.duke.ece651.team1.enrollmentApp.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

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

    @FXML
    private void onAddSectionClick() {//when adding a section, all professor names are shown
        loadFXML("/AddSection.fxml");
    }

    @FXML
    private void onUpdateSectionClick() {
        loadFXML("/UpdateSection.fxml");
    }

    @FXML
    private void onRemoveSectionClick() {
        loadFXML("/RemoveSection.fxml");
    }

    @FXML
    private void onReturnToClassClick() {
        loadFXML("/CourseMgmtNavi.fxml");  // Assume PreviousMenu.fxml is the return FXML
    }

    @FXML
    private void onUpdateClassNameClick() {
        loadFXML("/ChangeClassName.fxml");
    }

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
