package edu.duke.ece651.team1.shared;

public class Student {
    private final String legalName;
    private String disPlayName;
    private String email;
    public Student(String legalName, String disPlayName, String email) {
        this.legalName = legalName;
        this.disPlayName = disPlayName;
        this.email = email;
    }
    
    public Student(String legalName) {
        // this.legalName = legalName;
        // this.disPlayName = legalName;
        // this.email = "";
        this(legalName,legalName,"");
    }

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
