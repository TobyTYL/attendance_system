package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttendanceRecordExporterFactoryTest {
    @Test
    public void testNormal(){
        assertTrue(AttendanceRecordExporterFactory.createExporter("json") instanceof JsonAttendanceRecordExporter);
        assertTrue(AttendanceRecordExporterFactory.createExporter("xml") instanceof XmlAttendanceRecordExporter);
        assertTrue(AttendanceRecordExporterFactory.createExporter("csv") instanceof CsvAttendanceRecordExporter);

    }
    @Test 
    public void testIllegal(){
         assertThrows(IllegalArgumentException.class, () -> {
            AttendanceRecordExporterFactory.createExporter("txt");
        });
    }

}
