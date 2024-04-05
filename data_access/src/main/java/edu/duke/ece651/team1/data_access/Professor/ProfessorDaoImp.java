package edu.duke.ece651.team1.data_access.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Professor;

public class ProfessorDaoImp implements ProfessorDao {
    // private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
    // private static final String USER = "ece651";
    // private static final String PASSWORD = "passw0rd";

    @Override
    public void addProfessor(Professor professor) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "INSERT INTO Professors (UserID) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, professor.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeProfessor(long professorId) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "DELETE FROM Professors WHERE ProfessorID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, professorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Professor> findAllProfessors() {
        List<Professor> professorList = new ArrayList<>();
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Professors";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long professorId = rs.getLong("ProfessorID");
                int userId = rs.getInt("UserID");
                Professor professor = new Professor(professorId, userId);
                professorList.add(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professorList;
    }

    @Override
    public Professor getProfessorById(long professorId) {
        Professor professor = null;
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Professors WHERE ProfessorID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, professorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long userId = rs.getLong("UserID");
                professor = new Professor(professorId, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professor;
    }

    @Override
    public Professor findProfessorByUsrID(long userID) {
        // TODO Auto-generated method stub
        Professor professor = null;
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Professors WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, userID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long professorId = rs.getLong("ProfessorID");
                professor = new Professor(professorId, userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professor;
    }
}
