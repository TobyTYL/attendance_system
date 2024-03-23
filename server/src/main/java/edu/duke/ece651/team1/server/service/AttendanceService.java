package edu.duke.ece651.team1.server.service;
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
    
    public void saveAttendanceRecord(String record,String userName) throws IOException{
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        AttendanceRecord attendanceRecord =serializer.deserialize(record);
        String fileName = "Attendance-"+attendanceRecord.getSessionDate();
        AttendanceRecordExporter exporter = AttendanceRecordExporterFactory.createExporter("json");
        String filePath = attendanceRecordsPath+userName+"/";
        exporter.exportToFile(attendanceRecord, fileName,filePath);
    }

    public List<String> getRecordDates(String userName) throws IOException {
        String path = attendanceRecordsPath+userName+"/";
        try(Stream<Path>  stream = Files.list(Paths.get(path))){
            int attendance_length = "attendance".length();
            int date_length = "YYYY-MM-DD".length();
            int start = attendance_length+1;
            int end = start+date_length;
            List<String> dates = stream.map(Path::getFileName).map(Path::toString).map(filename->filename.substring(start,end)).collect(Collectors.toList()); 
            return dates;
        }catch(NoSuchFileException e ){
            return Collections.emptyList();
        }
    }

    public String getRecord(String userName, String sessionDate) throws IOException{
        String filePath = attendanceRecordsPath+userName+"/"+"Attendance-"+sessionDate+".json";
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
