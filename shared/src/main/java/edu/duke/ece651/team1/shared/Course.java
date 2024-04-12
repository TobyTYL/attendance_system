package edu.duke.ece651.team1.shared;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a course with a unique identifier and a name.
 * Additional properties and behavior can be added as needed.
 */
public class Course {
    private int id;
    private String name;
  //private Professor headInstructor;
  //private Set<User> coInstructors = new LinkedHashSet<>();

     // Constructor to set the course ID
    public Course(int id) {
        this.id = id;
    }
    // Constructor to set the course name
    public Course(String name){
        this.name = name;
    }
    // Constructor to set the course ID and name
    public Course(int id, String name){
        this.id = id;
        this.name = name;
    }
    // Constructor to set the course ID and name
    public int getID() {
        return id;
    }
    // Constructor to set the course ID and name
    public String getName() {
        return name;
    }
    // Constructor to set the course ID and name
    public void setName(String s) {
        this.name = s;
    }
    // Constructor to set the course ID and name
    public void setID(int id){
        this.id = id;
    }
  //ignore the user part for attendance
  /**
    public Professor getHeadInstructor() {
        return headInstructor;
    }
    public void setHeadInstructor(Professor p) {
        headInstructor = p;
    }

    public void addCoInstructor(User u) {
        coInstructors.add(u);
    }

    public Iterable<User> getCoInsturctors() {
        return coInstructors;
    }
  */

}
