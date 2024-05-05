# Installation and Configuration Guide
* Team 1: Yilin Tao, Huidan Tan, Wenzhuo Wu, Chen Zhang 

## 1. Introduction
This guide provides instructions for installing, configuring, and running the Attendance Management System (AMS). The AMS is designed to help educational institutions manage and track student attendance efficiently through a GUI-based application.

## 2. Prerequisites

* JDK 17
* JavaFX 21 (Note that JavaFX is no longer included with JDK starting from JDK 11)
* PostgreSQL
* Gradle 7.3.3 or Higher

## 3. Project Setup

- **Step 1**:  clone the AMS source from the official repository to your path of choice
    ```
    git clone https://gitlab.oit.duke.edu/kits/project-000-000-01-test/project-team-1.git
    ```
- **Step 2**:  building the projects
  ```
  gradle build
  ```

## 4. Client

### Configuration
* Ensure your system meets the minimum required software versions as detailed below. The client application utilizes Java 8, which is necessary for compatibility with the libraries used.


### Version Specifications
* Java Version: 1.8 (Java 8)
* Gradle Version: 6.3 — Used for building and managing dependencies.

### Dependencies
The client project is dependent on several key Java libraries for its functionality, which are outlined in the build.gradle file. Here’s a breakdown of the crucial libraries:

* Spring Boot: Version 2.7.10 - Utilized for building stand-alone, production-grade Spring-based Applications that you can "just run".
* Spring Boot Starter Thymeleaf: Used for integrating Thymeleaf templates, aiding in rendering HTML on the web.
* JSON Processing Libraries:
  * org.json: Version 20210307 - Used for constructing and manipulating JSON data interchange format.
  * Gson: Version 2.8.8 - Google’s library for converting Java objects into their JSON representation.

* Jsoup: Version 1.13.1 - For parsing HTML documents, extracting data, and manipulation.
* Google ZXing: Version 3.4.1 - A library for barcode image processing.

### Run the client
```
cd client
gradle build
gradle run
```

### Troubleshooting Version Issues
If you encounter any issues related to version incompatibilities, verify the versions of Java and Gradle installed on your system. Ensure all dependencies in the build.gradle file are correct and do not conflict with one another. Use gradle dependencies to diagnose and resolve any conflicts in your project dependencies.

## 5. Server
### Configuration
Ensure your development environment is equipped with the correct versions of Java and Gradle. The server is configured to utilize Java 8 for compatibility with a range of dependencies.

### Version Specifications
* Java Version: 1.8
* Spring Boot Version: 2.7.10
### Dependencies 
* Spring Boot Starters:
  * spring-boot-starter-web for building web applications including RESTful services using Spring MVC. 
  * spring-boot-starter-security for authentication and authorization.
  * spring-boot-starter-mail for email features within the application.
* Security and JSON Web Tokens:
  * io.jsonwebtoken:jjwt:0.9.1 for JWT creation and validation.
* Data Handling:
  * com.fasterxml.jackson.core:jackson-core:2.13.2 for JSON processing.
  * org.json:json:20210307 and com.google.code.gson:gson:2.8.8 for working with JSON data formats.
* Google APIs:
  * com.google.api-client:google-api-client:2.0.0 and related libraries for integrating Google services like Gmail.
* Encryption:
  * com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4 for adding encryption capabilities to property files.

### Running the Application
```
cd server
gradle bootRun
```

### Troubleshooting Version Issues
* Confirm Dependency Versions: Check your build.gradle file to ensure that the versions of all dependencies align with those supported by your system and any connected libraries.
* Resolve Conflicts: Execute the command gradle dependencies in your terminal. This will provide a detailed view of the project’s dependency tree, helping you identify and rectify any conflicting versions.

## 6. User Administration
### Configuration
Java Version: 17 
Gradle Version: Compatible with the version used to run and manage Java 17 projects.
JavaFX Version: 19.0.2.1 

### Dependencies
* Spring Boot Starters:
  * spring-boot-starter-security for integrating security features such as authentication and authorization.
* JavaFX Modules:
  * javafx.controls, javafx.fxml, and javafx.media for graphical components, FXML support, and media handling, respectively.
* JUnit Testing Framework:
  * org.junit.jupiter:junit-jupiter and related testing libraries to support unit and integration testing.
* TestFX:
  * org.testfx:testfx-core and org.testfx:testfx-junit5 for testing JavaFX applications.

### Running the Application
* Ensure that you are in the project directory.
  ```
  cd user_admin_app
  ```
* Run the application using Gradle:
  ```
  gradle run
  ```
  
### Troubleshooting Version Incompatibilities
If you encounter issues related to version mismatches:
* Check Dependency Versions: Ensure all dependencies in the build.gradle file are compatible with Java 17 and meet the specifications required by the application.
* Resolve Dependency Conflicts: Utilize gradle dependencies to examine and adjust the project’s dependency tree and resolve any version conflicts.

## 7. Enrollment Application
### Configuration
Make sure your development environment is configured with the correct versions of Java and Gradle. This application utilizes Java 17 and is built to integrate JavaFX for its GUI components, combined with the capability to support potential RESTful services using Spring Boot.

### Version Specifications
* Java Version: 17 
* JavaFX Version: 19.0.2.1 
* Gradle Version: Compatible with Java 17 projects and up-to-date plugin support.

### Dependencies
* Spring Boot: spring-boot-starter-web:2.5.6 for creating RESTful services.
* JavaFX Libraries: Used for the GUI elements in the application.
  * javafx-controls and javafx-swing for user interface controls and integrating JavaFX with Swing.
* Testing Libraries:
  * JUnit 5 (org.junit.jupiter:junit-jupiter-api and junit-jupiter-engine) for unit tests.
  * Mockito and PowerMock (org.mockito:mockito-core, org.powermock:powermock-api-mockito2, powermock-module-junit4) for mocking and testing Java classes.
  * TestFX (org.testfx:testfx-core, testfx-junit5) for testing JavaFX applications.

### Running the Application
* Ensure that you are in the project directory.
  ```
  cd user_admin_app
  ```
* Run the application using Gradle:
  ```
  gradle run
  ```
### Troubleshooting Version Incompatibilities
If you experience any issues due to version mismatches:

* Confirm Dependency Versions: Ensure all dependencies in your build.gradle file are compatible with Java 17 and meet the specifications for the application.
* Resolve Dependency Conflicts: Utilize gradle dependencies to view and manage the project’s dependency tree, helping to resolve any conflicts.

## 9. Conclusion
* This guide has outlined the steps necessary to set up and configure the Enrollment Application.
* These instructions aimed to ensure a smooth setup process by detailing each step, from installing dependencies to executing the application.
* Should you encounter any issues or require further assistance, do not hesitate to contact to team1 member. 

