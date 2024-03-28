package edu.duke.ece651.team1.server.repository;

// import java.io.BufferedWriter;
// import java.io.FileWriter;
// import java.io.IOException;
import java.nio.file.Files;
// import java.nio.file.NoSuchFileException;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceRecordExporter;
import edu.duke.ece651.team1.shared.AttendanceRecordExporterFactory;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
/*
 * This class represents a repository for storing and retrieving attendance records.
 */
@Repository
public class InMemoryAttendanceRepository {
    // Path to the directory where attendance records are stored
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;
    // Encryptor used to encrypt and decrypt attendance record content
    @Autowired
    private StringEncryptor encryptor;
    /*
     * Saves the given attendance record for the specified user
     * @param attendanceRecord The attendance record to be saved
     * @param userName The username of the user
     * @throws IOException If an I/O error occurs
     */
    public void saveAttendanceRecord(AttendanceRecord attendanceRecord, String userName) throws IOException {
        // Constructing the filename based on session date
        String fileName = "Attendance-" + attendanceRecord.getSessionDate();
        // AttendanceRecordExporter exporter = AttendanceRecordExporterFactory.createExporter("json");
        // Constructing the file path
        String filePath = attendanceRecordsPath + userName + "/"+fileName+".json";
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String content = serializer.serialize(attendanceRecord);
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        // Creating parent directories if they do not exist
        if (parentDir != null && !parentDir.exists()) {
            // If parent directory not exist, make one
            parentDir.mkdirs();
        }
        // Write back encrpt info
        // Serializing and encrypting the attendance record content, then writing it to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(encryptor.encrypt(content));
        }
    }
    /*
     * Retrieves the list of session dates for which attendance records exist for the specified user.
     * @param userName The username of the user.
     * @return A list of session dates.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> getRecordDates(String userName) throws IOException {
        // Constructing the directory path
        String path = attendanceRecordsPath + userName + "/";
        // Using Files.list() to list all files in the directory, then extracting session dates from filenames
        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            int attendance_length = "attendance".length();
            int date_length = "YYYY-MM-DD".length();
            int start = attendance_length + 1;
            int end = start + date_length;
            List<String> dates = stream.map(Path::getFileName).map(Path::toString)
                    .map(filename -> filename.substring(start, end)).sorted().collect(Collectors.toList());
            return dates;
        } catch (NoSuchFileException e) {
            return Collections.emptyList();
        }
    }
    /*
     * Retrieves the attendance record for the specified user and session date.
     * @param userName The username of the user.
     * @param sessionDate The session date.
     * @return The attendance record.
     * @throws IOException If an I/O error occurs.
     */
    public AttendanceRecord getRecord(String userName, String sessionDate) throws IOException {
        String filePath = attendanceRecordsPath + userName + "/" + "Attendance-" + sessionDate + ".json";
        String recordString = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        return serializer.deserialize(encryptor.decrypt(recordString));
    }
    /*
     * Retrieves all attendance records for the specified user.
     * @param userName The username of the user.
     * @return A list of attendance records.
     * @throws IOException If an I/O error occurs.
     */
    public List<AttendanceRecord> getRecords(String userName) throws IOException {
        // Constructing the directory path
        String path = attendanceRecordsPath + userName + "/";
        // Using Files.list() to list all files in the directory, then reading, decrypting, and deserializing each record
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
                    .map(content -> serializer.deserialize(encryptor.decrypt(content)))
                    .collect(Collectors.toList());
            return records;
        }catch(NoSuchFileException e){
            return Collections.emptyList();
        }
    }
}
