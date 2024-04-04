package edu.duke.ece651.team1.data_access;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class DB_connect {
    private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
    private static final String USER = "ece651";
    private static final String PASSWORD = "passw0rd";
    public static Connection getConnection()  {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("Connected to the PostgreSQL server successfully.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void createAllTable(){
        String filePath = "createTable.sql";
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                    DB_connect.class.getClassLoader().getResourceAsStream(filePath)))) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
            if (line.endsWith(";")) {
                try (Connection conn = getConnection();
                     Statement stmt = conn.createStatement()) {
                    stmt.execute(sb.toString());
                    sb = new StringBuilder(); 
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Failed to read the SQL file: " + e.getMessage());
    }
    }

    public static void main(String[] args) {
        createAllTable();
    }


}
