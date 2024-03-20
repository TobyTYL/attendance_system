package edu.duke.ece651.team1.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import com.google.gson.*;


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

    
    public String serialize(AttendanceRecord record) {
        // TODO Auto-generated method stub
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionDate", record.getSessionDate().format(formatter));
        JsonObject entriesJson = new JsonObject();
        for(Map.Entry<Student,AttendanceStatus> entry:record.getSortedEntries()){
            JsonObject entryJason = new JsonObject();
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

   
    public AttendanceRecord deserialize(String record) {
        // TODO Auto-generated method stub
        JsonObject jsonObject = JsonParser.parseString(record).getAsJsonObject();
        LocalDate sessionDate = LocalDate.parse(jsonObject.get("sessionDate").getAsString(), formatter);
        AttendanceRecord attendanceRecord = new AttendanceRecord(sessionDate);
        JsonObject entries = jsonObject.getAsJsonObject("Entries");
        for (Map.Entry<String, JsonElement> entry : entries.entrySet()) {
            //legal name
            String studentKey = entry.getKey();
            JsonObject entryValue = entry.getValue().getAsJsonObject();
            String displayName = entryValue.get("Display Name").getAsString();
            String email = entryValue.get("Email").getAsString();
            Student student = new Student(studentKey,displayName , email);
            String status = entryValue.get("Attendance status").getAsString();
            AttendanceStatus attendanceStatus = AttendanceStatus.fromString(status);
            attendanceRecord.initializeAttendanceEntry(student);
            attendanceRecord.updateStudentStatus(student, attendanceStatus);
        }
        return attendanceRecord;
    }
    
}
