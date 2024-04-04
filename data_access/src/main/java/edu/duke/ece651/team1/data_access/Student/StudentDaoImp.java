package edu.duke.ece651.team1.data_access.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Student;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;


public class StudentDaoImp {
    public static Optional<Student> findStudentByStudentID(long studentID) throws SQLException{
        String sql = "SELECT * FROM Students WHERE StudentID = ?";
        try(PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)){
            statement.setLong(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String legalName = resultSet.getString("LegalName");
                String displayName = resultSet.getString("DisplayName");
                String email = resultSet.getString("Email");
                return Optional.of(new Student(studentID, legalName, displayName, email));
            }else{
                return Optional.empty();
            }
        }
    }
}