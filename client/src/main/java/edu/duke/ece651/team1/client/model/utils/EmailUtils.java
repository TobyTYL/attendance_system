package edu.duke.ece651.team1.client.model.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    // Regex for common email validation
    private static final String EMAIL_REGEX = "^(.+)@(.+)$";

    // Precompiled Pattern is efficient for repeated use
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Checks if the provided email address is valid.
     *
     * @param email the email address to check
     * @return true if the email address is valid, false otherwise
     */
    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }

        // Trim the email input to remove any leading or trailing whitespace
        String trimmedEmail = email.trim();

        // Create a matcher for the trimmed email
        Matcher matcher = EMAIL_PATTERN.matcher(trimmedEmail);

        // Return true if the matcher finds a match, which means it is a valid email
        return matcher.matches();
    }
}
