package edu.duke.ece651.team1.client.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AttendanceSummary {
    private String displayName;
    private int attendedCount;
    private int totalCount;
    private int tardyCount;
    private int absentCount;
    private double attendanceRate;

    public AttendanceSummary(String displayName, int attendedCount, int totalCount, int tardyCount, int absentCount,
            double attendanceRate) {
        this.displayName = displayName;
        this.attendedCount = attendedCount;
        this.totalCount = totalCount;
        this.tardyCount = tardyCount;
        this.absentCount = absentCount;
        this.attendanceRate = attendanceRate;
    }

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
