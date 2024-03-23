package edu.duke.ece651.team1.shared;

import java.util.HashMap;
import java.util.Map;

public class AttendanceRecordExporterFactory {
    private static final Map<String, AttendanceRecordExporter> exporters = new HashMap<>();

    static {
    
        exporters.put("json", new JsonAttendanceRecordExporter());
        exporters.put("xml", new XmlAttendanceRecordExporter());
        exporters.put("csv", new CsvAttendanceRecordExporter());
    }

    public static AttendanceRecordExporter createExporter(String format) {
        AttendanceRecordExporter exporter = exporters.get(format);
        if (exporter == null) {
            throw new IllegalArgumentException("Unsupported export format: " + format);
        }
        return exporter;
    }
}
