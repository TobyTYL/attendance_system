package edu.duke.ece651.team1.user_admin_app.controller;
import static org.mockito.Mockito.*;

import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.User;
import edu.duke.ece651.team1.user_admin_app.view.ProfessorView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ProfessorControllerTest {
    private BufferedReader inputReader;
    private PrintStream out;
    private ProfessorView professorView;
    private ProfessorController professorController;
    private UserDao userDao;
    private ProfessorDao professorDao;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);

        professorView = mock(ProfessorView.class);
        professorController = new ProfessorController(inputReader, out);

        userDao = mock(UserDao.class);
        professorDao = mock(ProfessorDao.class);
        professorController.setProfessorDao(professorDao);
        professorController.setUserDao(userDao);
        professorController.setProfessorView(professorView);
    }

    @Test
    void testStartProfessorMenu_AddOption() throws IOException {
        when(professorView.readProfessorOption()).thenReturn("add", "exit");
        professorController.startProfessorMenu();
    }

    @Test
    void testStartProfessorMenu_InvalidOption() throws IOException {
        //String s = inputReader.readLine();
        when(professorView.readProfessorOption()).thenReturn("invalid", "exit");
        professorController.startProfessorMenu();
    }

    @Test
    void testStartProfessorMenu_ExitOption() throws IOException {
        when(professorView.readProfessorOption()).thenReturn("exit");
        professorController.startProfessorMenu();
    }

    @Test
    void testAddProfessor_Success() throws IOException {
        when(professorView.readProfessorName()).thenReturn("Professor Name");
        User user = new User(1, "Professor Name", "passw0rd", "Professor");
        when(userDao.addUser(any(User.class))).thenReturn(1);
        when(userDao.findUserByUsername("Professor Name")).thenReturn(null);
        when(professorDao.checkProfessorExists("Professor Name")).thenReturn(false);
        professorController.addProfessor();
    }

    @Test
    void testRemoveProfessor_Success() throws IOException {
//        UserDao spyUserDao = Mockito.spy(new UserDaoImp());
        when(professorView.readProfessorName()).thenReturn("Professor Name");
        User mockUser = new User(1, "Professor Name", "password", "Professor");
        mockUser.setEmail("testEmail");
//        doReturn(mockUser).when(spyUserDao).findUserByUsername(anyString());
        when(userDao.findUserByUsername(anyString())).thenReturn(mockUser);
        Professor mockProfessor = new Professor(1,1, "Professor Name");
        when(professorDao.findProfessorByUsrID(anyInt())).thenReturn(mockProfessor);
        professorController.removeProfessor();
    }
    @Test
    void testAddProfessor_ProfessorExists() throws IOException {
        ProfessorView professorView = mock(ProfessorView.class);
        when(professorView.readProfessorName()).thenReturn("Professor Name");
        UserDao userDao = mock(UserDao.class);
        when(userDao.addUser(any(User.class))).thenReturn(1);
        when(userDao.findUserByUsername("Professor Name")).thenReturn(null);
        ProfessorDao professorDao = mock(ProfessorDao.class);
        when(professorDao.checkProfessorExists("Professor Name")).thenReturn(true);
        ProfessorController professorController = new ProfessorController(mock(BufferedReader.class), mock(PrintStream.class));
        professorController.setProfessorView(professorView);
        professorController.setUserDao(userDao);
        professorController.setProfessorDao(professorDao);
        professorController.addProfessor();
    }
}
