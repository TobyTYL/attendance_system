package edu.duke.ece651.team1.server.service;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import edu.duke.ece651.team1.data_access.*;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDao;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.shared.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
/**
 * This class provides services related to courses, such as retrieving course information for professors and students.
 */
@Service
public class CourseService {
     CourseDao courseDao = new CourseDaoImp();
     SectionDao sectionDao = new SectionDaoImpl();
     EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
    /**
     * Retrieves information about courses taught by a professor.
     *
     * @param professorId The ID of the professor.
     * @return A list of JSON strings containing course information.
     */
    public List<String> getTaughtCoursesInfoForProfessor(int professorId){
        List<Section> sections = sectionDao.getSectionsByProfessorId(professorId);
        Gson gson = new Gson();
        List<String> coursesJson = sections.stream()
        .map(section -> {
            Course course = courseDao.getCourseById(section.getClassId());
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("courseId", course.getID());
            courseInfo.put("courseName", course.getName());
            courseInfo.put("sectionId", section.getSectionId());
            return courseInfo;
        }).map(gson::toJson)
        .collect(Collectors.toList());
        return coursesJson;
    }
     /**
     * Retrieves information about courses registered by a student.
     *
     * @param studentID The ID of the student.
     * @return A list of JSON strings containing course information.
     */
    public List<String> getRegisteredCoursesInfoForStudent(int studentID){
        //getenrollement by student id
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudentId(studentID);
        //each enrollment can get section id
        List<Section> sections = enrollments.stream().map(enrollment->sectionDao.getSectionById(enrollment.getSectionId())).collect(Collectors.toList());
        //each section can get course id
        Gson gson = new Gson();
        List<String> coursesJson = sections.stream()
        .map(section -> {
            Course course = courseDao.getCourseById(section.getClassId());
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("courseId", course.getID());
            courseInfo.put("courseName", course.getName());
            courseInfo.put("sectionId", section.getSectionId());
            return courseInfo;
        }).map(gson::toJson)
        .collect(Collectors.toList());
        return coursesJson;
    }
}
