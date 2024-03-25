package edu.duke.ece651.team1.server.repository;
import edu.duke.ece651.team1.shared.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
@Repository
public class InMemoryRosterRepository {
    public Set<Student> getRoster(String userName) {
        Set<Student> students = new HashSet<>();
        Student huidan = new Student("yitiao","huidan_tan18@163.com");
        Student zhecheng = new Student("zhecheng","huidan_tan18@163.com");
        students.add(huidan);
        students.add(zhecheng);
        return students;
    }
}
