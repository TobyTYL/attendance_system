 package edu.duke.ece651.team1.server.repository;

 import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
 import edu.duke.ece651.team1.shared.Student;
 import com.google.gson.Gson;
 import com.google.gson.reflect.TypeToken;

 import org.jasypt.encryption.StringEncryptor;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;

 import java.io.*;
 import java.lang.reflect.Type;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 import org.springframework.stereotype.Repository;
 import com.google.gson.Gson;
 /*
  * This class represents a repository for managing student roster information.
  */
 @Repository
 public class InMemoryRosterRepository {
     @Value("${studentInfo.path}")
     private String studentInfoPath;
     @Autowired
     private StringEncryptor encryptor;

     /*
      * Retrieves the list of students for the specified username.
      * @param username The username of the user.
      * @return A list of Student objects.
      * @throws IOException If an I/O error occurs.
      * @throws FileNotFoundException If the roster file does not exist.
      */
     public List<Student> getStudents(String username) throws IOException {
         List<Student> students = new ArrayList<>();
         // Path to the directory where student roster files are stored
         String filePath = studentInfoPath + "/" + username + "/roster.json";
         File file = new File(filePath);
         if (file.exists()) {
             students.addAll(readStudentsFromFile(file));
         }else{
             throw new FileNotFoundException("no roster file in record");
         }
         return students;
     }
     /*
      * Reads and decrypts student roster data from the specified file.
      * @param file The File object representing the roster file.
      * @return A list of Student objects.
      * @throws IOException If an I/O error occurs.
      */
     public  List<Student> readStudentsFromFile(File file) throws IOException {
         List<Student> students = new ArrayList<>();
         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             StringBuilder encryptedJsonContent = new StringBuilder();
             String line;
             while ((line = reader.readLine()) != null) {
                 encryptedJsonContent.append(line);
             }
             Gson gson = new Gson();
             Type studentListType = new TypeToken<List<Student>>() {
             }.getType();
             String decryptedJson = encryptor.decrypt(encryptedJsonContent.toString());
             students.addAll(gson.fromJson(decryptedJson, studentListType));
         }
         return students;
     }
     /*
      * Saves the list of students for the specified username.
      * @param students The list of Student objects to be saved.
      * @param username The username of the user.
      * @throws IOException If an I/O error occurs.
      */
     public void saveStudents(List<Student> students, String username) throws IOException {
         String filePath = studentInfoPath + "/" + username + "/roster.json";
         File file = new File(filePath);
         File parentDir = file.getParentFile();
         if (parentDir != null && !parentDir.exists()) {
             parentDir.mkdirs();
         }
         try (FileWriter writer = new FileWriter(filePath)) {
             Gson gson = new Gson();
             String json = gson.toJson(students);
             String encryptedJson = encryptor.encrypt(json); // 加密数据
             writer.write(encryptedJson);
         }
     }

 }
