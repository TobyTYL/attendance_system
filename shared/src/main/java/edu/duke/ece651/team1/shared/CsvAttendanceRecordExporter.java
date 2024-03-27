package edu.duke.ece651.team1.shared;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implements the AttendanceRecordExporter interface to export attendance records in CSV format.
 */
public class CsvAttendanceRecordExporter implements AttendanceRecordExporter {
    /**
     * Converts the given AttendanceRecord to a format suitable for CSV output.
     * @param record The attendance record to convert.
     * @return A list of string arrays, each representing a row in the CSV file.
     */
    public List<String[]> convertToCsvFormat(AttendanceRecord record) {
    List<String[]> data = new ArrayList<>();
    String[] date = new String[2];
    date[0] = "sessionDate";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    date[1] = record.getSessionDate().format(formatter);
    data.add(date);
    data.add(new String[]{"Legal Name", "display Name","Email", "Attendance Status"});
    for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
        Student student = entry.getKey();
        AttendanceStatus status = entry.getValue();
        data.add(new String[]{student.getLegalName(), student.getDisPlayName(), student.getEmail(),status.getStatus()});
    }
    return data;
}
     /**
     * Converts an array of strings to a single CSV-formatted string.
     * @param data The string array to convert.
     * @return A CSV-formatted string.
     */
    public String convertToCSV(String[] data) {
        return Stream.of(data)
         .collect(Collectors.joining(","));
    }
     /**
     * Exports the specified AttendanceRecord to a CSV file at the given path and filename.
     * @param record The attendance record to export.
     * @param filename The name of the file to create.
     * @param filePath The path where the file will be saved.
     * @throws IOException If an I/O error occurs during file creation or writing.
     */
    @Override
    public void exportToFile(AttendanceRecord record, String filename, String filePath) throws IOException {
        // TODO Auto-generated method stub
         File file = new File(filePath,filename+".csv");
         List<String[]> dataLines = convertToCsvFormat(record);
          try (PrintWriter pw = new PrintWriter(file)) {
        dataLines.stream()
          .map(this::convertToCSV)
          .forEach(pw::println);
    }

        
    }
    
}
