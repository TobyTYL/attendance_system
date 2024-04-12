package edu.duke.ece651.team1.data_access.Student;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * The StudentDaoImp class provides implementation for accessing student data in the database.
 */
public class StudentDaoImp implements StudentDao {
    /**
     * Adds a new student to the database.
     *
     * @param student The student to be added.
     */
    @Override
    public void addStudent(Student student) {
        try (Connection conn = DB_connect.getConnection()) {
            if (!userExists(conn, student.getUserId())) {
                throw new SQLException("User ID does not exist: " + student.getUserId());
            }
            String sql = "INSERT INTO Students (UserID, LegalName, DisplayName, Email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, student.getUserId());
            statement.setString(2, student.getLegalName());
            statement.setString(3, student.getDisPlayName());
            statement.setString(4, student.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks if a user exists in the database.
     *
     * @param conn   The connection to the database.
     * @param userId The ID of the user to check.
     * @return True if the user exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean userExists(Connection conn, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE UserID = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        } else {
            return false;
        }
    }
    /**
     * Removes a student from the database.
     *
     * @param student The student to be removed.
     */
    @Override
    public void removeStudent(Student student) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "DELETE FROM Students WHERE StudentID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Finds a student in the database by student ID.
     *
     * @param studentID The ID of the student to find.
     * @return An optional containing the student if found, empty otherwise.
     */
    @Override
    public Optional<Student> findStudentByStudentID(int studentID) {
        Optional<Student> optionalStudent = Optional.empty();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students WHERE StudentID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, studentID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String legalName = rs.getString("LegalName");
                int userId = rs.getInt("UserID");
                String displayName = rs.getString("DisplayName");
                String email = rs.getString("Email");
                optionalStudent = Optional.of(new Student(studentID, legalName, displayName, email, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalStudent;
    }
    /**
     * Retrieves all students from the database.
     *
     * @return A list of all students retrieved from the database.
     */
    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("StudentID");
                int userID = rs.getInt("UserID");
                String legalName = rs.getString("LegalName");
                String displayName = rs.getString("DisplayName");
                String email = rs.getString("Email");
                Student student = new Student(studentID, legalName, displayName, email,userID);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
    /**
     * Finds a student in the database by user ID.
     *
     * @param userID The ID of the user associated with the student.
     * @return An optional containing the student if found, empty otherwise.
     */
    @Override
    public Optional<Student> findStudentByUserID(int userID) {
        Optional<Student> optionalStudent = Optional.empty();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int studentID = rs.getInt("StudentID");
                String legalName = rs.getString("LegalName");
                String displayName = rs.getString("DisplayName");
                String email = rs.getString("Email");
                optionalStudent = Optional.of(new Student(studentID, legalName, displayName, email,userID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalStudent;
    }
    /**
     * Checks if a student with the given name exists in the database.
     *
     * @param studentName The name of the student to check.
     * @return True if the student exists, false otherwise.
     */
    @Override
    public boolean checkStudentExists(String studentName) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students WHERE LegalName = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Finds a student in the database by name.
     *
     * @param studentName The name of the student to find.
     * @return An optional containing the student if found, empty otherwise.
     */
    @Override
    public Optional<Student> findStudentByName(String studentName) {
        Optional<Student> optionalStudent = Optional.empty();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students WHERE LegalName = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                Integer studentID = rs.getInt("StudentID");
                String legalName = rs.getString("LegalName");
                String displayName = rs.getString("DisplayName");
                String email = rs.getString("Email");
                optionalStudent = Optional.of(new Student(studentID, legalName, displayName, email, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalStudent;
    }
    /**
     * Updates a student's information in the database.
     *
     * @param student The student whose information to update.
     */
    @Override
    public void updateStudent(Student student) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "UPDATE Students SET DisplayName = ?, Email = ? WHERE StudentID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, student.getDisPlayName());
            statement.setString(2, student.getEmail());
            statement.setInt(3, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

