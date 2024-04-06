package edu.duke.ece651.team1.shared;

/**
 * Represents a professor with a unique identifier, legal name, and email address.
 */
public class Professor {
    private Integer professorId;
    private  int userId;
    private String displayName;
    /**
     * Constructs a Professor with specified unique identifier, legal name, and email.
     * @param professorId The unique identifier of the professor.
     */
    public Professor(Integer professorId, int userId) {
        this.professorId = professorId;
        this.userId = userId;
    }
    public Professor(Integer professorId, int userId, String displayName) {
        this.professorId = professorId;
        this.userId = userId;
        this.displayName = displayName;
    }

    public Professor(int userId){
        this(null, userId);
    }
    public Professor(String displayName){
        this.displayName = displayName;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getUserId() {
        return userId;
    }
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(getClass())) {
            Professor p = (Professor) obj;
            return professorId == p.professorId && userId == p.userId;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Professor [professorId=" + professorId + ", userId=" + userId + "]";
    }
}
