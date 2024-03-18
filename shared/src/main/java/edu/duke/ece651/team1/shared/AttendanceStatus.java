package edu.duke.ece651.team1.shared;

public enum AttendanceStatus {
    PRESENT("Present"),
    ABSENT("Absent"),
    TARDY("Tardy");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public static AttendanceStatus fromString(String statusString) {
        for (AttendanceStatus status : AttendanceStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(statusString)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + statusString);
    }
}
