package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team1.shared.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


public class StudentViewTest {
    private BufferedReader inputReader;
    private PrintStream out;
    private StudentView studentView;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);
        studentView = new StudentView(inputReader, out);
    }

    



}
