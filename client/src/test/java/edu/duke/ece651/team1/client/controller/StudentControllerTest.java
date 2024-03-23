package edu.duke.ece651.team1.client.controller;

import edu.duke.ece651.team1.shared.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {
    public static String parseStudent(Student s) {
        return "legal: " + s.getLegalName() + ", display: " + s.getDisPlayName() + ", email: " + s.getEmail();
    }

    @Test
    public void testReadCSV1() throws IOException {
        // this file does not contain header and contains 2 rows
        System.out.println("testing new csv loading methods, doing test 1");
        Iterable<Student> res = StudentController.readCSV("src\\test\\resources\\student1.csv");
        for (Student s: res) {
            System.out.println(parseStudent(s));
        }
    }

    @Test
    public void testReadCSV2() throws IOException {
        // this file does not contain header and contains 3 rows (legal, display, email)
        System.out.println("testing new csv loading methods, doing test 2");
        Iterable<Student> res = StudentController.readCSV("src\\test\\resources\\student2.csv");
        for (Student s: res) {
            System.out.println(parseStudent(s));
        }
    }

    @Test
    public void testReadCSV3() throws IOException {
        // this file does not contain header and contains 3 rows (legal, email, display)
        System.out.println("testing new csv loading methods, doing test 3");
        Iterable<Student> res = StudentController.readCSV("src\\test\\resources\\student3.csv");
        for (Student s: res) {
            System.out.println(parseStudent(s));
        }
    }

    @Test
    public void testReadCSV4() throws IOException {
        // this file does not contain header and contains 2 rows (legal, email) plus headers
        System.out.println("testing new csv loading methods, doing test 4");
        Iterable<Student> res = StudentController.readCSV("src\\test\\resources\\student4.csv");
        for (Student s: res) {
            System.out.println(parseStudent(s));
        }
    }

    @Test
    public void testReadCSV5() throws IOException {
        // this file does not contain header and contains 3 rows (legal, display, email), with header
        System.out.println("testing new csv loading methods, doing test 5");
        Iterable<Student> res = StudentController.readCSV("src\\test\\resources\\student5.csv");
        for (Student s: res) {
            System.out.println(parseStudent(s));
        }
    }

    @Test
    public void testReadCSV6() throws IOException {
        // this file does not contain header and contains 3 rows (legal, email, display)
        System.out.println("testing new csv loading methods, doing test 6");
        Iterable<Student> res = StudentController.readCSV("src\\test\\resources\\student6.csv");
        for (Student s: res) {
            System.out.println(parseStudent(s));
        }
    }

}