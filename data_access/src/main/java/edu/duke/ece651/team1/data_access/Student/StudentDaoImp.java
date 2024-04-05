package edu.duke.ece651.team1.data_access.Student;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class StudentDaoImp implements StudentDao {

    @Override
    public void addStudent(Student student,long userId) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "INSERT INTO Students (UserID, LegalName, DisplayName, Email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, userId);
            statement.setString(2, student.getLegalName());
            statement.setString(3, student.getDisPlayName());
            statement.setString(4, student.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudent(long studentID) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "DELETE FROM Students WHERE StudentID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, studentID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> findStudentByStudentID(long studentID) {
        Optional<Student> optionalStudent = Optional.empty();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students WHERE StudentID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, studentID);
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
                long studentID = rs.getLong("StudentID");
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
    public Optional<Student> findStudentByUserID(long userID) {
        Optional<Student> optionalStudent = Optional.empty();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Students WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, userID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long studentID = rs.getLong("StudentID");
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
}

//
//    public static Optional<Student> findStudentByStudentID(long studentID){
//        String sql = "SELECT * FROM Students WHERE StudentID = ?";
//        try(PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)){
//            statement.setLong(1, studentID);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                String legalName = resultSet.getString("LegalName");
//                String displayName = resultSet.getString("DisplayName");
//                String email = resultSet.getString("Email");
//                return Optional.of(new Student(studentID, legalName, displayName, email));
//            }else{
//                return Optional.empty();
//            }
//        }
//    }
