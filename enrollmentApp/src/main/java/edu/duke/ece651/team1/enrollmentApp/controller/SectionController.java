package edu.duke.ece651.team1.enrollmentApp.controller;
import edu.duke.ece651.team1.enrollmentApp.view.CourseView;
import edu.duke.ece651.team1.enrollmentApp.view.SectionView;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;
public class SectionController {
    private final SectionDao sectionDao;
    private final CourseDao courseDao;
    private final ProfessorDao professorDao; 
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final SectionView sectionView;

    public SectionController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.sectionView = new SectionView(inputReader, out);
        this.sectionDao = new SectionDaoImpl();
        this.courseDao = new CourseDaoImp();
        this.professorDao = new ProfessorDaoImp() {
            
        };
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
        List<Professor> professors = professorDao.findAllProfessors();
        sectionView.showAllProfessors(professors);
        String professorName = sectionView.getProfessorDetailsForSection();
        // Attempt to find the professor by name
        Professor professor = professorDao.findProfessorByName(professorName);
        if (professor == null) {
            out.println("Professor with name " + professorName + " not found. Please add the professor first.");
            return;
        }
        
        int professorId = professor.getProfessorId();
        
        int classId = courseDao.getClassIdByName(className);
        if (classId == -1) {
            out.println("Class " + className + " not found.");
            return;
        }
        
        Section newSection = new Section(classId, professorId);
        sectionDao.addSection(newSection);
        sectionView.showAddSectionSuccessMessage(className, professorName);
    }
   
   
    protected void removeSection(String className) throws IOException {
        // Code to handle removing a section
        List<Section> sections = sectionDao.getAllSections();
        sectionView.showAllSections(sections);
        int sectionID = sectionView.getSectionToRemove(className);
        if (sectionView.confirmAction("remove", sectionID)) {
            sectionDao.deleteSection(sectionID);
            sectionView.showRemoveSectionSuccessMessage(sectionID, className);
        }
    }

    protected void updateSection(String className) throws IOException, SQLException {
        // Code to handle updating a section
        List<Section> sections = sectionDao.getAllSections();
        sectionView.showAllSections(sections);
        String sectionID = sectionView.getSectionToUpdate(className);
        int detailOption = sectionView.getDetailToUpdateForSection();
        switch (detailOption) {
            case 1:
                List<Professor> professors = professorDao.findAllProfessors();
                sectionView.showAllProfessors(professors);
                String newProfessorIdOrName = sectionView.getNewProfessorName();
                int sectitionIDInt = Integer.parseInt(sectionID);
                if (!sectionDao.checkSectionExists(sectitionIDInt)) {
                    out.println("Section do not exists!");
                    return;
                }

                String newProfessorName = sectionView.getNewProfessorName();
                Professor professor = professorDao.findProfessorByName(newProfessorName);
                if (professor == null) {
                    out.println("Professor with name " + newProfessorName + " not found. Please add the professor first.");
                    return;
                }
                int newProfessorId = professor.getProfessorId();
                sectionDao.updateSectionProfessor(sectionID, sectitionIDInt, Integer.toString(newProfessorId));
                sectionView.showUpdateSectionSuccessMessage(String.valueOf(sectionID), newProfessorName);
                break;

                // sectionDao.updateSectionProfessor(className, sectitionIDInt, newProfessorIdOrName);
                // if(sectionDao.checkSectionExists(sectitionIDInt)){
                //     sectionView.showUpdateSectionSuccessMessage(sectionID, newProfessorIdOrName);
                // }
                // out.println("Action failed, please try again.");
                // break;
        }
    }
}
