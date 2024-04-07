// package edu.duke.ece651.team1.data_access.professor;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.util.List;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import edu.duke.ece651.team1.shared.Professor;

// public class ProfessorDaoImpTest {
//     private Connection connection;
//     private ProfessorDaoImp professorDao;

//     private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
//     private static final String USER = "ece651";
//     private static final String PASSWORD = "passw0rd";

//     @BeforeEach
//     public void setUp() throws SQLException {
//         connection = DriverManager.getConnection(URL, USER, PASSWORD);
//         professorDao = new ProfessorDaoImp();
//     }

//     @AfterEach
//     public void tearDown() throws SQLException {
//         connection.close();
//     }

//     @Test
//     public void testAddProfessor() throws SQLException {
//         Professor professor = new Professor(1, "John Doe", "john.doe@example.com");
//         professorDao.addProfessor(professor);

//         Professor retrievedProfessor = professorDao.getProfessorById(1);
//         assertNotNull(retrievedProfessor);
//         assertEquals(professor.getLegalName(), retrievedProfessor.getLegalName());
//         assertEquals(professor.getEmail(), retrievedProfessor.getEmail());
//     }

//     @Test
//     public void testRemoveProfessor() throws SQLException {
//         // Add a professor first
//         Professor professor = new Professor(1, "John Doe", "john.doe@example.com");
//         professorDao.addProfessor(professor);
//         professorDao.removeProfessor(1);
//         Professor retrievedProfessor = professorDao.getProfessorById(1);
//     }

//     @Test
//     public void testGetAllProfessors() throws SQLException {
//         // Add some professors
//         Professor professor1 = new Professor(1, "John Doe", "john.doe@example.com");
//         Professor professor2 = new Professor(2, "Jane Smith", "jane.smith@example.com");
//         professorDao.addProfessor(professor1);
//         professorDao.addProfessor(professor2);

//         List<Professor> professors = professorDao.getAllProfessors();
//     }
// }

// //
// //import static org.junit.jupiter.api.Assertions.assertNull;
// //import static org.junit.jupiter.api.Assertions.assertTrue;
// //
// //import edu.duke.ece651.team1.shared.Professor;
// //import org.junit.jupiter.api.Test;
// //
// //class ProfessorDaoImpTest {
// //
// //    @Test
// //    void testAddProfessor() {
// //        ProfessorDaoImp professorDaoImp = new ProfessorDaoImp();
// //        professorDaoImp.addProfessor(new Professor("GG Bond"));
// //    }
// //
// //    @Test
// //    void testRemoveProfessor() {
// //        (new ProfessorDaoImp()).removeProfessor(1);
// //    }
// //
// //    @Test
// //    void testGetAllProfessors() {
// //        assertTrue((new ProfessorDaoImp()).getAllProfessors().isEmpty());
// //    }
// //
// //    @Test
// //    void testGetProfessorById() {
// //        assertNull((new ProfessorDaoImp()).getProfessorById(1));
// //    }
// //}
