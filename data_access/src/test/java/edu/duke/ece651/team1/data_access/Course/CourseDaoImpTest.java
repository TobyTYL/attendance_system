package edu.duke.ece651.team1.data_access;

import edu.duke.ece651.team1.shared.Course;
//import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CourseDaoImpTest {
  private EmbeddedPostgres embeddedPostgres;
  private Connection conn;
  private CourseDaoImp courseDao;

  @BeforeEach
    public void setUp() throws Exception {
        // Start the embedded PostgreSQL
        embeddedPostgres = EmbeddedPostgres.start();
        // Get a connection to the embedded database
        conn = embeddedPostgres.getPostgresDatabase().getConnection();
        // Create table and insert some test data
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE classes (classid SERIAL PRIMARY KEY, classname VARCHAR(255))");
            stmt.execute("INSERT INTO classes (classname) VALUES ('Test Course')");
        }
        // Initialize the DAO implementation
        courseDao = new CourseDaoImp(conn);
    }

    @AfterEach
    public void tearDown() throws Exception {
        conn.close();
        embeddedPostgres.close();
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = courseDao.getAllCourses();
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertEquals("Test Course", courses.get(0).getName());
    }

    @Test
    public void testAddAndRetrieveCourse() throws Exception {
        Course newCourse = new Course(2); // Assuming ID is auto-incremented and starts at 1
        newCourse.setName("Another Course");
        courseDao.addCourse(newCourse);

        Course retrievedCourse = courseDao.getCourseById(2);
        assertNotNull(retrievedCourse);
        assertEquals("Another Course", retrievedCourse.getName());
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Course courseToUpdate = courseDao.getCourseById(1);
        assertNotNull(courseToUpdate);
        courseToUpdate.setName("Updated Course");
        courseDao.updateCourse(courseToUpdate);

        Course updatedCourse = courseDao.getCourseById(1);
        assertNotNull(updatedCourse);
        assertEquals("Updated Course", updatedCourse.getName());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        courseDao.deleteCourse(1);
        Course deletedCourse = courseDao.getCourseById(1);
        assertNull(deletedCourse);
    }
    @Test
    public void testAddCourseWithNullNameThrowsException() {
        Course newCourse = new Course(1); // Assuming an ID of 1 for simplicity
        newCourse.setName(null); // Explicitly setting the name to null
        assertThrows(IllegalArgumentException.class, () -> courseDao.addCourse(newCourse),
            "Expected an IllegalArgumentException to be thrown when adding a course with null name");
    }
    @Test
    public void testUpdateNonExistentCourse() {
        Course courseToUpdate = new Course(999); // Use a non-existent course ID
        courseToUpdate.setName("Non Existent Course");
        // Depending on your DB setup, this might not throw an SQLException directly unless there are constraints/triggers
        assertDoesNotThrow(() -> courseDao.updateCourse(courseToUpdate));
    }

    @Test
    public void testDeleteNonExistentCourse() {
        // Similarly, this might not directly result in SQLException
        assertDoesNotThrow(() -> courseDao.deleteCourse(999));
    }

}
