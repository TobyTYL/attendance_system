package edu.duke.ece651.team1.shared;

import java.io.*;

public class JsonAttendanceRecordExporter implements AttendanceRecordExporter {
    /**
     * Implements the AttendanceRecordExporter interface to provide JSON exporting functionality.
     * This class serializes AttendanceRecord objects into JSON format and saves them to a specified file.
     */
    @Override
    public void exportToFile(AttendanceRecord record, String filename, String filePath) throws IOException {
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String content = serializer.serialize(record);
        filePath = filePath + filename + ".json";
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); //if parent directory not exist, make one
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

}
