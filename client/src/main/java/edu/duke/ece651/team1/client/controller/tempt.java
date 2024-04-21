// package edu.duke.ece651.team1.client.controller;

// import edu.duke.ece651.team1.client.model.UserSession;
// import edu.duke.ece651.team1.client.view.*;
// import edu.duke.ece651.team1.client.model.UserSession;
// import edu.duke.ece651.team1.shared.*;

// import java.io.BufferedReader;
// import java.io.PrintStream;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;
// import java.security.PublicKey;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;

// import org.springframework.core.ParameterizedTypeReference;
// import java.util.List;
// import java.util.Map;

// /**
//  * The AttendanceController class manages the attendance functionality of the
//  * application.
//  * It handles operations such as taking attendance, modifying attendance
//  * records, and exporting attendance data.
//  * This controller interacts with the AttendanceView for user input and displays
//  * and utilizes a RestTemplate
//  * for HTTP requests to the backend service.
//  */
// public class AttendanceController {
//     int sectionId;
//     BufferedReader inputReader;
//     final PrintStream out;
//     AttendanceView attendanceView;


//     /**
//      * Constructor to initialize the AttendanceController with input and output
//      * streams.
//      * 
//      * @param inputReader The BufferedReader to read user input.
//      * @param out         The PrintStream to output data to the user.
//      */
//     public AttendanceController(BufferedReader inputReader, PrintStream out, int sectionId) {
//         this.inputReader = inputReader;
//         this.out = out;
//         this.attendanceView = new AttendanceView(inputReader, out);
//         this.sectionId = sectionId;
//     }

  
//     /**
//      * Displays the attendance management menu and handles user interaction for
//      * taking attendance,
//      * modifying attendance records, and exporting attendance data.
//      * 
//      * @throws IOException If an I/O error occurs during user interaction.
//      */
//     public void startAttendanceMenue() throws IOException {
//         while (true) {
//             try {
//                 attendanceView.showAttendanceManageOption();
//                 String option = attendanceView.readAttendanceOption();
//                 if (option.equals("take")) {
//                     startAttendance();
//                 } else if (option.equals("modify")) {
//                     startModify();
//                 } else if (option.equals("export")) {
//                     startExport();
//                 } else if(option.equals("report")) {
//                     startReport();
//                 }else{
//                     return;
//                 }
//             } catch (IllegalArgumentException e) {
//                 out.println("Invalid option for Attendance Management Menue");

//             }

//         }
//     }
//     /**
//      * Displays the class report.
//      */
//     public void startReport(){
//         String report = getClassReport();
//         attendanceView.showClassReport(report);
//     }

//     /**
//      * Fetches the roster of students from the backend service.
//      * 
//      * @return An iterable collection of Student objects.
//      */
//     protected Iterable<Student> getRoaster() {
//         ParameterizedTypeReference<List<Student>> responseType = new ParameterizedTypeReference<List<Student>>() {
//         };
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/allStudents/" + sectionId;
//         return ControllerUtils.executeGetRequest(url, responseType);
//     }
//     /**
//      * Fetches the class report from the backend service.
//      * @return
//      */
//     private String getClassReport(){
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/report/class/" + sectionId;
//         ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
//         };
//        return ControllerUtils.executeGetRequest(url, responseType);   
//     }

    

//     /**
//      * Fetches the list of dates for which attendance records are available.
//      * 
//      * @return A list of dates as strings.
//      */
//     private List<String> getRecordDates() {
//         ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<List<String>>() {
//         };
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/record-dates/" + sectionId;
//         return ControllerUtils.executeGetRequest(url, responseType);
//     }

//     /**
//      * Fetches an attendance record for a specific session date.
//      * 
//      * @param sessionDate The date of the session.
//      * @return An AttendanceRecord object.
//      */
//     private AttendanceRecord getAttendanceRecord(String sessionDate) {
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/record/" + sectionId + "/" + sessionDate;
//         ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
//                 };
//         JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
//         String record = ControllerUtils.executeGetRequest(url,  responseType);
//         return serializer.deserialize(record);
//     }

//     /**
//      * Sends an attendance record to the backend service for storage.
//      * 
//      * @param record The AttendanceRecord to be sent.
//      */
//     private void sendAttendanceRecord(AttendanceRecord record) {
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/record/" + sectionId;
//         ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
//                 };
//         JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
//         String recordToJsonString = serializer.serialize(record);
//         ControllerUtils.executePostPutRequest(url, recordToJsonString, responseType, true);
//     }
//     /**
//      * Updates an attendance record in the backend service.
//      * @param record
//      */
//     private void updateAttendanceRecord(AttendanceRecord record) {
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/record/" + sectionId;
//         ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
//         };
//         JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
//         String recordToJsonString = serializer.serialize(record);
//         ControllerUtils.executePostPutRequest(url, recordToJsonString, responseType, false);
//     }

//     /**
//      * Initiates the process of taking attendance for a session.
//      * 
//      * @throws IOException If an I/O error occurs during user interaction.
//      */
//     public void startAttendance() throws IOException {
//         Iterable<Student> students;
//         try {
//             students = getRoaster();
           
//         } catch (IllegalArgumentException e) {
//             attendanceView.showNoRosterMessage();
//             return;
//         }

//         AttendanceRecord record = new AttendanceRecord(LocalDate.now());
//         attendanceView.showTakeAttendanceMenu(record.getSessionDate().toString());
//         record.initializeFromRoaster(students);
//         for (Student s : students) {
//             while (true) {
//                 try {
//                     String statusOption = attendanceView.promptForStudentAttendance(s.getDisPlayName(),true);
//                     if (statusOption.equals("P")) {
//                         record.markPresent(s);
//                         attendanceView.showMarkSuccessMessage(s.getDisPlayName(), "Present");
//                         break;
//                     } else if (statusOption.equals("A")) {
//                         record.markAbsent(s);
//                         attendanceView.showMarkSuccessMessage(s.getDisPlayName(), "Absent");
//                         break;
//                     } else {
//                         throw new IllegalArgumentException("That Attendance mark option is in valid,");
//                     }
//                 } catch (IllegalArgumentException e) {
//                     out.println("Invalid option. Please select A for Absent or P for present.");

//                 }
//             }
//         }
//         sendAttendanceRecord(record);
//         attendanceView.showAttenceFinishMessage(record);

//     }
//     /**
//      * Modifies the attendance record for a specific student.
//      * @param record
//      * @throws IOException
//      */
//     public void modifyOneStudent(AttendanceRecord record) throws IOException {
//         if (record == null || record.getEntries().isEmpty()) {
//             out.println("No attendance record available for this date.");
//             return;
//         }
//         String selectedStudentName = attendanceView.promptForStudentName(record);
//         if ("back".equals(selectedStudentName)) {
//             return; // Exit if user wants to go back
//         }
//         // Prompt for new attendance status
//         AttendanceStatus newStatus = attendanceView.promptForAttendanceStatus();
//         try {
//             modifyAttendanceRecord(record.getSessionDate().toString(), selectedStudentName,
//                     newStatus);
//             attendanceView.showModifySuccessMessage(selectedStudentName, newStatus.toString());
//             // out.println(modifyResult); // Show success or error message from the server
//         } catch (Exception e) {
//             out.println("Failed to modify attendance record: " + e.getMessage());
//         }
//     }
//     /**
//      * Modifies the attendance record for all students.
//      * @param record
//      * @throws IOException
//      */
//     public void modifyAllStudents(AttendanceRecord record) throws IOException {
//         for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
//             Student s = entry.getKey();
//             while (true) {
//                 try {
//                     String statusOption = attendanceView.promptForStudentAttendance(s.getDisPlayName(),false);
//                     if (statusOption.equals("P")) {
//                         record.updateStudentStatus(s, AttendanceStatus.PRESENT);
//                         attendanceView.showUpdateSuccessMessage(s.getDisPlayName(), "Present");
//                         break;
//                     } else if (statusOption.equals("T")) {
//                         record.updateStudentStatus(s, AttendanceStatus.TARDY);
//                         attendanceView.showUpdateSuccessMessage(s.getDisPlayName(), "Tardy");
//                         break;
//                     } else {
//                         record.updateStudentStatus(s, AttendanceStatus.ABSENT);
//                         attendanceView.showUpdateSuccessMessage(s.getDisPlayName(), "Absent");
//                         break;
//                     }
//                 } catch (IllegalArgumentException e) {
//                     out.println("Invalid option. Please select A for Absent or P for present.");

//                 }
//             }
//         }
//         updateAttendanceRecord(record);
//         attendanceView.showUpdateFinishMessage(record);
//     }

//     /**
//      * Initiates the process for modifying an existing attendance record.
//      * 
//      * @throws IOException If an I/O error occurs during user interaction.
//      */
//     public void startModify() throws IOException {
//         List<String> dates = getRecordDates();
//         if (dates.isEmpty()) {
//             out.println("No attendance records available. Please take attendance first.");
//             return;
//         }

//         String selectedDate = attendanceView.promptForDateSelection(dates);
//         if ("back".equals(selectedDate)) {
//             return; // Exit if user wants to go back
//         }
//         // Fetch the attendance record for the selected date
//         AttendanceRecord record = getAttendanceRecord(selectedDate);
//         attendanceView.showModifyMenue();
//         String option = attendanceView.readModifyOption();
//         if(option.equals("retake")){
//             modifyAllStudents(record);
//         }else{
//             modifyOneStudent(record);
//         }
//     }
//     /**
//      * Modifies the attendance record for a specific student.
//      * @param sessionDate
//      * @param studentName
//      * @param newStatus
//      */
//     private void modifyAttendanceRecord(String sessionDate, String studentName, AttendanceStatus newStatus) {
//         String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                 + "/api/attendance/modification/" + sectionId + "/" + sessionDate;

//         // Construct the attendance entry JSON
//         String attendanceEntryJson = String.format("{\"Legal Name\":\"%s\", \"Attendance Status\":\"%s\"}",
//                 studentName, newStatus.name());
//         ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
//         };
//         ControllerUtils.executePostPutRequest(url, attendanceEntryJson, responseType, true);
       
//     }

//     /**
//      * Initiates the process for exporting attendance records in various formats.
//      * 
//      * @throws IOException If an I/O error occurs during user interaction.
//      */
//     public void startExport() throws IOException {
//         while (true) {
//             List<String> dates = getRecordDates();
//             String dateOrBack = attendanceView.readExportDateFromPrompt(dates);
//             if ("back".equals(dateOrBack)) {
//                 return;
//             } else {
//                 while (true) {
//                     // get record of that date
//                     AttendanceRecord record = getAttendanceRecord(dateOrBack);
//                     String formatOrBack = attendanceView.readFormtFromPrompt();
//                     if ("back".equals(formatOrBack)) {
//                         break;
//                     } else {
//                         AttendanceRecordExporterFactory factory = new AttendanceRecordExporterFactory();
//                         String filePath = "src/data/";
//                         String fileName = "Attendance-" + dateOrBack;
//                         AttendanceRecordExporter exporter;
//                         if (formatOrBack.equals("json")) {
//                             exporter = factory.createExporter("json");
//                         } else if (formatOrBack.equals("xml")) {
//                             exporter = factory.createExporter("xml");
//                         } else {
//                             exporter = factory.createExporter("csv");
//                         }
//                         exporter.exportToFile(record, fileName, filePath);
//                         attendanceView.showExportSuccessMessage(fileName + "." + formatOrBack);
//                     }
//                     return;
//                 }
//             }
//         }
//     }
// }
