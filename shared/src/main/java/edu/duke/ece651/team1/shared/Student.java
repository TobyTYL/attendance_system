package edu.duke.ece651.team1.shared;
/**
 * Represents a student with a legal name, display name, and email address.
 * Provides multiple constructors to support various ways of creating a Student object.
 */
public class Student {
    private final String legalName;
    private String disPlayName;
    private String email;
    /**
     * Constructs a Student with specified legal name, display name, and email.
     * @param legalName The student's legal name.
     * @param disPlayName The name displayed in the application.
     * @param email The student's email address.
     */
    public Student(String legalName, String disPlayName, String email) {
        this.legalName = legalName;
        this.disPlayName = disPlayName;
        this.email = email;
    }
    
    /**
     * Constructs a Student with a legal name and uses it as the display name; no email.
     * @param legalName The student's legal name and display name.
     */
    public Student(String legalName) {
        // this.legalName = legalName;
        // this.disPlayName = legalName;
        // this.email = "";
        this(legalName,legalName,"");
    }
    
    /**
     * Constructs a Student with a legal name and email, and uses the legal name as the display name.
     * @param legalName The student's legal name.
     * @param email The student's email address.
     */
    public Student(String legalName, String email){
        this(legalName,legalName,email);
    }

    public Student(){
        this("","","");
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
        return "Student [legalName=" + legalName + ", disPlayName=" + disPlayName + ", email=" + email + "]";
    }
}
