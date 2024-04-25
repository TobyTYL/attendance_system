package edu.duke.ece651.team1.client.service;

import edu.duke.ece651.team1.shared.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import edu.duke.ece651.team1.client.model.*;
import java.util.*;
import java.io.*;
import org.springframework.web.client.*;


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
         * 
         * @return
         */
        public String getClassReport(int sectionId) {
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
                String record = ApiService.executeGetRequest(url, responseType);
                return serializer.deserialize(record);
        }

        /**
         * Sends an attendance record to the backend service for storage.
         * 
         * @param record The AttendanceRecord to be sent.
         */
        public void sendAttendanceRecord(AttendanceRecord record, int sectionId) {
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
         * 
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

        public void exportRecord(String sessionDate, int sectionId, String format) {
                AttendanceRecord record = getAttendanceRecord(sessionDate, sectionId);
                AttendanceRecordExporterFactory factory = new AttendanceRecordExporterFactory();
                String filePath = "src/data/";
                String fileName = "Attendance-" + sessionDate;
                AttendanceRecordExporter exporter;
                if (format.equals("json")) {
                        exporter = factory.createExporter("json");
                        // response.setContentType("application/json");

                } else if (format.equals("xml")) {
                        exporter = factory.createExporter("xml");
                        // response.setContentType("application/xml");

                } else {
                        exporter = factory.createExporter("csv");
                        // response.setContentType("text/csv");
                }
                try {
                        exporter.exportToFile(record, fileName, filePath);

                } catch (Exception e) {
                        e.printStackTrace();
                }
                // File file = new File(filePath + fileName + "." + format);
                // try (OutputStream out = new BufferedOutputStream(response.getOutputStream());
                // FileInputStream in = new FileInputStream(file)) {
                // response.setContentType("application/octet-stream");
                // response.setHeader("Content-Disposition", "attachment; filename=" +
                // file.getName());
                // response.setContentLength((int) file.length());

                // byte[] buffer = new byte[1024];
                // int numBytesRead;
                // while ((numBytesRead = in.read(buffer)) > 0) {
                // out.write(buffer, 0, numBytesRead);
                // }
                // } catch (Exception e) {
                // e.printStackTrace();
                // }

        }

        public List<AttendanceSummary> getAttendancestatistic(int sectionId) {
                String report = getClassReport(sectionId);
                String[] lines = report.split("\n");
                List<AttendanceSummary> summaries = new ArrayList<>();
                for (int i = 1; i < lines.length; i++) {
                        String line = lines[i];
                        summaries.add(new AttendanceSummary(line));
                }
                return summaries;
        }

        public String updateStudentAttendance(int sectionId, String sessionDate, int studentId) {

                String url = String.format("http://%s:%s/api/attendance/mark/%d/%s/%d",
                                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), sectionId,
                                sessionDate, studentId);
                ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
                };
                try{
                        ApiService.executePostPutRequest(url, null, responseType, true);
                        return "success";
                }catch(HttpClientErrorException e){
                        return "Authen failed: You are not student in this class, Your attendance cannot be marked";
                }
        }

}
