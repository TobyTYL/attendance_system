package edu.duke.ece651.team1.shared;

import java.io.IOException;

public interface AttendanceRecordExporter {
    void exportToFile(AttendanceRecord record, String filename,String filePath) throws IOException ;
}
