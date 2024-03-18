package edu.duke.ece651.team1.shared;

public class AttendanceSerializerFactory {
    public static AttendanceSerializer createSerializer(String format) {
        switch (format.toLowerCase()) {
            // case "csv":
            //     return new CsvAttendanceSerializer();
            case "json":
                return new JsonAttendanceSerializer();
            // case "txt":
            //     return new TxtAttendanceSerializer();
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }
}
