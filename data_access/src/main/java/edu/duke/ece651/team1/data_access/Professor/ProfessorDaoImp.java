package edu.duke.ece651.team1.data_access.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.duke.ece651.team1.shared.Professor;

public class ProfessorDaoImp implements ProfessorDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
    private static final String USER = "ece651";
    private static final String PASSWORD = "passw0rd";

    @Override
    public void addProfessor(Professor professor) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO Professors (UserID) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, professor.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeProfessor(int professorId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM Professors WHERE ProfessorID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, professorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Professor> findAllProfessors() {
        List<Professor> professorList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM Professors";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int professorId = rs.getInt("ProfessorID");
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
    public Professor getProfessorById(int professorId) {
        Professor professor = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM Professors WHERE ProfessorID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, professorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                professor = new Professor(professorId, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professor;
    }
}
