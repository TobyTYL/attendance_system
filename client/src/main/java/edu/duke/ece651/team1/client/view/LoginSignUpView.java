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
/**
 * The LoginSignUpView class manages the presentation of login and sign-up functionalities to the user.
 * It provides methods to display menus, prompts, messages, and handle user inputs related to authentication and registration.
 */
public class LoginSignUpView {
    BufferedReader inputReader;
    final PrintStream out;
    /**
     * Constructs a LoginSignUpView object with the specified input reader and output stream.
     * @param inputReader The BufferedReader to read user input from.
     * @param out The PrintStream to print output to.
     */
    public LoginSignUpView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays the login or registration menu to the user.
     */
    public void showLoginOrRegistrationMenu() {
        out.println("Welcome to the Attendance Management System!");
        out.println("1. Login");
        out.println("2. Sign Up and Login");
    }
    /**
     * Reads the user's choice of login or sign-up from the menu.
     * @return The selected option ("login" or "signUp").
     * @throws IOException If an I/O error occurs.
     */
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
    /**
     * Prompts the user to enter login credentials.
     * @return A map containing the entered username and password.
     * @throws IOException If an I/O error occurs.
     */
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
    /**
     * Prompts the user to enter sign-up information.
     * @return A map containing the entered username and password.
     * @throws IOException If an I/O error occurs.
     */
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
    /**
     * Prompts the user to enter information with the given prompt.
     * @param prompt The prompt message.
     * @return The user's input.
     * @throws IOException If an I/O error occurs.
     */
    public String readInfo(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("End of input reached");
        }
        return s;
    }
    /**
     * Displays a success message after successful login.
     */
    public void showLoginSuccessMessage() {
        out.println("Login successful. Welcome!");
    }
    /**
     * Displays a failure message after unsuccessful login.
     */
    public void showLoginFailureMessage() {
        out.println("Login failed. Please check your username and password and try again.");
    }
    /**
     * Displays a success message after successful registration.
     */
    public void showRegistrationSuccessMessage() {
        out.println("Registration successful.");
    }
    /**
     * Displays a failure message after unsuccessful registration.
     * @param message The reason for registration failure.
     */
    public void showRegistrationFailureMessage(String message) {
        out.println("Registration failed because " + message + " Please try again.");
    }

}
