package edu.duke.ece651.team1.data_access.Course;
import edu.duke.ece651.team1.shared.Course;
import java.util.List;

import edu.duke.ece651.team1.data_access.Course.*;

public interface CourseDao {
    List<Course> getAllCourses();
    Course getCourseById(int id);
    int getClassIdByName(String className);
    void addCourse(Course course) throws Exception;
    void updateCourse(Course course);
    void updateClassName(String oldClassName, String newClassName);
    void deleteCourse(int id);
    void deleteCourse(String className);
    boolean checkCourseExists(String className);
    Course getClassByName(String name);
}
