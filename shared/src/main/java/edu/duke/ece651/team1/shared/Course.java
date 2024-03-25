package edu.duke.ece651.team1.shared;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashSet;
import java.util.Set;


public class Course {
    private final long id;
    private String name;
  //private Professor headInstructor;
  //private Set<User> coInstructors = new LinkedHashSet<>();

    public Course(long id) {
        this.id = id;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
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
