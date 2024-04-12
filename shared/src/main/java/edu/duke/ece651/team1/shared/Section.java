package edu.duke.ece651.team1.shared;
/**
 * Represents a section of a course, with a unique section id, class id, and professor id.
 
 */
public class Section {
    private int sectionId;
    private int classId;
    private int professorId;
//    private String className; // Add className attribute

    /**
     * Constructor to create a section with the given parameters.
     * @param sectionId
     * @param classId
     * @param professorId
     */
    public Section(int sectionId, int classId, int professorId) {
        this.sectionId = sectionId;
        this.classId = classId;
        this.professorId = professorId;

    }
    /**
     * Constructor to create a section with the given parameters.
     * @param classId
     * @param professorId
     */
    public Section(int classId, int professorId){
        // edit here
//        this(-1,classId, professorId);
        this.classId = classId;
        this.professorId = professorId;
//        this.className = className; // Set className
    }

    // Getters
    public int getSectionId() {
        return sectionId;
    }

    public int getClassId() {
        return classId;
    }

    public int getProfessorId() {
        return professorId;
    }
//    public String getClassName() {
//        return className;
//    }


    // Setters
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }
//    public void setClassName(String className) {
//        this.className = className;
//    }
}
