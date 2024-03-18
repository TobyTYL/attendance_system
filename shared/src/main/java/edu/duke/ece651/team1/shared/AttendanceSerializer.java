package edu.duke.ece651.team1.shared;

import java.io.IOException;

public interface AttendanceSerializer {
    String serialize(AttendanceRecord record);
    void exportToFile(AttendanceRecord record, String filename, String filePath) throws IOException;
    AttendanceRecord deserialize(String record);
}
