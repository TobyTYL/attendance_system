package edu.duke.ece651.team1.enrollmentApp.controller;
import edu.duke.ece651.team1.enrollmentApp.view.CourseView;
import edu.duke.ece651.team1.enrollmentApp.view.SectionView;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
public class SectionController {
    private final SectionDao sectionDao;
    private final CourseDao courseDao;
    private final ProfessorDao professorDao; 
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final SectionView sectionView;

    public SectionController(BufferedReader inputReader, PrintStream out, CourseDao courseDao, ProfessorDao professorDao) {
        this.inputReader = inputReader;
        this.out = out;
        this.sectionView = new SectionView(inputReader, out);
        this.sectionDao = new SectionDaoImpl();
        this.courseDao = courseDao;
        this.professorDao = professorDao;
    }

    public void startSectionManagement(String className) throws IOException, SQLException {
        // Assume class exists check has been done prior
        sectionView.showClassSectionOptions(className);
        while (true) {
            int option = sectionView.getSectionManageChoice();
            switch (option) {
                case 1:
                    addSection(className);
                    break;
                case 2:
                    removeSection(className);
                    break;
                case 3:
                    updateSection(className);
                    break;
                case 5:
                    return; // Return to previous menu
                default:
                    out.println("Invalid option. Please try again.");
            }
        }
    }
    
    public void addSection(String className) throws IOException {
        Professor professor;
        String professorIdentifier = sectionView.getProfessorDetailsForSection();
        int professorId;

        // Check if the input is an integer (professor ID)
        if (professorIdentifier.matches("\\d+")) {
            professorId = Integer.parseInt(professorIdentifier);
        } else {
            // If it's not an ID, assume it's a name and get the ID from the database
            int userId = Integer.parseInt(professorIdentifier);
            professor = professorDao.findProfessorByUsrID(userId);
            if (professor == null) {
                out.println("Professor with name " + professorIdentifier + " not found.");
                return;
            }
            professorId = professor.getUserId();
        }

        int classId = courseDao.getClassIdByName(className);
        if (classId == -1) {
            out.println("Class " + className + " not found.");
            return;
        }

        // Create a new Section object
        Section newSection = new Section(classId, professorId);

        // Add the new section to the database
        sectionDao.addSection(newSection);

        // Inform the user
        sectionView.showAddSectionSuccessMessage(className, professorIdentifier);
    }

    private void removeSection(String className) throws IOException {
        // Code to handle removing a section
        int sectionID = sectionView.getSectionToRemove(className);
        if (sectionView.confirmAction("remove", sectionID)) {
            sectionDao.deleteSection(sectionID);
            sectionView.showRemoveSectionSuccessMessage(sectionID, className);
        }
    }

    private void updateSection(String className) throws IOException, SQLException {
        // Code to handle updating a section
        String sectionID = sectionView.getSectionToUpdate(className);
        int detailOption = sectionView.getDetailToUpdateForSection();
        switch (detailOption) {
            case 1:
                String newProfessorIdOrName = sectionView.getNewProfessorName();
                int sectitionIDInt = Integer.parseInt(sectionID);
                sectionDao.updateSectionProfessor(className, sectitionIDInt, newProfessorIdOrName);
                sectionView.showUpdateSectionSuccessMessage(sectionID, newProfessorIdOrName);
                break;
        }
    }
}
