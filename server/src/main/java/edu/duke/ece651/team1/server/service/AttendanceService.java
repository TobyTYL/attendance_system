package edu.duke.ece651.team1.server.service;
import edu.duke.ece651.team1.shared.*;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class AttendanceService {
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;
    public void saveAttendanceRecord(String record) throws IOException{
        AttendanceSerializer serializer = AttendanceSerializerFactory.createSerializer("json");
        AttendanceRecord attendanceRecord =serializer.deserialize(record);
        String fileName = "Attendance-"+attendanceRecord.getSessionDate();
        serializer.exportToFile(attendanceRecord, fileName,attendanceRecordsPath);

    }
}
