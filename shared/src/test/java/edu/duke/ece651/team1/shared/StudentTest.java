package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    public void testEqual(){
        Student s1 = new Student("ABC","ABC","ABC@email.com");
        Student s2 = new Student("ABC","ABC","ABC@email.com");
        Student s3 = new Student("ABD","ABC","ABC@email.com");
        Student s4 = new Student("ABC","ABCD","ABC@email.com");
        Student s5 = new Student("ABC","ABC","ABCD@email.com");
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);
        assertNotEquals(s1, s5);


    }
}
