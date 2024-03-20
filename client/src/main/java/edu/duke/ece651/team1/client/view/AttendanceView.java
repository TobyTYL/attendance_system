package edu.duke.ece651.team1.client.view;

import java.io.InputStreamReader;
import java.io.PrintStream;

import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
public class AttendanceView {
    BufferedReader inputReader;
    private final PrintStream out;

    public AttendanceView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showTakeAttendanceMenu() {
        out.println("========== Take Attendance ==========");
        out.println("For each student, please mark their attendance status as follows:");
        out.println("  P - Present");
        out.println("  A - Absent");
        out.println("  T - Tardy");
        out.println("=====================================");
        out.println("Please enter the attendance status for each student listed below:");
    }

    public String promptForStudentAttendance(String studentName) throws  IOException{
        out.println("Student Name: " + studentName);
        out.println("Press 'P' to mark Present, 'A' to mark Absent:");
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("End of input reached");
        }
        s = s.toUpperCase();
        if (!s.equals("A") && !s.equals("P") && !s.equals("T")) {
            throw new IllegalArgumentException("That action is invalid: it does not have the correct format.");
        }
        return s;
    }
    public void showFinalAttendanceRecord(AttendanceRecord record){
        Iterable<Map.Entry<Student, AttendanceStatus>> entryList = record.getSortedEntries();
        out.println("Attendance Record for " +record.getSessionDate());
        out.println("=====================================");
        for (Map.Entry<Student, AttendanceStatus> entry :entryList) {
            Student student = entry.getKey();
            AttendanceStatus status = entry.getValue();
            out.println(student.getDisPlayName() + ": " + status.getStatus());
        }
        out.println("=====================================");
    }
}
