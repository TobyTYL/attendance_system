package edu.duke.ece651.team1.shared;

/**
 * Represents a professor with a unique identifier, legal name, and email address.
 */
public class Professor {
    private final int professorId;
    private final String legalName;
    private final int userId;

    /**
     * Constructs a Professor with specified unique identifier, legal name, and email.
     * @param professorId The unique identifier of the professor.
     * @param legalName The professor's legal name.
     */
    public Professor(int professorId, String legalName, int userId) {
        this.professorId = professorId;
        this.legalName = legalName;
        this.userId = userId;
    }

    /**
     * Constructs a Professor with a legal name and no email.
     * @param legalName The professor's legal name.
     */
    public Professor(String legalName, int userId) {
        this(0, legalName, userId);
    }

    public int getProfessorId() {
        return professorId;
    }

    public String getLegalName() {
        return legalName;
    }

    public int getUserId() {
        return userId;
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(getClass())) {
            Professor p = (Professor) obj;
            return professorId == p.professorId && legalName.equals(p.legalName) && userId == p.userId;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Professor [professorId=" + professorId + ", legalName=" + legalName + ", userId=" + userId + "]";
    }
}
