package edu.duke.ece651.team1.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import com.google.gson.*;

/**
 * Provides functionality to serialize AttendanceRecord objects into JSON format using Gson.
 * This class also supports deserializing JSON strings back into AttendanceRecord objects.
 */
//to-do edit
public class JsonAttendanceSerializer {
    // private final Gson gson;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // public void exportToFile(AttendanceRecord record, String filename,String filePath) throws IOException {
    //     // TODO Auto-generated method stub
    //     String content = serialize(record);
    //     filePath = filePath+filename+".json";
    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
    //         writer.write(content);
    //     }
        
    // }

    /**
     * Serializes the given AttendanceRecord object into a JSON string.
     * Includes details such as session date and attendance entries for each student.
     * @param record The AttendanceRecord to serialize.
     * @return A JSON string representing the attendance record.
     */
    public String serialize(AttendanceRecord record) {
        // TODO Auto-generated method stub
        JsonObject jsonObject = new JsonObject();
        if(record.getRecordId()!=null){
            jsonObject.addProperty("Record Id", record.getRecordId());
        }
        jsonObject.addProperty("sessionDate", record.getSessionDate().format(formatter));
        JsonObject entriesJson = new JsonObject();
        for(Map.Entry<Student,AttendanceStatus> entry:record.getSortedEntries()){
            JsonObject entryJason = new JsonObject();     
            entryJason.addProperty("student Id", entry.getKey().getStudentId());
            entryJason.addProperty("Display Name", entry.getKey().getDisPlayName());
            entryJason.addProperty("Email", entry.getKey().getEmail());
            entryJason.addProperty("Attendance status", entry.getValue().getStatus());
            entriesJson.add(entry.getKey().getLegalName(), entryJason);
        }
        jsonObject.add("Entries", entriesJson);
        Gson gson = new Gson();
        String jsonString = gson.toJson(jsonObject);
        return jsonString;

       
    }

   /**
     * Deserializes a JSON string into an AttendanceRecord object.
     * Assumes the JSON format matches that produced by the serialize method.
     * @param record A JSON string representing an attendance record.
     * @return The deserialized AttendanceRecord object.
     */
    public AttendanceRecord deserialize(String record) {
        // TODO Auto-generated method stub
        JsonObject jsonObject = JsonParser.parseString(record).getAsJsonObject();
        LocalDate sessionDate = LocalDate.parse(jsonObject.get("sessionDate").getAsString(), formatter);
        AttendanceRecord attendanceRecord = new AttendanceRecord(sessionDate);
        if (jsonObject.has("Record Id") ) {
            int recordId = jsonObject.get("Record Id").getAsInt();
            attendanceRecord.setRecordId(recordId);
        }
        JsonObject entries = jsonObject.getAsJsonObject("Entries");
        for (Map.Entry<String, JsonElement> entry : entries.entrySet()) {
            JsonObject entryValue = entry.getValue().getAsJsonObject();
            String displayName = entryValue.get("Display Name").getAsString();
            String email = entryValue.get("Email").getAsString();
            String legalName =  entry.getKey();
            int studentId = entryValue.get("student Id").getAsInt();
            Student student = new Student( studentId,legalName,displayName , email,null);
            String status = entryValue.get("Attendance status").getAsString();
            AttendanceStatus attendanceStatus = AttendanceStatus.fromString(status);
            attendanceRecord.initializeAttendanceEntry(student);
            attendanceRecord.updateStudentStatus(student, attendanceStatus);
        }
        return attendanceRecord;
    }
    
}
