package edu.duke.ece651.team1.server.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.skyscreamer.jsonassert.JSONAssert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.duke.ece651.team1.data_access.Course.*;
import edu.duke.ece651.team1.data_access.Section.*;
import edu.duke.ece651.team1.data_access.Enrollment.*;
import edu.duke.ece651.team1.shared.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseDao courseDao;

    @Mock
    private SectionDao sectionDao;

    @Mock
    private EnrollmentDao enrollmentDao;

    @InjectMocks
    private CourseService courseService;

    private List<Section> createMockSectionsForProfessor() {
        List<Section> sections = new ArrayList<>();
        sections.add(new Section(1, 101, 1));
        sections.add(new Section(2, 102, 1));
        return sections;
    }

    private void mockCourseDaoResponses() {
        Course course1 = new Course(101,"ECE101");
        Course course2 = new Course(102, "ECE102");
        when(courseDao.getCourseById(101)).thenReturn(course1);
        when(courseDao.getCourseById(102)).thenReturn(course2);
    }

    private List<Enrollment> createMockEnrollmentsForStudent(int studentId) {
        Enrollment enrollment1 = new Enrollment(1, studentId, 1); // Using sample constructor
        Enrollment enrollment2 = new Enrollment(2, studentId, 2);
        return Arrays.asList(enrollment1, enrollment2);
    }

    @Test
    public void getTaughtCoursesInfoForProfessor_ReturnsCorrectInfo() {
        int professorId = 1;
        List<Section> sections = createMockSectionsForProfessor();
        when(sectionDao.getSectionsByProfessorId(professorId)).thenReturn(sections);
        mockCourseDaoResponses();
        List<String> coursesInfo = courseService.getTaughtCoursesInfoForProfessor(professorId);
        String expectedJson1 = "{\"courseId\":101,\"courseName\":\"ECE101\",\"sectionId\":1}";
        String expectedJson2 = "{\"courseId\":102,\"courseName\":\"ECE102\",\"sectionId\":2}";
        JSONAssert.assertEquals(expectedJson1, coursesInfo.get(0), false);
        JSONAssert.assertEquals(expectedJson2, coursesInfo.get(1), false);
        assertEquals(2, coursesInfo.size());
    }

    @Test
    void getRegisteredCoursesInfoForStudent_ReturnsCorrectInfo() {
        int studentId = 1;
        List<Enrollment> enrollments = createMockEnrollmentsForStudent(studentId);
        when(enrollmentDao.getEnrollmentsByStudentId(studentId)).thenReturn(enrollments);
        when(sectionDao.getSectionById(1)).thenReturn(new Section(1, 101, 1));
        when(sectionDao.getSectionById(2)).thenReturn(new Section(2, 102, 1));
        mockCourseDaoResponses();
        List<String> coursesInfo = courseService.getRegisteredCoursesInfoForStudent(studentId);
        String expectedJson1 = "{\"courseId\":101,\"courseName\":\"ECE101\",\"sectionId\":1}";
        String expectedJson2 = "{\"courseId\":102,\"courseName\":\"ECE102\",\"sectionId\":2}";
        assertEquals(2, coursesInfo.size());
        JSONAssert.assertEquals(expectedJson1, coursesInfo.get(0), false);
        JSONAssert.assertEquals(expectedJson2, coursesInfo.get(1), false);
    }

}
