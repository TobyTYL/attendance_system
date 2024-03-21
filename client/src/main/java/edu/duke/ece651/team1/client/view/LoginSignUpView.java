package edu.duke.ece651.team1.client.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.m;

import java.io.InputStreamReader;
import java.io.PrintStream;

public class LoginSignUpView {
    BufferedReader inputReader;
    final PrintStream out;

    public LoginSignUpView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showLoginOrRegistrationMenu() {
        out.println("Welcome to the Attendance Management System!");
        out.println("1. Login");
        out.println("2. Sign Up and Login");
    }

    public String readLoginOrSignUp() throws IOException {
        while (true) {
            try {
                int optionNum = ViewUtils.getUserOption(inputReader, out, 2);
                if (optionNum == 1) {
                    return "login";
                } else {
                    return "signUp";
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option. Please select 1 for Login or 2 for SignUp.");
            }
        }

    }

    public Map<String, String> promptForLogin() throws IOException {
        Map<String, String> map = new HashMap<>();
        out.println("Login");
        out.println("=====================================");
        String username = readInfo("Username:");
        String password = readInfo("Password:");
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    public Map<String, String> promptsignUp() throws IOException {
        Map<String, String> map = new HashMap<>();
        out.println("Sign Up");
        out.println("=====================================");
        String username = readInfo("Choose a username:");
        String password = readInfo("Choose a password:");
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    public String readInfo(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("End of input reached");
        }
        return s;
    }

    public void showLoginSuccessMessage() {
        out.println("Login successful. Welcome!");
    }

    public void showLoginFailureMessage() {
        out.println("Login failed. Please check your username and password and try again.");
    }

    public void showRegistrationSuccessMessage() {
        out.println("Registration successful.");
    }

    public void showRegistrationFailureMessage(String message) {
        out.println("Registration failed because " + message + " Please try again.");
    }

}
