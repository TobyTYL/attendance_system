package edu.duke.ece651.team1.enrollmentApp.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;
import edu.duke.ece651.team1.client.view.*;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.shared.User;



public class EnrollmentView {
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final ProfessorDao professorDao;
    private final UserDao userDao;

    public EnrollmentView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.professorDao = new ProfessorDaoImp();
        this.userDao = new UserDaoImp();
    }
    public EnrollmentView(BufferedReader inputReader, PrintStream out, ProfessorDao professorDaoImp, UserDao userDaoImp) {
        this.inputReader = inputReader;
        this.out = out;
        this.professorDao = professorDaoImp;
        this.userDao = userDaoImp;
    }

    public void showEnrollmentOptions() throws IOException {
        out.println("\nEnrollment Options:");
        out.println("1. Manually Enroll a Student");
        out.println("2. Batch Enroll Students (CSV Import)");
        out.println("3. Return to Main Menu");
    }

    public int getEnrollmentChoice() throws IOException {
        return ViewUtils.getUserOption(inputReader, out, 3);
    }

    public void showAllCourses(List<Course> courses) {
        out.println("\nAvailable Courses:");
        courses.forEach(course -> out.println("ID: " + course.getID() + ", Name: " + course.getName()));
    }
    
    public String getStudentIDForEnrollment() throws IOException {
        return ViewUtils.getUserInput(
                "\nEnter the student ID: ",
                "Invalid input. Please enter a valid student ID.",
                inputReader,
                out,
                input -> !input.trim().isEmpty() // Validate that the input is not empty.
        );
    }

    public String getClassForEnrollment() throws IOException {
        return ViewUtils.getUserInput(
                "Enter the name of the class: ",
                "Invalid input. Please enter a valid class name.",
                inputReader,
                out,
                input -> !input.trim().isEmpty() // Validate that the input is not empty.
        );
    }
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
