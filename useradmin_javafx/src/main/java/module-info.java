module edu.duke.ece651.team1.useradmin_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.duke.ece651.team1.useradmin_javafx to javafx.fxml;
    exports edu.duke.ece651.team1.useradmin_javafx;
}