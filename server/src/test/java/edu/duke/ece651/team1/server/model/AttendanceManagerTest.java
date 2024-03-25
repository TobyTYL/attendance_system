package edu.duke.ece651.team1.server.model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
import edu.duke.ece651.team1.server.repository.InMemoryRosterRepository;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.Student;

import java.io.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
@SpringBootTest

public class AttendanceManagerTest {

    // @Autowired
    @Autowired
    InMemoryAttendanceRepository inMemoryAttendanceRepository;
    @Autowired
    InMemoryRosterRepository inMemoryRosterRepository;
    

    @Test
    public void testGenerateReport() throws IOException {
        String userName = "duke";
        AttendanceManager manager= new AttendanceManager(userName, inMemoryRosterRepository.getRoster(userName), inMemoryAttendanceRepository.getRecords(userName));
        Student yitiao = new Student("yitiao","huidan_tan18@163.com");
        String expected_yitiao = "Attendance Report for yitiao:\n"+
                            "2024-03-18: Absent\n"+
                            "2024-03-21: Absent\n"+
                            "2024-03-24: Present\n"+
                            "Total Attendance: 1/3 (33.33% attendance rate)";
        String expected_zhecheng = "Attendance Report for zhecheng:\n"+
        "2024-03-18: Absent\n"+
        "2024-03-21: Present\n"+
        "2024-03-24: Present\n"+
        "Total Attendance: 2/3 (66.67% attendance rate)";
        Student zhecheng = new Student("zhecheng","huidan_tan18@163.com");
        assertEquals(expected_yitiao, manager.generateReport(yitiao));
        assertEquals(expected_zhecheng, manager.generateReport(zhecheng));
     
        
    }
}
