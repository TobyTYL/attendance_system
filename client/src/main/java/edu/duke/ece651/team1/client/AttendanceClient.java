package edu.duke.ece651.team1.client;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;

import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;
public class AttendanceClient {
    private final AttendanceRecord record;
    BufferedReader inputReader;
    final PrintStream out;
    public AttendanceClient(AttendanceRecord record, BufferedReader inputReader, PrintStream out) {
        this.record = record;
        this.inputReader = inputReader;
        this.out = out;
    }
    public AttendanceClient(BufferedReader inputReader, PrintStream out){
        this(new AttendanceRecord(LocalDate.now()), inputReader, out);
    }
    public String readStatusOption(String prompt) throws IOException {
        out.println(prompt);
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
    public void startAttendance(Iterable<Student> students) throws IOException{
        out.println("Welcome to take Attendance for session: "+ record.getSessionDate());
        record.initializeFromRoaster(students);
        for(Student s:students){
          out.println("Student Name: " + s.getDisPlayName());
          while (true) {
            try{
                String statusOption = readStatusOption("Press 'P' to mark Present, 'A' to mark Absent:");
                if(statusOption.equals("P")){
                     record.markPresent(s);
                     out.println("You sucessfully marked "+s.getDisPlayName()+" to Present");
                     break;
                }else if(statusOption.equals("A")){
                     record.markAbsent(s);
                     out.println("You sucessfully marked "+s.getDisPlayName()+" to Absent");
                     break;
                }else{
                    throw new IllegalArgumentException("That action is in valid here");
                }
            }catch(IllegalArgumentException e){
                out.println("Please try again "+e.getMessage());
            }
          }
        }
        out.println("Attendance marking completed.");
        out.println("Attendance records List blow:");
        out.println(record.displayAttendance());
        

    }
}

    



