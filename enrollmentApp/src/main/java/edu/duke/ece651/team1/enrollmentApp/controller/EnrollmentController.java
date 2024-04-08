package edu.duke.ece651.team1.enrollmentApp.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.*;

import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.enrollmentApp.view.EnrollmentView;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Enrollment;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.shared.Student;

public class EnrollmentController {
    private EnrollmentView enrollmentView;
    private CourseDao courseDao;
    private SectionDao sectionDao;
    private EnrollmentDaoImpl enrollmentDao;
    private StudentDao studentDao;

    public EnrollmentController(BufferedReader inputReader, PrintStream out) {
        this.enrollmentView = new EnrollmentView(inputReader, out);
        this.courseDao = new CourseDaoImp();
        this.sectionDao = new SectionDaoImpl();
        this.enrollmentDao = new EnrollmentDaoImpl();
        this.studentDao = new StudentDaoImp();
    }

    public void startEnrollment() throws IOException {
        enrollmentView.showEnrollmentOptions();
        int choice = enrollmentView.getEnrollmentChoice();
        switch (choice) {
            case 1:
                manuallyEnrollStudent();
                break;
            case 2:
                // batchEnrollStudents();
                break;
            case 3:
                return; // Exit or go back to the previous menu
            default:
                // Handle invalid choice
                break;
        }
    }

    private void manuallyEnrollStudent() throws IOException {
        String studentIdStr = enrollmentView.getStudentIDForEnrollment();
        int studentId;
        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid student ID format.");
            return;
        }
        // Check if student exists in the database
        Optional<Student> studentOptional = studentDao.findStudentByStudentID(studentId);
        if (!studentOptional.isPresent()) {
            System.out.println("Student ID not found. Please register the student first.");
            return;
        }

        List<Course> courses = courseDao.getAllCourses();
        enrollmentView.showAllCourses(courses);
        
        String selectedCourseName = enrollmentView.getClassForEnrollment();
        Course course = courseDao.getClassByName(selectedCourseName);
        if(course == null){
            System.out.println("Course not found. Please try again.");
            return;
        }
    
        List<Section> sections = sectionDao.getSectionsByClassId(course.getID());
        enrollmentView.showAllSections(sections);
    
        int selectedSectionId = enrollmentView.getSectionChoice();
        Section selectedSection = sectionDao.getSectionById(selectedSectionId);
        if(selectedSection == null){
            System.out.println("Section not found. Please try again.");
            return;
        }
    
        if(enrollmentDao.isStudentAlreadyEnrolled(studentId, selectedSectionId)) {
            System.out.println("Student is already enrolled in this section.");
            return;
        }
    
        Enrollment newEnrollment = new Enrollment(studentId, selectedSectionId);
        enrollmentDao.addEnrollment(newEnrollment);
        System.out.println("Student " + studentId + " successfully enrolled in Section ID: " + selectedSectionId);
    }
}
