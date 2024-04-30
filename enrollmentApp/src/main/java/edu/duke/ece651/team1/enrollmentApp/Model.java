package edu.duke.ece651.team1.enrollmentApp;

import edu.duke.ece651.team1.shared.Course;

public class Model {
     private static Course selectedCourse;

    public static void setSelectedCourse(Course course) {
        selectedCourse = course;
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }
}
