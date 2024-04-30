package edu.duke.ece651.team1.client.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Represents a summary of attendance records for an individual, including counts of attended,
 * tardy, and absent sessions, as well as the overall attendance rate.
 */
public class AttendanceSummary {
    private String displayName;
    private int attendedCount;
    private int totalCount;
    private int tardyCount;
    private int absentCount;
    private double attendanceRate;
    
    /**
     * Constructs an AttendanceSummary with specified details.
     *
     * @param displayName the display name of the individual
     * @param attendedCount the number of sessions attended
     * @param totalCount the total number of sessions
     * @param tardyCount the number of sessions tardy
     * @param absentCount the number of sessions absent
     * @param attendanceRate the percentage rate of attendance
     */
    public AttendanceSummary(String displayName, int attendedCount, int totalCount, int tardyCount, int absentCount,
            double attendanceRate) {
        this.displayName = displayName;
        this.attendedCount = attendedCount;
        this.totalCount = totalCount;
        this.tardyCount = tardyCount;
        this.absentCount = absentCount;
        this.attendanceRate = attendanceRate;
    }
     /**
     * Constructs an AttendanceSummary from a formatted string line.
     *
     * @param line the formatted string containing attendance data
     * @throws IllegalArgumentException if the input string does not match the expected format
     */
    public AttendanceSummary(String line) {
        String pattern = "(\\w+): \\w+ (\\d+)/(\\d+) sessions \\((\\d+\\.\\d+)% attendance rate\\), Tardy Count: (\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(line);

        if (matcher.find()) {
            this.displayName = matcher.group(1);
            parse(matcher);
        } else {
            throw new IllegalArgumentException("Invalid input line: " + line);
        }   
    }
    
    /**
     * Constructs an AttendanceSummary with a specified display name and a formatted string line.
     *
     * @param line the formatted string containing attendance data
     * @param displayName the display name to set for the AttendanceSummary
     * @throws IllegalArgumentException if the input string does not match the expected format
     */
    public AttendanceSummary(String line, String displayName){
        String pattern ="(\\w+) \\w+: (\\d+)/(\\d+) \\((\\d+\\.\\d+)% attendance rate\\), Tardy Count: (\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(line);
        if(matcher.find()){
            this.displayName = displayName;
            parse(matcher);
        }else {
            throw new IllegalArgumentException("Invalid input line: " + line);
        }   
    }
    /**
     * Parses the attendance details from the matcher and sets the properties of the AttendanceSummary.
     *
     * @param matcher the Matcher object containing the parsed groups from the regex pattern
     */
    private void parse(Matcher matcher){
        this.attendedCount = Integer.parseInt(matcher.group(2));
        this.totalCount = Integer.parseInt(matcher.group(3));
        this.attendanceRate = Double.parseDouble(matcher.group(4));
        this.tardyCount = Integer.parseInt(matcher.group(5));
        this.absentCount = totalCount - attendedCount - tardyCount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAttendedCount() {
        return attendedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTardyCount() {
        return tardyCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public double getAttendanceRate() {
        return attendanceRate;
    }

    

}
