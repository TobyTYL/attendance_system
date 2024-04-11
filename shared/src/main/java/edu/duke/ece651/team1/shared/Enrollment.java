package edu.duke.ece651.team1.shared;
/**
 * Represents an enrollment of a student in a section of a course.
 */
public class Enrollment {
    private Integer enrollmentId;
    private int studentId;
    private int sectionId;
    /**
     * Constructor to create an enrollment with the given parameters.
     * @param enrollmentId
     * @param studentId
     * @param sectionId
     */
    public Enrollment(Integer enrollmentId, int studentId, int sectionId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }

    /**
     * Constructor to create an enrollment with the given parameters.
     * @param studentId
     * @param sectionId
     */
    public Enrollment(int studentId, int sectionId) {
        // this.studentId = studentId;
        // this.sectionId = sectionId;
        this(null, studentId, sectionId);
    }
   
    /**
     * Get the enrollment id.
     * @return
     */
    public Integer getEnrollmentId() {
        return enrollmentId;
    }
    /**
     * Set the enrollment id.
     * @param enrollmentId
     */
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
    /**
     * Get the student id.
     * @return
     */
    public int getStudentId() {
        return studentId;
    }
    /**
     * Set the student id.
     * @param studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    /**
     * Get the section id.
     * @return
     */
    public int getSectionId() {
        return sectionId;
    }
    /**
     * Set the section id.
     * @param sectionId
     */
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
