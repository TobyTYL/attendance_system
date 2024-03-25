package edu.duke.ece651.team1.server.service;
import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
import edu.duke.ece651.team1.shared.*;
// import java.io.*;


import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

@Service
public class AttendanceService {
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;
    @Autowired
    private InMemoryAttendanceRepository inMemoryAttendanceRepository;
    
    public void saveAttendanceRecord(String record,String userName) throws IOException{
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        AttendanceRecord attendanceRecord =serializer.deserialize(record);
        inMemoryAttendanceRepository.saveAttendanceRecord(attendanceRecord, userName);
    }

   

    public List<String> getRecordDates(String userName) throws IOException {
        return inMemoryAttendanceRepository.getRecordDates(userName);
    }

    public String getRecord(String userName, String sessionDate) throws IOException{
        return inMemoryAttendanceRepository.getRecord(userName, sessionDate);
    }
}
