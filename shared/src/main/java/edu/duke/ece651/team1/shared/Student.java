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
        this.legalName = legalName;
        this.disPlayName = legalName;
        this.email = "";

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
}
