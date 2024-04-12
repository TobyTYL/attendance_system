package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Predicate;
/**
 * The ViewUtils class provides utility methods for user interface operations.
 */
public class ViewUtils {
    /**
     * Gets the user's option from the input reader.
     *
     * @param inputReader The BufferedReader object for reading user input.
     * @param out         The PrintStream object for displaying messages to the user.
     * @param maxOptio   The maximum valid option number.
     * @return The user's selected option as an integer.
     * @throws IOException   If an I/O error occurs.
     * @throws EOFException If the end of the input stream is reached.
     * @throws IllegalArgumentException If the user's input is invalid or out of range.
     */
    public static int getUserOption(BufferedReader inputReader, PrintStream out, int maxOptio) throws  IOException{
        out.println("Enter your choice: ");
        String s = inputReader.readLine();
        if (s == null|| s.trim().isEmpty()) {
          throw new EOFException("End of input reached");
        }
        int ans = Integer.parseInt(s);
        if (ans <= 0 || ans > maxOptio) {
          throw new IllegalArgumentException("That option is invalid: it does not have the correct format.");
        }
        return ans;
    }
}


