module edu.duke.ece651.team1.useradmin_javafx {
    requires javafx.controls;
    requires javafx.fxml;
//    requires edu.duke.ece651.team1.shared;
    opens edu.duke.ece651.team1.useradmin_javafx to javafx.fxml;
    exports edu.duke.ece651.team1.useradmin_javafx;
}
