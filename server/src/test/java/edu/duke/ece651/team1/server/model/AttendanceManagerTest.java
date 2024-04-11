package edu.duke.ece651.team1.server.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.team1.shared.*;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.Map;

public class AttendanceManagerTest {

   private AttendanceManager manager;
   private Set<Student> roster;
   private List<AttendanceRecord> records;

   @BeforeEach
   public void setUp() {
       roster = new HashSet<>();
       records = new ArrayList<>();
       // Assume Student class has an appropriate constructor
       Student student1 = new Student("huidan","rachel","huidan@duke.com");
       Student student2 = new Student("yitiao", "yitiao@duke.com");
       Student student3 = new Student("meng", "meng@duke.com");
       roster.add(student1);
       roster.add(student2);
       AttendanceRecord record1 = new AttendanceRecord(LocalDate.of(2024, 03, 24));
       record1.initializeFromRoaster(roster);
       record1.initializeAttendanceEntry(student3);
       record1.updateStudentStatus(student2, AttendanceStatus.PRESENT);
       AttendanceRecord record2 = new AttendanceRecord(LocalDate.of(2024, 03, 27));
       record2.initializeFromRoaster(roster);
       record2.updateStudentStatus(student2, AttendanceStatus.TARDY);
       record2.updateStudentStatus(student1, AttendanceStatus.PRESENT);

       records.add(record1);
       records.add(record2);
       manager = new AttendanceManager( roster, records);
   }

   @Test
   public void testGenerateReport()  {

       Student student1 = new Student("huidan","rachel","huidan@duke.com");
       Student student2 = new Student("yitiao", "yitiao@duke.com");
       String report = manager.generateReport(student1,true);
       String expected_student1 = "Attendance Report for rachel:\n"+
       "2024-03-24: Absent\n"+
       "2024-03-27: Present\n"+
       "Total Attendance: 1/2 (50.00% attendance rate), "+
       "Tardy Count: 0";
       assertEquals(expected_student1,report);
       String expected_student2 = "Attendance Report for yitiao:\n"+
       "Total Attendance: 1/2 (90.00% attendance rate), "+
       "Tardy Count: 1";
       String report2 = manager.generateReport(student2,false);
       assertEquals(expected_student2, report2);

   }

   @Test
   public void testCalculateAttendance() {
       Map<String, AttendanceStatus> testRecords = new TreeMap<>();
       testRecords.put("2024-03-01", AttendanceStatus.PRESENT);
       testRecords.put("2022-03-02", AttendanceStatus.TARDY);
       testRecords.put("2022-03-04", AttendanceStatus.PRESENT);
       testRecords.put("2022-03-03", AttendanceStatus.ABSENT);
       double[] result = manager.calculateAttendance(testRecords);
       assertEquals(70.0, result[0]);
       assertEquals(2, result[1]);
       assertEquals(1, result[2]);
   }
   @Test
   public void testGenerateClassReport() {
       String classReport = manager.generateClassReport();
       String expectedReport = "Class Attendance Report:\n" +
           "rachel: Attended 1/2 sessions (50.00% attendance rate), Tardy Count: 0.\n" +
           "yitiao: Attended 1/2 sessions (90.00% attendance rate), Tardy Count: 1.\n";
    
       assertEquals(expectedReport, classReport, "The class report should match the expected output.");
   }

}
