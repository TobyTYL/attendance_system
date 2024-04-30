package edu.duke.ece651.team1.client.model;

public class CourseDetail {
    int id;
    int sectionId;
    String courseName;
    public CourseDetail(int id, int sectionId, String courseName) {
        this.id = id;
        this.sectionId = sectionId;
        this.courseName = courseName;
    }
    public int getId() {
        return id;
    }
    public int getSectionId() {
        return sectionId;
    }
    public String getCourseName() {
        return courseName;
    }
    
}
