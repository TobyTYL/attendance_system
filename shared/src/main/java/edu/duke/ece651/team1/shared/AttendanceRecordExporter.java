package edu.duke.ece651.team1.shared;

import java.io.IOException;
/**
 * Defines a contract for exporting attendance records to a file.
 * Implementations are expected to handle the export process based on different file formats.
 */
public interface AttendanceRecordExporter {
    /**
     * Exports an AttendanceRecord to a specified file and path.
     * @param record The attendance record to export.
     * @param filename The name of the file to create.
     * @param filePath The path where the file will be saved.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    void exportToFile(AttendanceRecord record, String filename,String filePath) throws IOException ;
}
