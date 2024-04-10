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
public class StudentReportController {
    BufferedReader inputReader;
    final PrintStream out;
    private StudentReportView view;
    private int sectionId;
    private String className;
    private int studentId;
    private RestTemplate restTemplate;

    public StudentReportController(BufferedReader inputReader, PrintStream out, int sectionId, String className,int studentId) {
        this.inputReader = inputReader;
        this.out = out;
        this.sectionId = sectionId;
        this.className = className;
        this.studentId = studentId;
        this.view = new StudentReportView(inputReader, out);
        this.restTemplate = new RestTemplate();
        
    }

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

    public String getReport(boolean detail){
        String url = String.format("http://%s:%s/api/attendance/report/student/%d/%d/?detail=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, sectionId,detail);
        HttpEntity<String> requestEntity = new HttpEntity<>(ControllerUtils.getSessionTokenHeaders());
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to fetch student report: " + response.getStatusCode());
        }
        return response.getBody();
    }
}
