package edu.duke.ece651.team1.server.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceRecordExporter;
import edu.duke.ece651.team1.shared.AttendanceRecordExporterFactory;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
@Repository
public class InMemoryAttendanceRepository {
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;

    public void saveAttendanceRecord(AttendanceRecord attendanceRecord, String userName) throws IOException {
        String fileName = "Attendance-" + attendanceRecord.getSessionDate();
        AttendanceRecordExporter exporter = AttendanceRecordExporterFactory.createExporter("json");
        String filePath = attendanceRecordsPath + userName + "/";
        exporter.exportToFile(attendanceRecord, fileName, filePath);
    }

    public List<String> getRecordDates(String userName) throws IOException {
        String path = attendanceRecordsPath + userName + "/";
        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            int attendance_length = "attendance".length();
            int date_length = "YYYY-MM-DD".length();
            int start = attendance_length + 1;
            int end = start + date_length;
            List<String> dates = stream.map(Path::getFileName).map(Path::toString)
                    .map(filename -> filename.substring(start, end)).collect(Collectors.toList());
            return dates;
        } catch (NoSuchFileException e) {
            return Collections.emptyList();
        }
    }

    public String getRecord(String userName, String sessionDate) throws IOException {
        String filePath = attendanceRecordsPath + userName + "/" + "Attendance-" + sessionDate + ".json";
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public List<AttendanceRecord> getRecords(String userName) throws IOException {
        String path = attendanceRecordsPath + userName + "/";
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            List<AttendanceRecord> records = stream
                    .map(file -> {
                        try {
                            return new String(Files.readAllBytes(file));

                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(content -> content != null)
                    .map(content -> serializer.deserialize(content))
                    .collect(Collectors.toList());
            return records;
        }catch(NoSuchFileException e){
            return Collections.emptyList();
        }
    }
   
    
  
}
