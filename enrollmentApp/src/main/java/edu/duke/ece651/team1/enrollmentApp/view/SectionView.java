package edu.duke.ece651.team1.enrollmentApp.view;
import edu.duke.ece651.team1.client.view.ViewUtils;
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
public class SectionView {
    private BufferedReader inputReader;
    private final PrintStream out;
    private final UserDao userDao;
    // add className
//    private String className;
    //new
    private final ProfessorDao professorDao;
    public SectionView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.userDao = new UserDaoImp();
        this.professorDao = new ProfessorDaoImp();
    }

    public void showClassSectionOptions(String className) {
        out.println("\nManage Sections for " + className + ":");
        out.println("1. Add a Section");
        out.println("2. Remove a Section");
        out.println("3. Update a Section");
        out.println("5. Return to Previous Menu");
    }
    // add classname into para
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
    public int getSectionManageChoice() throws IOException {
        return ViewUtils.getUserOption(inputReader, out, 5);
    }

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

    public String getSectionToUpdate(String className) throws IOException {
        return ViewUtils.getUserInput(
                "Enter the SectionID/professorName of the section you wish to update in " + className + ": ",
                "Invalid SectionID/Professor name. Please enter a valid one: ",
                inputReader,
                out,
                input -> !input.trim().isEmpty() // Validate that the input is not empty
        );
    }

    public String getProfessorDetailsForSection() throws IOException {
        return ViewUtils.getUserInput(
                "Enter the Professor name who will be instructing this section: ",
                "Professor ID or name cannot be empty. Please enter a valid ID or name: ",
                inputReader,
                out,
                input -> !input.trim().isEmpty()
        );
    }

    public void showAddSectionSuccessMessage(String className, String professorName) {
        out.println("New section added to [SectionID: " + className + "] with Professor [" + professorName + "] successfully.");
    }

    public void showRemoveSectionSuccessMessage(int sectionID, String className) {
        out.println("Section [SectionID: " + sectionID + "] removed successfully from [Class: " + className + "].");
    }

    public int getDetailToUpdateForSection() throws IOException {
        out.println("\nWhat would you like to update for the section?");
        out.println("1. Professor");
        out.print("Enter your choice: ");
        return ViewUtils.getUserOption(inputReader, out, 1);
    }

    public String getNewProfessorName() throws IOException {
        return ViewUtils.getUserInput(
                "\nEnter the new Professor name for the section: ",
                "Professor ID or name cannot be empty. Please enter a valid ID or name: ",
                inputReader,
                out,
                input -> !input.trim().isEmpty()
        );
    }

    public void showUpdateSectionSuccessMessage(String sectionID, String professorName) {
        out.println("Section " + sectionID + " now assigned to Professor " + professorName + " successfully.");
    }
    public void showUpdateClassNameConfirmation(String oldClassName, String newClassName){
        out.println("Successfully update the class name: " + oldClassName + " to: " + newClassName);
    }
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
