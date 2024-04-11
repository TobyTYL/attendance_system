package edu.duke.ece651.team1.shared;
/**
 * Enumerates possible attendance statuses for a student, including present, absent, and tardy.
 * Each status is associated with a descriptive string.
 */
public enum AttendanceStatus {
    // Enum constants with descriptive strings
    PRESENT("Present"),
    ABSENT("Absent"),
    TARDY("Tardy");

    private final String status;
    // Constructor to set the status description
    AttendanceStatus(String status) {
        this.status = status;
    }
    // Getter for the status description
    public String getStatus() {
        return status;
    }
    // Method to convert a string to a corresponding AttendanceStatus enum, ignoring case.
    /**
     *  convert a string to a corresponding AttendanceStatus enum, ignoring case.
     * @param statusString
     * @return
     */
    public static AttendanceStatus fromString(String statusString) {
        for (AttendanceStatus status : AttendanceStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(statusString)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + statusString);
    }
}
