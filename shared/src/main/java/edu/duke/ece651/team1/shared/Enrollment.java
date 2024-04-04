package edu.duke.ece651.team1.shared;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int sectionId;

    public Enrollment(int studentId, int sectionId) {
        this.studentId = studentId;
        this.sectionId = sectionId;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
