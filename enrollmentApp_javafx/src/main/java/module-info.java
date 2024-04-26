module edu.duke.ece651.team1.enrollmentApp_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires edu.duke.ece651.team1.shared;
    requires edu.duke.ece651.team1.data_access;

    opens edu.duke.ece651.team1.enrollmentApp_javafx.enrollmentApp_javafx to javafx.fxml;
    exports edu.duke.ece651.team1.enrollmentApp_javafx.enrollmentApp_javafx;
}