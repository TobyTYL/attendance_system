package edu.duke.ece651.team1.client.service;

import org.springframework.stereotype.Service;
import edu.duke.ece651.team1.client.model.*;
import org.springframework.core.ParameterizedTypeReference;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Service for managing student attendance reports. Provides functionality to retrieve both summary and detailed attendance reports.
 */

@Service
public class StudentReportService {
     /**
     * Retrieves the attendance report for the student in the specific class, either in summary or detailed format.
     *
     * @param detail Specifies whether to retrieve a detailed report (true) or a summary report (false).
     * @return The attendance report as a string.
     */
    public String getReport(int sectionId, boolean detail){
        String url = String.format("http://%s:%s/api/attendance/report/student/%d/%d/?detail=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), UserSession.getInstance().getUid(), sectionId,detail);
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
       return ApiService.executeGetRequest(url, responseType);
    }

    public AttendanceSummary getSummaryStatistic(int sectionId){
        String report = getReport(sectionId, false);
        String[] lines = report.split("\n");
        String line = lines[lines.length-1];
        return new AttendanceSummary(line,"");
    }

    
    public Map<String, String> getDetailReport(int sectionId){
        String report = getReport(sectionId, true);
        Map<String, String> attendanceMap = new TreeMap<>();
        String pattern = "(\\d{4}-\\d{2}-\\d{2}): (Present|Tardy|Absent)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(report);
        while (matcher.find()) {
            String sessionDate = matcher.group(1);
            String attendanceStatus = matcher.group(2);
            attendanceMap.put(sessionDate, attendanceStatus);
        }
        return attendanceMap;
    }
}
