package edu.duke.ece651.team1.enrollmentApp.view;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.shared.User;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;
/**
* The SectionView class provides methods to display section management options and process user interactions
* for adding, removing, or updating sections within courses. This class interfaces with DAOs to fetch
* professor and section information.
*/
public class SectionView {
   private BufferedReader inputReader;// Reader to receive input from the user.
   private final PrintStream out;// Stream to send output to the user.
   private final UserDao userDao;// Data access object for user queries.
   private final ProfessorDao professorDao;// Data access object for professor queries.
   /**
    * Constructor initializing the SectionView with standard input/output and DAO implementations.
    *
    * @param inputReader A BufferedReader for user input.
    * @param out A PrintStream for system output.
    */
   public SectionView(BufferedReader inputReader, PrintStream out) {
       this.inputReader = inputReader;
       this.out = out;
       this.userDao = new UserDaoImp();
       this.professorDao = new ProfessorDaoImp();
   }
   /**
    * Alternative constructor for dependency injection in tests or when different implementations of DAOs are needed.
    *
    * @param inputReader A BufferedReader for user input.
    * @param out A PrintStream for system output.
    * @param userDaoImp A UserDao implementation.
    * @param professorDaoImp A ProfessorDao implementation.
    */
   public SectionView(BufferedReader inputReader, PrintStream out, UserDao userDaoImp, ProfessorDao professorDaoImp) {
       this.inputReader = inputReader;
       this.out = out;
       this.userDao = userDaoImp;
       this.professorDao = professorDaoImp;
   }
   /**
    * Displays options for managing sections associated with a specific class.
    *
    * @param className The name of the class for which sections are managed.
    */
   public void showClassSectionOptions(String className) {
       out.println("\nManage Sections for " + className + ":");
       out.println("1. Add a Section");
       out.println("2. Remove a Section");
       out.println("3. Update a Section");
       out.println("5. Return to Previous Menu");
   }
   // add classname into para
   /**
    * Displays all sections from a list, including professor names associated with each section.
    *
    * @param sections A list of Section objects to display.
    */
    public void showAllSections(List<Section> sections) {
       if (sections.isEmpty()) {
           out.println("No sections available.");
           return;
       }
       // edit here
       out.println("Available sections:");
//         out.println("Available sections for class " + className + ":");
        for (Section section : sections) {
//             if (section.getClassName().equals(className)) {
                String professorName = "";
                Integer professorID = section.getProfessorId();
                Professor professor = professorDao.getProfessorById(professorID);
                User professorUser = userDao.getUserById(professor.getUserId());
                professorName = professorUser.getUsername();
                out.println("Section ID: " + section.getSectionId() + ", Class ID: " + section.getClassId()
                        + ", Professor ID: " + section.getProfessorId()
                        + " (Professor Name: " + professorName + ")");
//             }
       }
   }
   /**
    * Prompts the user to select a section management option.
    * @return
    * @throws IOException
    */
   public int getSectionManageChoice() throws IOException {
       return ViewUtils.getUserOption(inputReader, out, 5);
   }

   /**
    * Prompts the user for the SectionID of the section to remove.
    * @param className
    * @return
    * @throws IOException
    */
   public int getSectionToRemove(String className) throws IOException {
       String ans = ViewUtils.getUserInput(
               "Enter the SectionID of the section you wish to remove from " + className + ": ",
               "Invalid SectionID/Professor name. Please enter a valid one: ",
               inputReader,
               out,
               input -> !input.trim().isEmpty() // Validate that the input is not empty
       );
       return Integer.valueOf(ans);
   }

   /**
    * Prompts the user for the SectionID of the section to update.
    * @param className
    * @return
    * @throws IOException
    */
   public String getSectionToUpdate(String className) throws IOException {
       return ViewUtils.getUserInput(
               "Enter the SectionID/professorName of the section you wish to update in " + className + ": ",
               "Invalid SectionID/Professor name. Please enter a valid one: ",
               inputReader,
               out,
               input -> !input.trim().isEmpty() // Validate that the input is not empty
       );
   }
   /**
    * Prompts the user for the Professor name who will be instructing the section.
    * @return
    * @throws IOException
    */
   public String getProfessorDetailsForSection() throws IOException {
       return ViewUtils.getUserInput(
               "Enter the Professor name who will be instructing this section: ",
               "Professor ID or name cannot be empty. Please enter a valid ID or name: ",
               inputReader,
               out,
               input -> !input.trim().isEmpty()
       );
   }

   /**
    * Displays a success message after adding a new section.
    * @param className
    * @param professorName
    */
   public void showAddSectionSuccessMessage(String className, String professorName) {
       out.println("New section added to [SectionID: " + className + "] with Professor [" + professorName + "] successfully.");
   }

   /**
    * Prompts the user for the SectionID of the section to update.
    * @param className
    * @return
    * @throws IOException
    */
   public void showRemoveSectionSuccessMessage(int sectionID, String className) {
       out.println("Section [SectionID: " + sectionID + "] removed successfully from [Class: " + className + "].");
   }

   /**
    * Prompts the user for the detail to update for the section.
    * @return
    * @throws IOException
    */
   public int getDetailToUpdateForSection() throws IOException {
       out.println("\nWhat would you like to update for the section?");
       out.println("1. Professor");
       out.print("Enter your choice: ");
       return ViewUtils.getUserOption(inputReader, out, 1);
   }

   /**
    * Prompts the user for the new Professor name for the section.
    * @return
    * @throws IOException
    */
   public String getNewProfessorName() throws IOException {
       return ViewUtils.getUserInput(
               "\nEnter the new Professor name for the section: ",
               "Professor ID or name cannot be empty. Please enter a valid ID or name: ",
               inputReader,
               out,
               input -> !input.trim().isEmpty()
       );
   }
   /**
    * Displays a success message after updating a section.
    * @param sectionID
    * @param professorName
    */
   public void showUpdateSectionSuccessMessage(String sectionID, String professorName) {
       out.println("Section " + sectionID + " now assigned to Professor " + professorName + " successfully.");
   }
   /**
    * Displays a success message after updating a class name.
    * @param oldClassName
    * @param newClassName
    */
   public void showUpdateClassNameConfirmation(String oldClassName, String newClassName){
       out.println("Successfully update the class name: " + oldClassName + " to: " + newClassName);
   }
   /**
    *  Prompts the user for a new class name.
    * @param action
    * @param sectionID
    * @return
    * @throws IOException
    */
   public boolean confirmAction(String action, int sectionID) throws IOException {
       String userInput = ViewUtils.getUserInput(
           "\nAre you sure you want to " + action + " section " + sectionID + "? This action cannot be undone. (yes/no): ",
           "Invalid response. Please answer yes or no: ",
           inputReader,
           out,
           input -> input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")
       );
       return "yes".equalsIgnoreCase(userInput) || "Y".equalsIgnoreCase(userInput);
   }
   /**
    * Displays a success message after updating a class name.
    * @param professors
    */
   public void showAllProfessors(List<Professor> professors) {
   if (professors.isEmpty()) {
       out.println("No professors available.");
       return;
   }
   out.println("Available Professors:");
   for (Professor professor : professors) {
       // edit here (Toby)
       int userId = professor.getUserId();
       String professorName = userDao.getUserById(userId).getUsername();
       out.println("ID: " + professor.getProfessorId() + " - Name: " + professorName);
//        out.println("ID: " + professor.getProfessorId() + " - Name: " + professor.getDisplayName())
       }
   }

}
