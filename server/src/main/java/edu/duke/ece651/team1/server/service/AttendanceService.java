package edu.duke.ece651.team1.server.service;
import edu.duke.ece651.team1.shared.*;
// import java.io.*;


import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

@Service
public class AttendanceService {
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;
    
    public void setAttendanceRecordsPath(String attendanceRecordsPath) {
        this.attendanceRecordsPath = attendanceRecordsPath;
    }
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

    /**
     *  find if the student is the record by searching by lagal name
     * @param record
     * @param studentName
     * @return
     */
    private Student findStudentByLegalName(AttendanceRecord record, String studentName) {
        for (Student student : record.getEntries().keySet()) {
            if (student.getLegalName().equals(studentName)) {
                return student;
            }
        }
        return null;
    }

    // public String getPath(){
    //     return attendanceRecordsPath;
    // }
    /**
     * get student record entry by searching the username and session date and student name
     * for later modify the entry (attendance status)
     * @param userName
     * @param sessionDate
     * @param studentName
     * @return
     * @throws IOException
     */
    public String getStudentRecordEntry(String userName, String sessionDate, String studentName) throws IOException {
        //String filePath = attendanceRecordsPath + userName + "/" + "Attendance-" + sessionDate + ".json";
        String filePath = "src/data/attendanceRecord/" + userName + "/" + "Attendance-" + sessionDate + ".json";
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        AttendanceRecord record = serializer.deserialize(jsonContent);
    
        Student foundStudent = findStudentByLegalName(record, studentName);
        if (foundStudent != null) {
            AttendanceStatus status = record.getEntries().get(foundStudent);
            return "Student Name: " + studentName + ", Attendance Status: " + status;
        }
    
        return "No attendance record found for student: " + studentName;
    }
//string attendanceEntry {legal name:yitiao, Atttendance Status: Present}
    public String modifyStudentEntry(String userName, String sessionDate, String attendanceEntryJson) {
        try {
            // Assuming a method to get the file path for a specific record
            JSONObject json = new JSONObject(attendanceEntryJson);
            String studentName = json.getString("Legal Name");
            String statusString = json.getString("Attendance Status");
            AttendanceStatus newStatus = AttendanceStatus.valueOf(statusString.toUpperCase());

            String filePath = "src/data/attendanceRecord/" + userName + "/Attendance-" + sessionDate + ".json";//find file path
            JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            AttendanceRecord record = serializer.deserialize(jsonContent);
    
            // Find and update the student's status
            Student foundStudent = findStudentByLegalName(record, studentName);
            if (foundStudent != null) {
                record.updateStudentStatus(foundStudent, newStatus);
                
                // Serialize the updated record and save it
                String updatedJsonContent = serializer.serialize(record);
                Files.write(Paths.get(filePath), updatedJsonContent.getBytes());
                
                return "Successfully updated attendance status for " + studentName;
            } else {
                return "Student not found in the attendance record for " + sessionDate;
            }
    
        } catch (IOException e) {
            // Handle file reading/writing errors
            return "Failed to modify attendance record: " + e.getMessage();
        }catch (JSONException e) {
            return "Invalid JSON format for attendance entry: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Invalid attendance status provided.";
        }
    }
    
}


