package edu.duke.ece651.team1.data_access.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.*;

public class CourseDaoImp implements CourseDao{
   
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
    @Override
    public void addCourse(Course course) throws IllegalArgumentException {
        // Check if the course name is valid
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        
        // Modify the SQL query to only specify the classname column
        String sql = "INSERT INTO classes (classname) VALUES (?)";
        try (Connection conn = DB_connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set the classname parameter
            ps.setString(1, course.getName());
            
            // Execute the update
            ps.executeUpdate();
            
            // Retrieve the generated classid
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Set the generated id back to the course object
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
}
