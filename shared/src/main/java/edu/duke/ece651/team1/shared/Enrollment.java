package edu.duke.ece651.team1.shared;

public class Enrollment {
    private Integer enrollmentId;
    private int studentId;
    private int sectionId;

    public Enrollment(Integer enrollmentId, int studentId, int sectionId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }


    public Enrollment(int studentId, int sectionId) {
        // this.studentId = studentId;
        // this.sectionId = sectionId;
        this(null, studentId, sectionId);
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
