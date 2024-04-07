package edu.duke.ece651.team1.data_access.Student;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class StudentDaoImp implements StudentDao {
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

    private boolean userExists(Connection conn, int userId) throws SQLException {
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
                String displayName = rs.getString("DisplayName");
                String email = rs.getString("Email");
                optionalStudent = Optional.of(new Student(studentID, legalName, displayName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalStudent;
    }

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
                Student student = new Student(studentID, legalName, displayName, email);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
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
                optionalStudent = Optional.of(new Student(studentID, legalName, displayName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalStudent;
    }
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
    // new one
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

