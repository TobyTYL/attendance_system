package edu.duke.ece651.team1.shared;

public class Section {
    private int sectionId;
    private int classId;
    private int professorId;

    public Section(int sectionId, int classId, int professorId) {
        this.sectionId = sectionId;
        this.classId = classId;
        this.professorId = professorId;
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
}
