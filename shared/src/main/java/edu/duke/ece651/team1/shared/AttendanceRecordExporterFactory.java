package edu.duke.ece651.team1.shared;

import java.util.HashMap;
import java.util.Map;
/**
 * A factory for creating AttendanceRecordExporter instances based on the specified format.
 * Supports JSON, XML, and CSV formats.
 */
public class AttendanceRecordExporterFactory {
    private static final Map<String, AttendanceRecordExporter> exporters = new HashMap<>();

    static {
    
        exporters.put("json", new JsonAttendanceRecordExporter());
        exporters.put("xml", new XmlAttendanceRecordExporter());
        exporters.put("csv", new CsvAttendanceRecordExporter());
    }
    /**
     * Creates and returns an AttendanceRecordExporter for the specified format.
     * @param format The format of the exporter (json, xml, csv).
     * @return An instance of AttendanceRecordExporter for the specified format.
     * @throws IllegalArgumentException if an unsupported format is requested.
     */
    public static AttendanceRecordExporter createExporter(String format) {
        AttendanceRecordExporter exporter = exporters.get(format);
        if (exporter == null) {
            throw new IllegalArgumentException("Unsupported export format: " + format);
        }
        return exporter;
    }
}
