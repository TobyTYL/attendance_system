package edu.duke.ece651.team1.client.model.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A utility class to perform email validation.
 */
public class EmailUtils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
     /**
     * Checks if the given email address is valid.
     *
     * @param emailStr The email address to be checked.
     * @return true if the email address is valid, false otherwise.
     */
    public static boolean checkEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr.trim());
        return matcher.matches();
    }
}
