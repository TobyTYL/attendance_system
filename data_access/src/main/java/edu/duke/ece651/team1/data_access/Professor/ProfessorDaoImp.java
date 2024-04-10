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
    @Override
    public void addProfessor(Professor professor) {
        try (Connection conn = DB_connect.getConnection()) {
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
        try (Connection conn = DB_connect.getConnection()) {
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
        try (Connection conn = DB_connect.getConnection()) {
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
        try (Connection conn = DB_connect.getConnection()) {
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

    @Override
    public Professor findProfessorByUsrID(int userID) {
        Professor professor = null;
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Professors WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int professorId = rs.getInt("ProfessorID");
                professor = new Professor(professorId, userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professor;
    }

    @Override
    public boolean checkProfessorExists(String professorName) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Professors WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            int userId = getUserIDByProfessorName(conn, professorName);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getUserIDByProfessorName(Connection conn, String professorName) throws SQLException {
        String sql = "SELECT UserID FROM Users WHERE Username = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, professorName);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt("UserID");
        } else {
            throw new SQLException("No user found with the given professor name: " + professorName);
        }
    }
    public Professor findProfessorByName(String name) {
        try (Connection conn = DB_connect.getConnection()) {
            // Assuming there's a Users table where professor names are stored.
            String sql = "SELECT p.ProfessorID, p.UserID FROM Professors p JOIN Users u ON p.UserID = u.UserID WHERE u.Username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Professor(rs.getInt("ProfessorID"), rs.getInt("UserID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
