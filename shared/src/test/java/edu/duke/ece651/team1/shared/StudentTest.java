package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
public class StudentTest {
  @Test
  public void test_Student_Constructor() {
    Student student = new Student("Jimmy", "Jim", "jimmy@duke.edu");
    Student student2 = new Student("Jimmy", "jimmy@duke.edu");
    Student studnet3 = new Student();
    assertEquals("Jimmy", student.getLegalName());
    assertEquals("Jim", student.getDisPlayName());
    assertEquals("jimmy@duke.edu", student.getEmail());
    student2.setStudentEmail("new email");
    assertEquals("new email", student2.getEmail());
    student2.setUserId(1);
    assertEquals(1, student2.getUserId());
  }

  @Test
  public void test_student_Constuctor_Uid(){
    Student student = new Student(1,"Jimmy", "Jim", "jimmy@duke.edu");
    student.setStudentId(2);
    assertEquals(1, student.getUserId());
    assertEquals(2, student.getStudentId());
  }
  
  @Test
    public void testEqual(){
        Student s1 = new Student("ABC","ABC","ABC@email.com");
        Student s2 = new Student("ABC","ABC","ABC@email.com");
        Student s3 = new Student("ABD","ABC","ABC@email.com");
        Student s4 = new Student("ABC","ABCD","ABC@email.com");
        Student s5 = new Student("ABC","ABC","ABCD@email.com");
        s5.updateDisplayName("good");
        assertEquals("good", s5.getDisPlayName());
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);
        assertNotEquals(s1, s5);
        Object obj = new Object();
        assertNotEquals(s1, obj);//type checking

    }
}

