package edu.duke.ece651.team1.enrollmentApp.controller;

import edu.duke.ece651.team1.enrollmentApp.view.CourseView;
import edu.duke.ece651.team1.data_access.*;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.shared.*;
import java.sql.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.io.IOException;
/**
* Manages course-related functionalities such as creating, updating, and removing courses.
*/
public class CourseController {
   final CourseDao courseDao;
   private final BufferedReader inputReader;
   private final PrintStream out;
   final CourseView CourseView;
   final SectionController sectionController;
   /**
    * Initializes the CourseController with input/output streams and controllers for handling courses and sections.
    *
    * @param inputReader BufferedReader to read user input.
    * @param out PrintStream to print outputs to the user.
    */
   public CourseController(BufferedReader inputReader, PrintStream out) {
       this.inputReader = inputReader;
       this.out = out;
       this.CourseView = new CourseView(inputReader, out);
       this.courseDao = new CourseDaoImp();
       this.sectionController = new SectionController(inputReader, out);
   }

   public CourseController(BufferedReader inputReader, PrintStream out, CourseView courseiew, CourseDao courseDao, SectionController sectionController) {
       this.inputReader = inputReader;
       this.out = out;
       this.CourseView = courseiew;
       this.courseDao = courseDao;
       this.sectionController = sectionController;
   }
   /**
    * Displays options to manage courses and handles user input to create, update, or remove courses.
    *
    * @throws IOException If an input or output exception occurred.
    * @throws SQLException If a database access error occurs.
    */
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
   /**
    * Handles the creation of a new class after checking for its existence to avoid duplicates.
    *
    * @throws IOException If an input or output exception occurred.
    */
   void createNewClass() throws IOException {
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
   /**
    * Handles updating existing classes including adding or removing sections or updating class details.
    *
    * @throws IOException If an input or output exception occurred.
    * @throws SQLException If a database access error occurs.
    */
   void updateClass() throws IOException, SQLException {
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

   }
   /**
    * Handles the removal of a class after confirmation from the user.
    *
    * @throws IOException If an input or output exception occurred.
    */
   void removeClass() throws IOException {
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
   /**
    * Updates the name of an existing class after ensuring the new name does not lead to a duplicate.
    *
    * @throws IOException If an input or output exception occurred.
    */
   void updateClassName(String oldClassName) throws IOException {
       String newClassName = CourseView.getNewClassName();
       if (courseDao.checkCourseExists(newClassName)) {
           out.println("Class with the new name already exists!");
           return;
       }
       courseDao.updateClassName(oldClassName, newClassName);
       CourseView.showUpdateClassNameConfirmation(oldClassName, newClassName);
   }
   /**
    * Displays all available courses from the database.
    *
    * @throws IOException If an input or output exception occurred.
    */
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
