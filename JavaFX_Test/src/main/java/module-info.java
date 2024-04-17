module edu.duke.ece651.team1.javafx_test {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.duke.ece651.team1.javafx_test to javafx.fxml;
    exports edu.duke.ece651.team1.javafx_test;
}