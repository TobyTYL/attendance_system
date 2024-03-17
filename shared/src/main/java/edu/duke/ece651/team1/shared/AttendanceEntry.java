package edu.duke.ece651.team1.shared;
import java.time.*;

public class AttendanceEntry {
    private LocalDate date;
    private AttendanceStatus status;

    public AttendanceEntry(LocalDate date, AttendanceStatus status){
        this.date = date;
        this.status = status;
    }
    public void updateDate(LocalDate date){
        this.date = date;
    }
    public void updateStatus(AttendanceStatus status){
        this.status = status;
    }
    public LocalDate getDate(){
        return this.date;
    }
    public AttendanceStatus getStatus(){
        return this.status;
    }
    
}
