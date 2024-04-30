package edu.duke.ece651.team1.enrollmentApp.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.shared.User;


/**
* The EnrollmentView class provides user interface functionalities for managing student enrollments
* in courses and sections within the application. This includes displaying enrollment options, handling user
* inputs, and showing relevant messages based on user actions.
*/
public class EnrollmentView {
   private final BufferedReader inputReader;// Reader to receive input from the user.
   private final PrintStream out;// Stream to send output to the user.
   private final ProfessorDao professorDao;// Data access object for professor related queries.
   private final UserDao userDao;// Data access object for user related queries.
   /**
    * Constructor to initialize the EnrollmentView with standard I/O.
    *
    * @param inputReader A BufferedReader to handle input from the user.
    * @param out A PrintStream to handle output to the user.
    */
   public EnrollmentView(BufferedReader inputReader, PrintStream out) {
       this.inputReader = inputReader;
       this.out = out;
       this.professorDao = new ProfessorDaoImp();
       this.userDao = new UserDaoImp();
   }
   /**
    * Alternative constructor that allows dependency injection for testing or customized behavior.
    *
    * @param inputReader A BufferedReader for user input.
    * @param out A PrintStream for system output.
    * @param professorDaoImp A ProfessorDao implementation.
    * @param userDaoImp A UserDao implementation.
    */
   public EnrollmentView(BufferedReader inputReader, PrintStream out, ProfessorDao professorDaoImp, UserDao userDaoImp) {
       this.inputReader = inputReader;
       this.out = out;
       this.professorDao = professorDaoImp;
       this.userDao = userDaoImp;
   }
   /**
    * Displays the main enrollment options to the user.
    *
    * @throws IOException if an input or output exception occurred.
    */
   public void showEnrollmentOptions() throws IOException {
       out.println("\nEnrollment Options:");
       out.println("1. Manually Enroll a Student");
       out.println("2. Batch Enroll Students (CSV Import)");
       out.println("3. Return to Main Menu");
   }
   /**
    * Retrieves the user's choice for enrollment management.
    *
    * @return An integer indicating the user's choice.
    * @throws IOException if there is a failure in reading input.
    */
   public int getEnrollmentChoice() throws IOException {
       return ViewUtils.getUserOption(inputReader, out, 3);
   }
   /**
    * Displays all available courses.
    *
    * @param courses A list of Course objects to be displayed.
    */
   public void showAllCourses(List<Course> courses) {
       out.println("\nAvailable Courses:");
       courses.forEach(course -> out.println("ID: " + course.getID() + ", Name: " + course.getName()));
   }
   /**
    * Prompts and retrieves the student ID for enrollment.
    *
    * @return A string representing the student ID.
    * @throws IOException if there is an input/output error.
    */
   public String getStudentIDForEnrollment() throws IOException {
       return ViewUtils.getUserInput(
               "\nEnter the student ID: ",
               "Invalid input. Please enter a valid student ID.",
               inputReader,
               out,
               input -> !input.trim().isEmpty() // Validate that the input is not empty.
       );
   }
   /**
    * Prompts and retrieves the class name for which enrollment is to be made.
    *
    * @return A string representing the class name.
    * @throws IOException if there is an input/output error.
    */
   public String getClassForEnrollment() throws IOException {
       return ViewUtils.getUserInput(
               "Enter the name of the class: ",
               "Invalid input. Please enter a valid class name.",
               inputReader,
               out,
               input -> !input.trim().isEmpty() // Validate that the input is not empty.
       );
   }
   /**
    * Displays all available sections for a selected course.
    *
    * @param sections A list of Section objects to be displayed.
    */
   public void showAllSections(List<Section> sections) {
       // if (sections.isEmpty()) {
       //     out.println("\nNo available sections for this course.");
       //     return;
       // }
       out.println("\nAvailable Sections for Selected Course:");
       for (Section section : sections) {
           // Fetch the professor based on professorId
           Professor professor = professorDao.getProfessorById(section.getProfessorId());
           if (professor != null) {
               User professorUser = userDao.getUserById(professor.getUserId());
               //String professorName = professorUser.getUsername();
               String professorName = professorUser.getUsername();
               out.println("SectionID: " + section.getSectionId() + " - Professor ID: " + section.getProfessorId() + " - Name: " + professorName);
           } else {
               // In case the professor ID does not correspond to a valid professor
               out.println("SectionID: " + section.getSectionId() + " - Professor ID: " + section.getProfessorId() + " - Name: Unknown");
           }
       }
       //sections.forEach(section -> out.println("SectionID: " + section.getSectionId() + " - Professor ID: " + section.getProfessorId()));
   }
   /**
    * Prompts the user to select a section ID for enrollment.
    *
    * @return An integer representing the selected SectionID.
    * @throws IOException if there is an input/output error.
    */
   public int getSectionChoice() throws IOException {
       String input = ViewUtils.getUserInput(
           "Enter the SectionID to enroll the student in: ",
           "Please enter a valid SectionID: ",
           inputReader,
           out,
           s -> s.matches("\\d+")
       );
       return Integer.parseInt(input);
   }
   /**
    * Prompts the user to provide the filename for a CSV file used in batch enrollment.
    *
    * @return A string representing the filepath to the CSV file.
    * @throws IOException if there is an input/output error.
    */
   public String getCsvFilePath() throws IOException {
       return ViewUtils.getUserInput(
               "\nEnter the name to the CSV filename for batch enrollment: ",
               "Filename cannot be empty. Please enter a valid file path.",
               inputReader,
               out,
               input -> !input.trim().isEmpty() // Validate that the input is not empty.
       );
   }
}
