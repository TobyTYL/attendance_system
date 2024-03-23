package edu.duke.ece651.team1.client.view;

import java.io.InputStreamReader;
import java.io.PrintStream;

import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;

import java.io.BufferedReader;
import java.io.EOFException;
import java.util.List;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
public class AttendanceView {
    BufferedReader inputReader;
    private final PrintStream out;

    public AttendanceView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showTakeAttendanceMenu(String sessionDate) {
        out.printf("=============== Take Attendance for session: %s ===============%n", sessionDate);
        out.println("For each student, please mark their attendance status as follows:");
        out.println("  P - Present");
        out.println("  A - Absent");
        out.println("  T - Tardy");
        out.println("==================================================================");
        out.println("Please enter the attendance status for each student listed below:");
    }
    public void showMarkSuccessMessage(String student, String status){
        out.println("You sucessfully marked " + student + " to "+status);
    }

    public void showAttenceFinishMessage(AttendanceRecord records){
        out.println("Attendance marking completed.");
        out.println("Attendance records List blow:");
        showFinalAttendanceRecord(records);
    }

    public String promptForStudentAttendance(String studentName) throws  IOException{
        out.println("Student Name: " + studentName);
        out.println("Press 'P' to mark Present, 'A' to mark Absent:");
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("End of input reached");
        }
        s = s.toUpperCase();
        if (!s.equals("A") && !s.equals("P") && !s.equals("T")) {
            throw new IllegalArgumentException("That action is invalid: it does not have the correct format.");
        }
        return s;
    }
    public void showFinalAttendanceRecord(AttendanceRecord record){
        Iterable<Map.Entry<Student, AttendanceStatus>> entryList = record.getSortedEntries();
        out.println("Attendance Record for " +record.getSessionDate());
        out.println("=====================================");
        for (Map.Entry<Student, AttendanceStatus> entry :entryList) {
            Student student = entry.getKey();
            AttendanceStatus status = entry.getValue();
            out.println(student.getDisPlayName() + ": " + status.getStatus());
        }
        out.println("=====================================");
    }

    public String readAttendanceOption() throws IOException{
        int option = ViewUtils.getUserOption(inputReader, out, 4);
        if(option == 1){
            return "take";
        }else if(option==2){
            return "modify";
        }else if(option==3){
            return "export";
        }else{
            return "back";
        }
    }

    public void showAttendanceManageOption(){
        out.println("Please select an option to begin:");
        out.println("1. Take attendance for today's class");
        out.println("2. Edit your previous attendance");
        out.println("3. Export attendance records");
        out.println("4. Back to Main Menu");
    }

    public void showExportOption(){
        out.println("Please select an export format:");
        out.println("1. Export as JSON");
        out.println("2. Export as XML");
        out.println("3. Export as CSV");
        out.println("4. Back to attendance Record List");
    }

    public void showExportSuccessMessage(String fileName){
        out.println("Export successful. Your file has been saved to client/src/data directory/"+fileName);
    }

    public void showEmptyDatesMessage(){
         out.println("No Attendance record available. Please try to take attendance first");
    }
    
    public void showAttendanceDateList(List<String> dates){
        for(int i=0;i<dates.size();i++){
            int index = i+1;
            out.println(index+". "+dates.get(i));
        }
        
    }

    public String readExportDateFromPrompt(List<String> dates) throws IOException{
        while (true) {
            try{
                if(dates.isEmpty()){
                    out.println("No Attendance record available. Please try to take attendance first");
                    out.println("press 1 to Go back to the previous menu.");
                }else{
                    showAttendanceDateList(dates);
                    out.println("Which record do you want to export? Please press 1-" + dates.size() + " to specify a specific date record.");
                    out.println("Or press "+(dates.size()+1)+" to Go back to the previous menu.");
                }
                int option = ViewUtils.getUserOption(inputReader, out, dates.size()+1);
                if(option==dates.size()+1){
                    return "back";
                }
                return dates.get(option-1);
            }catch(IllegalArgumentException e){
                out.println("Invalid option for Export Menue.");
            }
        }
        
    }

    public String readFormtFromPrompt() throws IOException{
        while (true) {
            try{
                showExportOption();
                int option = ViewUtils.getUserOption(inputReader, out, 4);
                if(option==1){
                    return "json";
                }else if(option==2){
                    return "xml";
                }else if(option==3){
                    return "csv";
                }else{
                    return "back";
                }
            }catch(IllegalArgumentException e){
                out.println("Invalid option for Export format");
            }
        }
      

    }

    
}
