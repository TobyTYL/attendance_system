package edu.duke.ece651.team1.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;

public class JsonAttendanceRecordExporter implements AttendanceRecordExporter {

    @Override
    public void exportToFile(AttendanceRecord record, String filename, String filePath) throws IOException {
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String content = serializer.serialize(record);
        filePath = filePath+filename+".json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
    
}
