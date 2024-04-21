package edu.duke.ece651.team1.client.service;
import edu.duke.ece651.team1.shared.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import edu.duke.ece651.team1.client.model.*;
import java.util.*;
@Service
public class AttendanceService {
     /**
     * Fetches the roster of students from the backend service.
     * 
     * @return An iterable collection of Student objects.
     */
    public List<Student> getRoaster(int sectionId) {
        ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<List<Student>>() {
        };
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/allStudents/" + sectionId;
        return ApiService.executeGetRequest(url, responseType);
    }
    /**
     * Fetches the class report from the backend service.
     * @return
     */
    public String getClassReport(int sectionId){
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/report/class/" + sectionId;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
       return ApiService.executeGetRequest(url, responseType);   
    }
    
    

    /**
     * Fetches the list of dates for which attendance records are available.
     * 
     * @return A list of dates as strings.
     */
    public List<String> getRecordDates(int sectionId) {
        ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<List<String>>() {
        };
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record-dates/" + sectionId;
        return ApiService.executeGetRequest(url, responseType);
    }

    /**
     * Fetches an attendance record for a specific session date.
     * 
     * @param sessionDate The date of the session.
     * @return An AttendanceRecord object.
     */
    public AttendanceRecord getAttendanceRecord(String sessionDate, int sectionId) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record/" + sectionId + "/" + sessionDate;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
                };
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String record = ApiService.executeGetRequest(url,  responseType);
        return serializer.deserialize(record);
    }

    /**
     * Sends an attendance record to the backend service for storage.
     * 
     * @param record The AttendanceRecord to be sent.
     */
    public void sendAttendanceRecord(AttendanceRecord record,int sectionId) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record/" + sectionId;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
                };
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordToJsonString = serializer.serialize(record);
        ApiService.executePostPutRequest(url, recordToJsonString, responseType, true);
    }
    /**
     * Updates an attendance record in the backend service.
     * @param record
     */
    public void updateAttendanceRecord(AttendanceRecord record, int sectionId) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record/" + sectionId;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordToJsonString = serializer.serialize(record);
        ApiService.executePostPutRequest(url, recordToJsonString, responseType, false);
    }
  
}
