# Attandance Management Software Requirements Specification

## Introduction

### Purpose

The purpose of this Software Requirements Specification (SRS) document is to provide a comprehensive outline of the evolved Attendance Management System (AMS), now redesigned for academic settings with a client/server architecture. This new iteration of AMS focuses on enhancing the user experience for a diverse range of users, including faculty, students, and administrative staff, across various classes and sections. It brings forth a more efficient and streamlined process for managing attendance in educational environments.


### Scope

The evolved Attendance Management System (AMS) is re-envisioned as a comprehensive client/server application, aimed at enhancing attendance recording and management processes in educational institutions. This iteration introduces significant enhancements to accommodate a broader range of functionalities, tailored to both faculty and students in a multi-class environment. The scope of the AMS now includes:

#### Client/Server Architecture

The AMS is architectured with a centralized server hosting the repository of student attendance, accessed by lightweight client applications. This design ensures efficient data management and processing, primarily handled by the server operating on a Linux VM.

#### Multiple User Roles and Authentication

The system supports various user roles, including faculty and students, each with distinct authentication and authorization capabilities. The user administration, including the addition, removal, and updating of users, is handled through a standalone application, not integrated into the client/server model.

#### Class and Section Management

Faculty can manage multiple classes and sections, with functionalities to add, remove, update, and enroll students. Enrollment can be conducted both manually and through batch processes, such as CSV file uploads.

#### Attendance Recording and Management

The core functionality of AMS remains the swift and efficient recording of attendance by faculty at the beginning of each class, with an enhanced interface for selecting specific classes and sections.

#### Late Arrivals and Attendance Amendments

Faculty can amend attendance records post-class to account for late arrivals or other changes, marking students as tardy or modifying their attendance status.

#### Data Storage in RDBMS

Replacing plain text files, the AMS now utilizes a Relational Database Management System (RDBMS)

#### Data Management in Program Using the DAO Pattern
The AMS employs the Data Access Object (DAO) pattern to effectively manage interactions with the underlying RDBMS. This architectural pattern provides a flexible framework for accessing data sources and manipulating persisted data

#### Notifications and Communication Preferences

Students will by default receive automatic notifications on a weekday. Notifications include email alerts about attendance status and weekly reports. Students have the flexibility to modify their preference regarding whether to receive email notifications. If students disable the notification for a class, they will not receive the attendance notification or the weekly report.

#### Detailed Reporting

The AMS provides detailed attendance reports for both faculty and students. Faculty reports include attendance summaries with particulars like tardiness, while students can access class-specific attendance records.

#### Security and Data Integrity

Considering the sensitivity of attendance data, enhanced security measures are implemented to protect against unauthorized access and ensure data integrity, in line with the system's client/server architecture.

#### System and Platform Flexibility

The AMS maintains its platform independence, adaptable to various operating systems, but now prioritizes a user-friendly client interface with minimal processing demands on the client side.

### Definitions, acronyms and abbreviations


The following table provides definitions for terms and acronyms used within this document.


| Term      | Definition                                                                                                                                                                                                        |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Professor | An individual responsible for delivering course content, managing and publishing courses, managing student information, managing classroom activities, and recording student attendance.                          |
| Student   | An individual enrolled in the course who participates in classroom activities and whose attendance is tracked by the system. A student shall receive reports and notifications regarding their attendance status. |
| User      | An individual with access to create, read, update, and delete (CRUD) all data within the system. This role typically encapsulates administrative privileges or specialized access rights.                         |


## Overall description

### Product perspective

The Attendance Management System (AMS) is a self-contained, stand-alone software solution intended to modernize and streamline the process of managing student attendance in educational institutions. It is not a component of a larger system but is designed to operate independently, interfacing directly with users through a text-based terminal. The AMS can integrate with existing university systems to import student roster data and may export attendance records for use in other applications or reporting tools. Its cross-platform capability allows it to function on various operating systems such as Windows, macOS, and Linux, thereby ensuring wide accessibility for users regardless of their hardware preferences.

The AMS is designed to function without the need for specialized hardware, relying instead on standard computing devices available to most educational professionals. From the product perspective, the AMS aims to occupy a unique position in the market by combining ease of use with robust functionality, prioritizing data security and user accessibility.



### Product Functions

The evolved Attendance Management System (AMS) encompasses a range of enhanced functionalities to address the complexities of attendance management in an academic setting, particularly in a client/server environment:

#### Client/Server-Based Attendance Recording and Management

- **Centralized Attendance Tracking:** Allows faculty to record attendance via client applications interfacing with the centralized server, ensuring real-time data accuracy.
- **Dynamic Updating and Status Modification:** Faculty can mark students present, absent, or tardy through the client interface, with updates immediately reflected on the server.

#### Robust Roster Management

- **Database-Driven Roster Import and Management:** Supports importing student rosters from various formats like CSV into the RDBMS, facilitating seamless enrollment updates.
- **Enrollment Adjustments:** Automatically updates rosters as students enroll or drop out, maintaining current class composition.

#### Comprehensive Late Arrival Handling

- **Late Arrival Marking:** Provides a straightforward method for faculty to mark students tardy, reflecting in the server database.
- **Adjustable Attendance Records:** Allows post-class amendments to attendance records, accommodating late arrivals and other changes.

#### Advanced Data Storage and Export Capabilities

- **RDBMS Storage:** Transition from plain text to RDBMS like MySQL for robust, scalable, and secure storage of attendance data.
- **Flexible Data Export:** Supports exporting data in formats like JSON, XML, and others, catering to diverse reporting and integration needs.

#### Enhanced Automated Communication

- **Configurable Notifications:** Facilitates automated, customizable attendance notifications to students based on their preferences and specific class requirements.
- **Detailed Weekly and Real-Time Reports:** Generates comprehensive attendance reports for students, offering insights into their attendance patterns.

#### User-Centric Interaction Design

- **Intuitive Client Applications:** Develops user-friendly interfaces for both faculty and student clients, streamlining the attendance recording and checking process.
- **Advanced Search and Navigation:** Enhances the capability to search and navigate through class lists for efficient and accurate attendance management.

#### Reinforced Security

- **Enhanced Data Protection:** Incorporates advanced security measures to safeguard sensitive attendance information, in line with client/server architecture.
- **Strict Access Controls:** Implements robust authentication and authorization mechanisms to ensure system access is restricted to authorized users only.

These enhanced functions aim to significantly improve the attendance management process, reducing administrative efforts and elevating the accuracy and efficiency of attendance tracking in educational institutions.


### Constraints

The development and implementation of the Attendance Management System (AMS) must adhere to several constraints that define its operational parameters. These constraints are essential to ensure the system's compatibility, usability, security, and scalability:

- **Client/Server Architecture Compatibility**: The AMS, transitioning to a client/server model, must ensure seamless interaction between client applications and the central server. This includes compatibility with various client operating systems, including Windows, macOS, and Linux, and server stability on a Linux VM.

- **Interface and Interaction Limitations**: Considering the user-friendly, light-client design, the interface is constrained to be straightforward yet functional, focusing on accessibility and ease of use without complex graphical elements.

- **Enhanced Security Measures**: The AMS must implement advanced security protocols, particularly in the client/server communication, authentication processes, and data encryption, to safeguard sensitive information against unauthorized access.

- **RDBMS-Based Data Storage**: Shifting from file-based to database storage, the system must efficiently manage data within a Relational Database Management System, accommodating large datasets and ensuring data integrity.

- **Data Export and Conversion**: The system will provide data export functionality in multiple formats (JSON, XML, CSV.) from the RDBMS, requiring reliable conversion and validation mechanisms.

- **Scalability and Performance**: The AMS should be optimized for high performance, particularly in handling concurrent client requests and database interactions, maintaining efficiency during peak usage.

- **Network Dependability**: The effectiveness of automated notifications and report distribution is contingent on consistent network connectivity and the reliability of email servers.

- **Future-Proofing and Extensibility**: The system's architecture must be designed for easy extensibility, allowing for future enhancements, such as different notification methods or database system changes, without necessitating significant architecture modifications.

These constraints guide the AMS development, ensuring it meets the required standards of functionality, security, and flexibility, while accommodating the evolving needs of educational institutions.

#### Dependencies:

- **Java and JDBC**: The system's development and functionality are heavily reliant on Java and JDBC for backend operations, database interactions, and client-server communication.
- **Operating Systems Compatibility**: Cross-platform compatibility is essential, requiring the system to function seamlessly across diverse operating systems like Windows, macOS, and Linux, which should support the Java environment.
- **Email Service Reliability**: The effectiveness of the notification system depends on the integration with a reliable email service, crucial for sending automated attendance notifications and reports.
- **Stable Network Connectivity**: Continuous access to a reliable network is vital for system operations, especially for functionalities that involve client-server communication and external integrations.
- **Data Management Libraries**: The system's ability to manage and export data in various formats (JSON, XML, etc.) hinges on the availability of Java-compatible libraries for data processing, serialization, and deserialization.


## Requirements

### Specific Requirements

The Specific Requirements of the Attendance Management System (AMS) reflect the updated client/server model and expanded functionalities for different user roles. These requirements aim to streamline attendance management for instructors, enhance student communication, and facilitate comprehensive database management:

- **Requirement 1 (Report Generate)**: The system shall allow instructors to view class attendance report, including the participation rate
- **Requirement 2 (Attendance Recording)**: The system shall display individual student names sequentially on the screen for attendance purposes. The system shall enable instructors to quickly mark students as present, absent, or tardy using a simple keypress for each student name presented on the terminal interface.
- **Requirement 3 (Late Arrivals & Attendance Adjustment)**:The system shall enable the professor to retrieve previous attendance records. It shall provide functionality for the professor to modify a student's attendance status at any time after the class has concluded,  reflecting changes for students who arrived late or incorrectly inserted data
- **Requirement 4 (Data Storage)**: The system shall record attendance data in servers with the capability to export this data into other formats such as JSON and XML.
- **Requirement 5 (Automated Notifications)**: The system shall send automated email notifications to students, including weekly attendance reports and real-time updates when their attendance status changes.
- **Requirement 6 ( Notifications preference Update)**: The system shall enable the student to update their Notification preference to enable or disable status.
- **Requirement 7 (Report for Student)**: The system shall enable the student to view their detail/summary class attendance record of each course.

### Non-Functional requirements

- **Requirement 8 (Security)**: The system shall implement security measures to ensure the confidentiality and integrity of attendance data, employing secure authentication protocols.
- **Requirement 9 (System Accessibility)**: The system shall be operable on various operating systems such as Windows, macOS, and Linux without requiring additional modifications or configurations.

### Functional Requirements

#### User Class 1: Professors

- **FR1.1 Class Selection for Attendance Recording**
  - Faculty members can select from a list of classes and sections they are teaching to record attendance.
- **FR1.2 Attendance Recording Interface**
  - Display student names for the selected class/section, allowing faculty to mark attendance status for each student.
- **FR1.3 Record Attendance for Previous Days**
  - Enable faculty to optionally record or modify attendance for previous class sessions.
- **FR1.4 Modify Attendance Records**
  - Faculty can modify attendance records for a student on a specific day in a class
- **FR1.5 Attendance Report Generation**
  - Generate attendance reports for faculty, detailing each student’s attendance in a class. Tardy marks should count as 80% participation for the affected day.

#### User Class 2: Students

- **FR2.1 Login and Authentication**
  - Students can log into the system, which may be integrated with the faculty application or a separate entity.
- **FR2.2 Notification Preferences**
  - Students can set notification preferences per class
- **FR2.3 Attendance Summary and Detailed Reports**
  - Students can access both summary and detailed reports of their attendance across all enrolled classes.

### User Class 3: User Administer

**FR3.1 User admin**

- **Add/Update/Remove Users**: Facilitate adding, updating, and removing users, supporting professor and student roles.

### User Class 4: Class Manager

- **FR4.1 Add Classes and Sections**
  - Enable the addition of classes with details like name; creation of sections per class with information on section numbers, and instructor assignments.
- **FR4.2 Update Classes and Sections**
  - Allow for the modification of class and section details, including names, and instructors, ensuring real-time updates in the system.
- **FR4.3 Remove Classes and Sections (With Caution)**
  - Facilitate the removal of classes and sections, incorporating explicit confirmation steps and warnings about the potential data loss and impact on schedules.

- **FR4.4 Manual Enrollment**
  - Support the individual enrollment of students into specific class sections.
- **FR4.5 Batch Enrollment**
  - Enable batch processing for enrolling students via file uploads (e.g., CSV), with validation and feedback mechanisms for the data processed.

