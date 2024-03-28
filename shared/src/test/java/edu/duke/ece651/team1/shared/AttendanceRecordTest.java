package edu.duke.ece651.team1.shared;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.time.LocalDate;
public class AttendanceRecordTest {
    AttendanceRecord record = new AttendanceRecord( LocalDate.now());
    Student huidan = new Student("huidan");
        Student zhecheng = new Student("zhecheng");
    @Test
    public void testInitializeAttendanceEntry(){
        record.initializeAttendanceEntry(zhecheng);
        record.initializeAttendanceEntry(huidan);
        assertEquals(record.getAttendanceEntry(zhecheng), AttendanceStatus.ABSENT);
        assertEquals(record.getAttendanceEntry(huidan), AttendanceStatus.ABSENT);
    }
    // public static<T> void checkMarkPresent(Iterable<T> it, T... expected) {
       
    //     Set<T> expectedSet = new HashSet<>();
    //     for(T l: expected){
    //         expectedSet.add(l);
    //     }
    //     for(T l: it){
    //         assert(expectedSet.contains(l));
    //         expectedSet.remove(l);
    //     }
    //     assertTrue(expectedSet.isEmpty());
    // }
    @Test
    public void testMarkPresent(){
        record.initializeAttendanceEntry(huidan);
        record.markPresent(huidan);
        assertEquals(record.getAttendanceEntry(huidan), AttendanceStatus.PRESENT);
    }
    @Test
    public void testMarkTardy(){
        record.initializeAttendanceEntry(zhecheng);
        record.markTardy(zhecheng);
        assertEquals(record.getAttendanceEntry(zhecheng), AttendanceStatus.TARDY);
    }
    @Test
    public void testNotinRecord(){
        assertThrows(IllegalArgumentException.class, ()->record.markPresent(huidan));
        assertThrows(IllegalArgumentException.class, ()->record.markTardy(zhecheng));

    }
    // @Test void testDisplayAttendace(){
    //     Set<Student> roaster = new HashSet<>(Set.of(huidan,zhecheng));
    //     record.initializeFromRoaster(roaster);
    //     huidan.updateDisplayName("Rachel");
    //     record.markPresent(huidan);
    //     String expected = "Attendance Record for "+LocalDate.now()+"\n"+
    //                     "----------------------------\n"+
    //                     "Rachel: Present\n"+
    //                     "zhecheng: Absent\n"+
    //                     "----------------------------";
    //     assertEquals(expected, record.displayAttendance());
        
    // }
    @Test
    public void testGetEntries() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student1 = new Student("123", "John Doe");
        Student student2 = new Student("456", "Jane Doe");
        
        record.initializeAttendanceEntry(student1);
        record.markPresent(student1);
        record.initializeAttendanceEntry(student2);
        record.markAbsent(student2);
        
        Map<Student, AttendanceStatus> entries = record.getEntries();
        
        // Verify that the entries map has the correct statuses
        assertEquals(AttendanceStatus.PRESENT, entries.get(student1));
        assertEquals(AttendanceStatus.ABSENT, entries.get(student2));
        
        // Verify that the map is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> entries.put(new Student("789", "New Student"), AttendanceStatus.TARDY));
        
        // Verify the map size to ensure no unintended entries are present
        assertEquals(2, entries.size());
    }
}
