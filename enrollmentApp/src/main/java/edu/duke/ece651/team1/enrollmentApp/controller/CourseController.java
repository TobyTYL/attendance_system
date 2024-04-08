package edu.duke.ece651.team1.enrollmentApp.controller;

import edu.duke.ece651.team1.enrollmentApp.view.CourseView;
import edu.duke.ece651.team1.data_access.*;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.client.*;
import java.sql.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.io.IOException;
public class CourseController {
    private final CourseDao courseDao;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final CourseView CourseView;
    private final SectionController sectionController;

    public CourseController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.CourseView = new CourseView(inputReader, out);
        this.courseDao = new CourseDaoImp();
        this.sectionController = new SectionController(inputReader, out);
    }
    public void startCourseManagement() throws IOException, SQLException {
        while (true) {
            CourseView.showClassManageOption();
            int option = CourseView.getStudentManageChoice();
            switch (option) {
                case 1:
                    createNewClass();
                    break;
                case 2:
                    updateClass();
                    break;
                case 3:
                    removeClass();
                    break;
                case 4:
                    return; // Return to the main menu
                default:
                    out.println("Invalid option for Class Management Menu.");
            }
        }
    }

    private void createNewClass() throws IOException {
        try {
            String className = CourseView.getClassNameToCreate();
            if (courseDao.checkCourseExists(className)) {
                out.println("Class already exists!");
                return;
            }
            Course newCourse = new Course(className);
            courseDao.addCourse(newCourse);
            if (courseDao.checkCourseExists(className)) {
                CourseView.showCreateNewClassSuccessMessage(className);
                return;
            }
            
        } catch (Exception e) {
            // Handle exception
            out.println("An error occurred: " + e.getMessage());
        }
    }

    private void updateClass() throws IOException, SQLException {
        // Use CourseView to ask which class to update
        showAllCourses();
        String ClassNameToUpdate = CourseView.getClassNameToUpdateOrRemove("update");
        if (!courseDao.checkCourseExists(ClassNameToUpdate)) {
            out.println("Class does not exist!");
            return;
        }
        while (true) {
            CourseView.showClassSectionOptions(ClassNameToUpdate);
            int updateChoice = CourseView.getStudentUpdateCourseChoice();
            switch (updateChoice) {
                case 1:
                    sectionController.addSection(ClassNameToUpdate);
                    break;
                case 2:
                    sectionController.removeSection(ClassNameToUpdate);
                    break;
                case 3:
                    sectionController.updateSection(ClassNameToUpdate);
                    break;
                case 4:
                updateClassName(ClassNameToUpdate);
                case 5:
                    return; // Return to the main menu
                default:
                    out.println("Invalid option for Class Management Menu.");
            }
        }
        
        // String newClassName = CourseView.getNewClassName();

        // // Update the class name using CourseDao
        // courseDao.updateClassName(oldClassName, newClassName);

        // // Confirm the successful update
        // CourseView.showActionConfirmation("updated", newClassName);
    }

    private void removeClass() throws IOException {
        showAllCourses();
        String className = CourseView.getClassNameToUpdateOrRemove("remove");
        if (!courseDao.checkCourseExists(className)) {
            out.println("Class does not exist!");
            return;
        }
        if (CourseView.confirmAction("remove", className)) {
            courseDao.deleteCourse(className);
            CourseView.showActionConfirmation("removed", className);
        }
    }
    
    private void updateClassName(String oldClassName) throws IOException {
        String newClassName = CourseView.getNewClassName();
        if (courseDao.checkCourseExists(newClassName)) {
            out.println("Class with the new name already exists!");
            return;
        }
        courseDao.updateClassName(oldClassName, newClassName);
        CourseView.showUpdateClassNameConfirmation(oldClassName, newClassName);
    }
    public void showAllCourses() throws IOException {
        List<Course> courses = courseDao.getAllCourses(); // Fetch the list of all courses
        
        if (courses.isEmpty()) {
            out.println("No courses available.");
            return;
        }

        out.println("Available Courses:");
        for (Course course : courses) {
            out.println("ID: " + course.getID() + ", Name: " + course.getName());
        }
    }
}
