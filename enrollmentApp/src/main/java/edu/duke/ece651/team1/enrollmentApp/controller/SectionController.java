package edu.duke.ece651.team1.enrollmentApp.controller;
import edu.duke.ece651.team1.enrollmentApp.view.CourseView;
import edu.duke.ece651.team1.enrollmentApp.view.SectionView;
import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;
/**
* Manages the operations related to sections including adding, removing, and updating section details.
*/
public class SectionController {
   private final SectionDao sectionDao;
   private final CourseDao courseDao;
   private final ProfessorDao professorDao;
   private final BufferedReader inputReader;
   private final PrintStream out;
   protected final SectionView sectionView;

   public SectionController(BufferedReader inputReader, PrintStream out) {
       this.inputReader = inputReader;
       this.out = out;
       this.sectionView = new SectionView(inputReader, out);
       this.sectionDao = new SectionDaoImpl();
       this.courseDao = new CourseDaoImp();
       this.professorDao = new ProfessorDaoImp();
   }
   /**
    * Constructs a SectionController with necessary data access objects and I/O components.
    *
    * @param inputReader The BufferedReader to handle user input.
    * @param out The PrintStream to handle output to the user.
    * @param sectionDao An implementation of SectionDao for database operations related to sections.
    * @param courseDao An implementation of CourseDao for database operations related to courses.
    * @param professorDao An implementation of ProfessorDao for database operations related to professors.
    */
   public SectionController(BufferedReader inputReader, PrintStream out, SectionDao sectionDao, CourseDao courseDao, ProfessorDao professorDao) {
       this.inputReader = inputReader;
       this.out = out;
       this.sectionView = new SectionView(inputReader, out);
       this.sectionDao = sectionDao;
       this.courseDao = courseDao;
       this.professorDao = professorDao;
   }
   /**
    * Initiates section management for a given class, allowing the addition, removal, and updating of sections.
    *
    * @param className The name of the class for which section management is to be performed.
    * @throws IOException If an input or output exception occurred.
    * @throws SQLException If a database access error occurs.
    */
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
   /**
    * Facilitates the addition of a new section under a given class name, allowing the user to select a professor.
    *
    * @param className The class name under which the section will be added.
    * @throws IOException If an input or output exception occurred.
    */
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
       // error here
       int classId = courseDao.getClassIdByName(className);
       if (classId == -1) {
           out.println("Class " + className + " not found.");
           return;
       }

       Section newSection = new Section(classId, professorId);
       sectionDao.addSection(newSection);
       sectionView.showAddSectionSuccessMessage(className, professorName);
   }

   /**
    * Handles the removal of a section based on user selection from a displayed list.
    *
    * @param className The class name under which sections are to be managed.
    * @throws IOException If an input or output exception occurred.
    */
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
   /**
    * Manages updates to existing sections such as changing the professor.
    *
    * @param className The class name for which sections are managed.
    * @throws IOException If an input or output exception occurred.
    * @throws SQLException If a database access error occurs.
    */
   protected void updateSection(String className) throws IOException, SQLException {
       // Code to handle updating a section
       //List<Section> sections = sectionDao.getAllSections();
       int classID = courseDao.getClassIdByName(className);
       List<Section> sections = sectionDao.getSectionsByClassId(classID);
       sectionView.showAllSections(sections);
       String sectionID = sectionView.getSectionToUpdate(className);
       int detailOption = sectionView.getDetailToUpdateForSection();
       switch (detailOption) {
           case 1:
               List<Professor> professors = professorDao.findAllProfessors();
               sectionView.showAllProfessors(professors);
               // edit here
               String newProfessorName = sectionView.getNewProfessorName();
               int sectitionIDInt = Integer.parseInt(sectionID);

//                String newProfessorID = sectionView.getNewProfessorName();
//                int sectitionIDInt = Integer.parseInt(sectionID);
               if (!sectionDao.checkSectionExists(sectitionIDInt)) {
                   out.println("Section do not exists!");
                   return;
               }

//                String newProfessorName = sectionView.getNewProfessorName();

               Professor professor = professorDao.findProfessorByName(newProfessorName);
               if (professor == null) {
                   out.println("Professor with name " + newProfessorName + " not found. Please add the professor first.");
                   return;
               }
               // edit here
//                String newProfessorID = String.valueOf(professor.getProfessorId());
               Integer newProfessorID = professor.getProfessorId();
               sectionDao.updateSectionProfessor(className, sectitionIDInt, newProfessorID);
               sectionView.showUpdateSectionSuccessMessage(sectionID, newProfessorName);

//                sectionDao.updateSectionProfessor(sectionID, sectitionIDInt, Integer.toString(newProfessorId));
//                sectionView.showUpdateSectionSuccessMessage(String.valueOf(sectionID), newProfessorName);
               break;

       }
   }


}
