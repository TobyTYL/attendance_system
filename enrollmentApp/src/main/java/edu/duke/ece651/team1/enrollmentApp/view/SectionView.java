package edu.duke.ece651.team1.enrollmentApp.view;
import edu.duke.ece651.team1.client.view.ViewUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Predicate;
public class SectionView {
    private BufferedReader inputReader;
    private final PrintStream out;

    public SectionView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showClassSectionOptions(String className) {
        out.println("\nManage Sections for " + className + ":");
        out.println("1. Add a Section");
        out.println("2. Remove a Section");
        out.println("3. Update a Section");
        out.println("5. Return to Previous Menu");
    }

    public int getSectionManageChoice() throws IOException {
        return ViewUtils.getUserOption(inputReader, out, 5);
    }

    public int getSectionToRemove(String className) throws IOException {
        String ans = ViewUtils.getUserInput(
                "Enter the SectionID/Professor name of the section you wish to remove from " + className + ": ",
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
                "Enter the Professor ID or name who will be instructing this section: ",
                "Professor ID or name cannot be empty. Please enter a valid ID or name: ",
                inputReader,
                out,
                input -> !input.trim().isEmpty()
        );
    }

    public void showAddSectionSuccessMessage(String className, String professorName) {
        out.println("New section added to " + className + " with Professor " + professorName + " successfully.");
    }

    public void showRemoveSectionSuccessMessage(int sectionID, String className) {
        out.println("Section " + sectionID + " removed successfully from " + className + ".");
    }

    public int getDetailToUpdateForSection() throws IOException {
        out.println("\nWhat would you like to update for the section?");
        out.println("1. Professor");
        out.print("Enter your choice: ");
        return ViewUtils.getUserOption(inputReader, out, 1);
    }

    public String getNewProfessorName() throws IOException {
        return ViewUtils.getUserInput(
                "\nEnter the new Professor ID or name for the section: ",
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


}
