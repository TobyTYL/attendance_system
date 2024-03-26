package edu.duke.ece651.team1.server.repository;

import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import com.google.gson.Gson;

@Repository
public class InMemoryRosterRepository {
    @Value("${studentInfo.path}")
    private String studentInfoPath;

    public List<Student> getStudents(String username) throws IOException {
        List<Student> students = new ArrayList<>();
        String filePath = studentInfoPath + "/" + username + "/roster.json";
        File file = new File(filePath);
        if (file.exists()) {
            students.addAll(readStudentsFromFile(file));
        }
        return students;
    }

    private  List<Student> readStudentsFromFile(File file) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            Gson gson = new Gson();
            Type studentListType = new TypeToken<List<Student>>() {
            }.getType();
            students.addAll(gson.fromJson(jsonContent.toString(), studentListType));
        }
        return students;
    }

    public void saveStudents(List<Student> students, String username) throws IOException {
        String filePath = studentInfoPath + "/" + username + "/roster.json";
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); 
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new Gson();
            gson.toJson(students, writer);
        }
    }

}
