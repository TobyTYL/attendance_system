package edu.duke.ece651.team1.data_access.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Course;
/**
 * The CourseDaoImpl class provides implementation for accessing course data in the database.
 */
public class CourseDaoImp implements CourseDao{
    /**
     * Retrieves a list of all courses from the database.
     *
     * @return A list of all courses.
     */
    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT classid, classname FROM classes";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(rs.getInt("classid"));
                course.setName(rs.getString("classname"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    /**
     * Retrieves a course by its ID from the database.
     *
     * @param id The ID of the course.
     * @return The course with the specified ID, or null if not found.
     */
    @Override
    public Course getCourseById(int id) {
        String sql = "SELECT classid, classname FROM classes WHERE classid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course(rs.getInt("classid"));
                    course.setName(rs.getString("classname"));
                    return course;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves the class ID by its name from the database.
     *
     * @param className The name of the class.
     * @return The ID of the class, or -1 if not found.
     */
    @Override
    public int getClassIdByName(String className) {
        String sql = "SELECT ClassID FROM classes WHERE ClassName = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setString(1, className);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("classid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicating not found
    }
    /**
     * Adds a new course to the database.
     *
     * @param course The course to add.
     * @throws IllegalArgumentException If the course name is null or empty.
     */
    @Override
    public void addCourse(Course course) throws IllegalArgumentException {
        // Check if the course name is valid
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        String sql = "INSERT INTO classes (classname) VALUES (?)";
        try (Connection conn = DB_connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getName());
            ps.executeUpdate();
            // Retrieve the generated classid
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating course failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider rethrowing as a runtime exception or a custom exception
            // throw new RuntimeException("Database operation failed", e);
        }
    }
    /**
     * Updates an existing course in the database.
     *
     * @param course The course to update.
     */
    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE classes SET classname = ? WHERE classid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setString(1, course.getName());
            ps.setLong(2, course.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes a course by its ID from the database.
     *
     * @param id The ID of the course to delete.
     */
    @Override
    public void deleteCourse(int id) {
        String sql = "DELETE FROM classes WHERE classid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes a course by its name from the database.
     *
     * @param className The name of the course to delete.
     */
    @Override
    public void deleteCourse(String className) {
        String sql = "DELETE FROM classes WHERE classname = ?";
        try (Connection conn = DB_connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, className);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
        }
    }
    /**
     * Checks if a course with the given name exists in the database.
     *
     * @param className The name of the course to check.
     * @return True if the course exists, otherwise false.
     */
    @Override
    public boolean checkCourseExists(String className) {
        String sql = "SELECT COUNT(*) FROM classes WHERE classname = ?";
        try (Connection conn = DB_connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, className);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if course exists: " + e.getMessage());
        }
        return false;
    }
    /**
     * Updates the class name in the database.
     *
     * @param oldClassName The old name of the class.
     * @param newClassName The new name of the class.
     */
    @Override
    public void updateClassName(String oldClassName, String newClassName) {
        String sql = "UPDATE classes SET classname = ? WHERE classname = ?";
        try (Connection conn = DB_connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newClassName);
            ps.setString(2, oldClassName);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating class name: " + e.getMessage());
        }
    }
    /**
     * Retrieves a course by its name from the database.
     *
     * @param name The name of the course.
     * @return The course with the specified name, or null if not found.
     */
    @Override
    public Course getClassByName(String name) {
        Course course = null;
        String sql = "SELECT classid, classname FROM classes WHERE classname = ?";
        try (Connection conn = DB_connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    course = new Course(rs.getInt("classid"), rs.getString("classname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }
}