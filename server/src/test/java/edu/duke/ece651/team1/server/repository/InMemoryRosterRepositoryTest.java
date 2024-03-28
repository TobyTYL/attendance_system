package edu.duke.ece651.team1.server.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;

import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ContextConfiguration(classes = {InMemoryRosterRepository.class})
@ExtendWith(SpringExtension.class)
class InMemoryRosterRepositoryTest {
    @Autowired
    private InMemoryRosterRepository inMemoryRosterRepository;
   @Mock
    private StringEncryptor encryptor;
    @TempDir
    Path tempDir;


  @MockBean
  private StringEncryptor stringEncryptor;

  @Test
  void testGetStudents() throws IOException {
    assertThrows(FileNotFoundException.class, () -> inMemoryRosterRepository.getStudents("janedoe"));
  }

  @Test
  @Disabled("TODO: Complete this test")
  void testSaveStudents() throws IOException {
    inMemoryRosterRepository.saveStudents(new ArrayList<>(), "janedoe");
  }
  @Test
  void testReadStudentsFromFile() throws IOException {
    List<Student> expectedStudents = Arrays.asList(
            new Student("Alice", "alice@example.com"),
            new Student("Bob", "bob@example.com")
    );

    String jsonContent = new Gson().toJson(expectedStudents);
    File tempFile = File.createTempFile("temp", ".json");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
      writer.write(jsonContent);
    }
    when(stringEncryptor.decrypt(jsonContent)).thenReturn(jsonContent);
    List<Student> actualStudents = inMemoryRosterRepository.readStudentsFromFile(tempFile);
    assertEquals(expectedStudents.size(), actualStudents.size());
    for (int i = 0; i < expectedStudents.size(); i++) {
      Student expectedStudent = expectedStudents.get(i);
      Student actualStudent = actualStudents.get(i);
      assertEquals(expectedStudent.getLegalName(), actualStudent.getLegalName());
      assertEquals(expectedStudent.getEmail(), actualStudent.getEmail());
    }
    tempFile.delete();
  }


void setUp(InMemoryRosterRepository repository) {
        MockitoAnnotations.openMocks(this);
        // Replace with actual constructor
        ReflectionTestUtils.setField(repository, "encryptor", encryptor);
        ReflectionTestUtils.setField(repository, "studentInfoPath", tempDir.toString()+"/");
    }

    @Test
    void saveStudents_createsEncryptedJsonFile() throws IOException {
       InMemoryRosterRepository repository = new InMemoryRosterRepository();
       setUp(repository);
        List<Student> students = Arrays.asList(
            new Student("Student1", "@email"),
            new Student("Student2", "@email")
        );
        String username = "testUser";
        String expectedJson = new Gson().toJson(students);
        String encryptedJson = "encryptedJson";
        when(encryptor.encrypt(anyString())).thenReturn(encryptedJson);
        repository.saveStudents(students, username);
        Path filePath = tempDir.resolve(username + "/roster.json");
        assertTrue(Files.exists(filePath), "The encrypted JSON file should be created.");
        String fileContent = Files.readString(filePath);
        assertEquals(encryptedJson, fileContent, "The file content should be encrypted.");
    }

}
