package edu.duke.ece651.team1.shared;

/**
 * Represents a professor with a unique prof id, user id, and name.
 */
public class Professor {
    private Integer professorId;
    private  int userId;
    private String professorName;
    /**
     * Constructs a Professor with specified userid, professorid
     * @param professorId The unique identifier of the professor.
     */
    public Professor(Integer professorId, int userId) {
        this.professorId = professorId;
        this.userId = userId;
    }
    /**
     * Constructs a Professor with specified professorid, userid, name
     * @param professorId
     * @param userId
     * @param professorName
     */
    public Professor(Integer professorId, int userId, String professorName) {
        this.professorId = professorId;
        this.userId = userId;
        this.professorName = professorName;
    }
    /**
     * Constructs a Professor with specified unique userid
     * @param userId
     */
    public Professor(int userId){
        this(null, userId);
    }
    /**
     * Constructs a Professor with specified unique name
     */
    public Professor(String displayName){
        this.professorName = displayName;
    }
    /**
     * Constructs a Professor with specified userid and name
     * @param userId
     * @param professorName
     */
    public Professor(int userId, String professorName){
        this.userId = userId;
        this.professorName = professorName;
    }
    /**
     * get professor id
     * @return
     */
    public Integer getProfessorId() {
        return professorId;
    }
    /**
     * get user id
     * @return
     */
    public int getUserId() {
        return userId;
    }
    /**
     * get diaplay name
     * @return
     */
    public String getDisplayName() {
        return professorName;
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
