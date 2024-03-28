package edu.duke.ece651.team1.server.repository;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Collectors; // For collecting the stream results


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.team1.shared.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class InMemoryAttendanceRepositoryTest {

    @Mock
    private StringEncryptor encryptor;
    private InMemoryAttendanceRepository repository;
    @Mock
    private JsonAttendanceSerializer serializer;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new InMemoryAttendanceRepository();
        ReflectionTestUtils.setField(repository, "attendanceRecordsPath", tempDir.toString()+"/");
        ReflectionTestUtils.setField(repository, "encryptor", encryptor);
    }

    @Test
    void saveAttendanceRecord_Success() throws IOException {
       
        AttendanceRecord record = new AttendanceRecord(LocalDate.parse("2024-03-27"));
        record.initializeAttendanceEntry(new Student("huidan"));
        String dummyContent = "{\"sessionDate\":\"2024-03-27\"}";
        String encryptedContent = "encryptedDummyContent";
        when(encryptor.encrypt(anyString())).thenReturn(encryptedContent);
        repository.saveAttendanceRecord(record, "user1");
        Path filePath = tempDir.resolve("user1/Attendance-2024-03-27.json");
        assertTrue(Files.exists(filePath));
        // String content = Files.readString(filePath);
        // assertEquals(encryptedContent, content);
    }
    @Test
void listTempDirContents() throws IOException {
    // Assuming tempDir has been initialized by JUnit with @TempDir
    System.out.println("Listing contents of tempDir: " + tempDir);

    try (Stream<Path> stream = Files.list(tempDir)) {
        List<Path> filesList = stream.collect(Collectors.toList());
        if (filesList.isEmpty()) {
            System.out.println("The tempDir is empty.");
        
        } else {
            assertEquals("stream", filesList);
            filesList.forEach(path -> System.out.println(path.getFileName()));
        }
    } catch (IOException e) {
        e.printStackTrace();
        fail("An IOException was thrown while trying to list the contents of the tempDir.");
    }
}
    @Test
    void getRecordDates_Success() throws IOException {
       
        String userName = "user1";
        String sessionDate = "2024-03-27";
        Path userDir = Files.createDirectories(tempDir.resolve(userName));
        Files.createFile(userDir.resolve("Attendance-" + sessionDate + ".json"));

        // When
        List<String> dates = repository.getRecordDates(userName);

        // Then
        assertFalse(dates.isEmpty());
        assertTrue(dates.contains(sessionDate));
    }

    @Test
    void getRecord_Success() throws IOException {
      
        String userName = "user1";
        String sessionDate = "2024-03-21";
        String dummyContent = "{\n" +
        "    \"sessionDate\": \"2024-03-21\",\n" +
        "    \"Entries\": {\n" +
        "        \"yitiao\": {\n" +
        "            \"Display Name\": \"yitiao\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Absent\"\n" +
        "        },\n" +
        "        \"zhecheng\": {\n" +
        "            \"Display Name\": \"zhecheng\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Present\"\n" +
        "        }\n" +
        "    }\n" +
        "}";
        String encryptedContent = "encryptedDummyContent";
        when(encryptor.decrypt(anyString())).thenReturn(dummyContent);

        Path filePath = tempDir.resolve(userName + "/Attendance-" + sessionDate + ".json");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, encryptedContent);

        // When
        AttendanceRecord record = repository.getRecord(userName, sessionDate);

        // Then
        assertNotNull(record);
        assertEquals(sessionDate, record.getSessionDate().toString());
    }

    @Test
    void getRecords_Success() throws IOException {
        
        String userName = "user1";
        String dummyContent1 = "{\n" +
        "    \"sessionDate\": \"2024-03-27\",\n" +
        "    \"Entries\": {\n" +
        "        \"yitiao\": {\n" +
        "            \"Display Name\": \"yitiao\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Absent\"\n" +
        "        },\n" +
        "        \"zhecheng\": {\n" +
        "            \"Display Name\": \"zhecheng\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Present\"\n" +
        "        }\n" +
        "    }\n" +
        "}";
        String dummyContent2 =  "{\n" +
        "    \"sessionDate\": \"2024-03-28\",\n" +
        "    \"Entries\": {\n" +
        "        \"yitiao\": {\n" +
        "            \"Display Name\": \"yitiao\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Absent\"\n" +
        "        },\n" +
        "        \"zhecheng\": {\n" +
        "            \"Display Name\": \"zhecheng\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Present\"\n" +
        "        }\n" +
        "    }\n" +
        "}";
        String encryptedContent1 = "encryptedDummyContent1";
        String encryptedContent2 = "encryptedDummyContent2";
        when(encryptor.decrypt(anyString())).thenAnswer(invocation -> {
            String arg = invocation.getArgument(0);
            return arg.equals(encryptedContent1) ? dummyContent1 : dummyContent2;
        });

        Path userDir = Files.createDirectories(tempDir.resolve(userName));
        Files.writeString(userDir.resolve("Attendance-2024-03-27.json"), encryptedContent1);
        Files.writeString(userDir.resolve("Attendance-2024-03-28.json"), encryptedContent2);

        // When
        List<AttendanceRecord> records = repository.getRecords(userName);

        // Then
        assertEquals(2, records.size());
        assertTrue(records.stream().anyMatch(r -> r.getSessionDate().toString().equals("2024-03-27")));
        assertTrue(records.stream().anyMatch(r -> r.getSessionDate().toString().equals("2024-03-28")));
    }
}

