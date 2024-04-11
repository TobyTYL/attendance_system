package edu.duke.ece651.team1.client.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import edu.duke.ece651.team1.client.view.StudentReportView;
import edu.duke.ece651.team1.client.model.UserSession;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.core.ParameterizedTypeReference;
import edu.duke.ece651.team1.client.model.UserSession;
import java.util.List;
import org.springframework.http.*;
/**
 * The StudentReportController class manages the generation and display of attendance reports for a student in a specific class.
 * It allows the user to view both summary and detailed reports for their attendance.
 * This controller interacts with the StudentReportView for user input and utilizes a RestTemplate
 * for HTTP requests to the backend service.
 */
public class StudentReportController {
    BufferedReader inputReader;
    final PrintStream out;
    private StudentReportView view;
    private int sectionId;
    private String className;
    private int studentId;
    private RestTemplate restTemplate;
    /**
     * Constructs a StudentReportController with the specified input reader, output stream, section ID, class name, and student ID.
     *
     * @param inputReader The BufferedReader to read user input.
     * @param out         The PrintStream to output data to the user.
     * @param sectionId   The ID of the class section.
     * @param className   The name of the class.
     * @param studentId   The ID of the student.
     */
    public StudentReportController(BufferedReader inputReader, PrintStream out, int sectionId, String className,int studentId) {
        this.inputReader = inputReader;
        this.out = out;
        this.sectionId = sectionId;
        this.className = className;
        this.studentId = studentId;
        this.view = new StudentReportView(inputReader, out);
        this.restTemplate = new RestTemplate();
        
    }
    /**
     * Starts the report menu for the student, allowing them to choose between a summary or detailed report.
     */
    public void startReportMenue(){
        while (true) {
            view.showReportMenue(className);
            try{
                String option = view.readReportOpetion();
                if(option.equals("summary") || option.equals("detail")){
                    boolean detail = option.equals("detail")?true:false;
                    String report = getReport(detail);
                    view.showReport(report, className);           
                }else{
                    return;
                }
            }catch(Exception e){
                out.println("Exception happen in student report controller because "+e.getMessage());
            }
        }
    }
    /**
     * Retrieves the attendance report for the student in the specific class, either in summary or detailed format.
     *
     * @param detail Specifies whether to retrieve a detailed report (true) or a summary report (false).
     * @return The attendance report as a string.
     */
    public String getReport(boolean detail){
        String url = String.format("http://%s:%s/api/attendance/report/student/%d/%d/?detail=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, sectionId,detail);
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
       return ControllerUtils.executeGetRequest(url, responseType);
    }
}
