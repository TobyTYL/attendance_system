CREATE TABLE IF NOT EXISTS Users (
    UserID SERIAL PRIMARY KEY,
    Username VARCHAR(255) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    Role VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Students (
    StudentID SERIAL PRIMARY KEY,
    UserID INT NOT NULL UNIQUE,
    LegalName VARCHAR(255) NOT NULL,
    DisplayName VARCHAR(255),
    Email VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE IF NOT EXISTS Professors (
    ProfessorID SERIAL PRIMARY KEY,
    UserID INT NOT NULL UNIQUE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE IF NOT EXISTS Classes (
    ClassID SERIAL PRIMARY KEY,
    ClassName VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Sections (
    SectionID SERIAL PRIMARY KEY,
    ClassID INT NOT NULL,
    ProfessorID INT NOT NULL,
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID),
    FOREIGN KEY (ProfessorID) REFERENCES Professors(ProfessorID)
);

CREATE TABLE IF NOT EXISTS Enrollment (
    EnrollmentID SERIAL PRIMARY KEY,
    StudentID INT NOT NULL,
    SectionID INT NOT NULL,
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (SectionID) REFERENCES Sections(SectionID)
);

CREATE TABLE IF NOT EXISTS AttendanceRecords (
    AttendanceRecordID SERIAL PRIMARY KEY,
    SectionID INT NOT NULL,
    SessionDate DATE NOT NULL,
    FOREIGN KEY (SectionID) REFERENCES Sections(SectionID)
);

CREATE TABLE IF NOT EXISTS AttendanceEntries (
    AttendanceEntryID SERIAL PRIMARY KEY,
    AttendanceRecordID INT NOT NULL,
    StudentID INT NOT NULL,
    AttendanceStatus VARCHAR(50) NOT NULL,
    FOREIGN KEY (AttendanceRecordID) REFERENCES AttendanceRecords(AttendanceRecordID),
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID)
);

CREATE TABLE IF NOT EXISTS NotificationPreference (
    PreferenceID SERIAL PRIMARY KEY,
    StudentID INT NOT NULL,
    ClassID INT NOT NULL,
    ReceiveNotifications BOOLEAN NOT NULL,
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

