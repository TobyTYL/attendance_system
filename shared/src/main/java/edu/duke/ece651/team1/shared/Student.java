package edu.duke.ece651.team1.shared;
/**
 * Represents a student with a legal name, display name, and email address.
 * Provides multiple constructors to support various ways of creating a Student object.
 */
public class Student {
    private Integer studentId;
    private final String legalName;
    private String disPlayName;
    private String email;
    private Integer userId;

    /**
     * Constructs a Student with specified legal name, display name, and email.
     * @param legalName The student's legal name.
     * @param disPlayName The name displayed in the application.
     * @param email The student's email address.
     */
    // public Student(Integer studentId, String legalName, String disPlayName, String email) {
    //     this(studentId, legalName,disPlayName,email,null);

    // }
    public Student(Integer studentId, String legalName, String disPlayName, String email, Integer userId) {
        this.studentId = studentId;
        this.legalName = legalName;
        this.disPlayName = disPlayName;
        this.email = email;
        this.userId = userId;
    }

    public Student(int uid,String legalName, String disPlayName, String email) {
        this.legalName = legalName;
        this.disPlayName = disPlayName;
        this.email = email;
        this.userId = uid;
    }

    /**
     * Constructs a Student with a legal name and uses it as the display name; no email.
     * @param legalName The student's legal name and display name.
     */
    public Student(String legalName) {
        // this.legalName = legalName;
        // this.disPlayName = legalName;
        // this.email = "";
        this(null, legalName,legalName,"",null);
    }

    public Student(String legalName, String displayName,String email){
        this(null,legalName,displayName,email,null);
    }
    public Student(String legalName, String email){
        this(null,legalName,legalName,email,null);
    }



    public Student(){
        this(null, "", "", "",null);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getDisPlayName() {
        return disPlayName;
    }

    public String getEmail() {
        return email;
    }
    public void setStudentEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void updateDisplayName(String dispalyName){
        this.disPlayName = dispalyName;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(getClass())) {
            Student s = (Student) obj;
            return legalName.equals(s.legalName) && disPlayName.equals(s.disPlayName) && email.equals(s.email);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", legalName=" + legalName + ", disPlayName=" + disPlayName + ", email=" + email + "]";
    }
}
