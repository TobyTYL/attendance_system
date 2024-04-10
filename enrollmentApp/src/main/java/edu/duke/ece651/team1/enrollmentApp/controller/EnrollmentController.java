package edu.duke.ece651.team1.enrollmentApp.controller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.*;

import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Notification.NotificationPreference;
import edu.duke.ece651.team1.data_access.Notification.NotificationPreferenceDao;
import edu.duke.ece651.team1.data_access.Notification.NotificationPreferenceDaoImp;
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
    private NotificationPreferenceDao notificationPreferenceDao;
    private final PrintStream out; 

    public EnrollmentController(BufferedReader inputReader, PrintStream out) {
        this.enrollmentView = new EnrollmentView(inputReader, out);
        this.courseDao = new CourseDaoImp();
        this.sectionDao = new SectionDaoImpl();
        this.enrollmentDao = new EnrollmentDaoImpl();
        this.studentDao = new StudentDaoImp();
        this.notificationPreferenceDao = new NotificationPreferenceDaoImp();
        this.out = out;
    }

    public void startEnrollment() throws IOException {
        enrollmentView.showEnrollmentOptions();
        int choice = enrollmentView.getEnrollmentChoice();
        switch (choice) {
            case 1:
                manuallyEnrollStudent();
                break;
            case 2:
                batchEnrollStudent();
                break;
            case 3:
                return; // Exit or go back to the previous menu
            default:
                // Handle invalid choice
                break;
        }
    }
    private boolean enrollStudent(int studentId, int sectionId) {
        // Check if student already enrolled in the section
        if(enrollmentDao.isStudentAlreadyEnrolled(studentId, sectionId)) {
            out.println("Student ID " + studentId + " is already enrolled in Section ID: " + sectionId);
            return false;
        }
    
        // Perform the enrollment
        Enrollment newEnrollment = new Enrollment(studentId, sectionId);
        enrollmentDao.addEnrollment(newEnrollment);
        out.println("Student ID " + studentId + " successfully enrolled in Section ID: " + sectionId);
        int classId = sectionDao.getClassIdBySectionId(sectionId);
        if (classId == -1) {
            out.println("Failed to find the class for the given section. Enrollment failed.");
            return false;
        }

        // Check if notification preference exists, if not add it
        NotificationPreference notificationPreference = notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(studentId, classId);
        if (notificationPreference == null) {
            // No existing preference, add a new one with notifications enabled
            notificationPreferenceDao.addNotificationPreference(studentId, classId, true);
        } else {
            // Existing preference, update it to enable notifications
            notificationPreferenceDao.updateNotificationPreference(studentId, classId, true);
        }
        return true;
    }

    private void manuallyEnrollStudent() throws IOException {
        String studentIdStr = enrollmentView.getStudentIDForEnrollment();
        int studentId;
        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException e) {
            out.println("Invalid student ID format.");
            return;
        }
        // Check if student exists in the database
        Optional<Student> studentOptional = studentDao.findStudentByStudentID(studentId);
        if (!studentOptional.isPresent()) {
            out.println("Student ID not found. Please register the student first.");
            return;
        }

        List<Course> courses = courseDao.getAllCourses();
        enrollmentView.showAllCourses(courses);
        
        String selectedCourseName = enrollmentView.getClassForEnrollment();
        Course course = courseDao.getClassByName(selectedCourseName);
        if(course == null){
            out.println("Course not found. Please try again.");
            return;
        }
    
        List<Section> sections = sectionDao.getSectionsByClassId(course.getID());
        if (sections.isEmpty()) {
            out.println("There are no available sections for this class right now. You can add the section first.");
            return; 
        }
        enrollmentView.showAllSections(sections);
    
        int selectedSectionId = enrollmentView.getSectionChoice();
        Section selectedSection = sectionDao.getSectionById(selectedSectionId);
        if(selectedSection == null){
            out.println("Section not found. Please try again.");
            return;
        }   
        boolean success = enrollStudent(studentId, selectedSectionId);
        if (!success) {
            out.println("Enrollment failed. Please try again.");
        }
    }

    /**
     * required csv format: StudentID,ClassName,SectionID
     * @throws IOException
     */
    private void batchEnrollStudent() throws IOException {
        String csvFileName = enrollmentView.getCsvFilePath();
        String basePath = System.getProperty("user.dir");
        String filePath = basePath + "/src/test/resources/" + csvFileName;

        int successfulEnrollments = 0;
        int failedEnrollments = 0;
        List<String> errors = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] values = line.split(",");
                    if (values.length != 3) {
                        errors.add("Invalid format in line: " + line);
                        failedEnrollments++;
                        continue;
                    }
                    int studentId = Integer.parseInt(values[0].trim());
                    int sectionId = Integer.parseInt(values[2].trim());
                    boolean success = enrollStudent(studentId, sectionId);
                    if (success) {
                        successfulEnrollments++;
                    } else {
                        failedEnrollments++;
                    }
                } catch (NumberFormatException e) {
                    errors.add("Invalid number format in line: " + line);
                    failedEnrollments++;
                } catch (Exception e) {
                    errors.add("Unexpected error in line: " + line);
                    failedEnrollments++;
                }
            }
        } catch (FileNotFoundException e) {
            out.println("File not found: " + filePath);
            return;
        } catch (IOException e) {
            out.println("Error reading file: " + filePath);
            return;
        }
    
        // Report the outcome
        out.println("Batch enrollment process completed.");
        out.println("Results:");
        out.println("- Successfully enrolled " + successfulEnrollments + " students.");
        out.println("- Encountered errors with " + failedEnrollments + " entries.");
        if (!errors.isEmpty()) {
            out.println("Error details:");
            errors.forEach(out::println);
        }
    }
    
}