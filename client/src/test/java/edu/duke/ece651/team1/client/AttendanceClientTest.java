package edu.duke.ece651.team1.client;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.team1.shared.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceClientTest {
     private AttendanceClient createAttendanceClient(String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        return new AttendanceClient(input, output);
       
    }

    @Test 
    public void testReadStatusOption() throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String prompt = "Press 'P' to mark Present, 'A' to mark Absent:";
        AttendanceClient client = createAttendanceClient("a\nT\nP\n", bytes) ;
        String[] expectedOut = new String[]{"A","T","P"};
        for(int i=0; i<2;i++){
            assertEquals(expectedOut[i], client.readStatusOption(prompt));
            assertEquals(prompt+"\n", bytes.toString());
            bytes.reset();
        }
       
    }
    @Test 
    public void testInvalidOption() throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String prompt = "Press 'P' to mark Present, 'A' to mark Absent:";
        AttendanceClient client1 = createAttendanceClient("", bytes) ;
        assertThrows(EOFException.class,()->client1.readStatusOption(prompt));
        AttendanceClient client2 = createAttendanceClient("B", bytes) ;
        assertThrows(IllegalArgumentException.class, ()->client2.readStatusOption(prompt));
    }
    @Test 
    public void testStartAttendance() throws IOException{
       Student huidan = new Student("huidan");
       huidan.updateDisplayName("rachel");
       Student zhecheng = new Student("zhecheng");
       List<Student> students = new ArrayList<>();
        students.add(huidan);
        students.add(zhecheng);
       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
       String prompt = "Press 'P' to mark Present, 'A' to mark Absent:";
       AttendanceClient client1 = createAttendanceClient("A\ns\nP\n", bytes);
       String expected = "Welcome to take Attendance for session: "+LocalDate.now()+"\n"+
                        "Student Name: rachel\n"+
                        "Press 'P' to mark Present, 'A' to mark Absent:\n"+
                        "You sucessfully marked rachel to Absent\n"+
                        "Student Name: zhecheng\n"+
                        "Press 'P' to mark Present, 'A' to mark Absent:\n"+
                        "Please try again That action is invalid: it does not have the correct format.\n"+
                        "Press 'P' to mark Present, 'A' to mark Absent:\n"+
                        "You sucessfully marked zhecheng to Present\n"+
                        "Attendance marking completed.\n"+
                        "Attendance records List blow:\n"+
                        "Attendance Record for "+LocalDate.now()+"\n"+
                        "----------------------------\n"+
                        "rachel: Absent\n"+
                        "zhecheng: Present\n"+
                        "----------------------------\n";
        client1.startAttendance(students);
        assertEquals(expected,bytes.toString() );
    }
}
