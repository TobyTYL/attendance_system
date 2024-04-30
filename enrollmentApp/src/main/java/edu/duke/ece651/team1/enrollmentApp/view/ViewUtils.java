package edu.duke.ece651.team1.enrollmentApp.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.EOFException;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Set;
import java.util.function.Predicate;
/**
 * The ViewUtils class provides utility methods for handling user input and interaction in various views.
 * It includes methods for getting user options and inputs with validation.
 */
public class ViewUtils {
    /**
     * Gets the user's option within the specified range.
     * @param inputReader The BufferedReader to read user input from.
     * @param out The PrintStream to print output to.
     * @param maxOption The maximum valid option number.
     * @return The selected option number.
     * @throws IOException If an I/O error occurs.
     */
    public static int getUserOption(BufferedReader inputReader, PrintStream out, int maxOptio) throws  IOException{
        out.println("Enter your choice: ");
        String s = inputReader.readLine();
        if (s == null) {
          throw new EOFException("End of input reached");
        }
        int ans = Integer.parseInt(s);
        if (ans <= 0 || ans > maxOptio) {
          throw new IllegalArgumentException("That option is invalid: it does not have the correct format.");
        }
        return ans;
    }
     /**
     * Gets user input with validation using the specified prompt and redo prompt.
     * @param prompt The initial prompt message.
     * @param redoPrompt The prompt message to display if input is invalid.
     * @param inputReader The BufferedReader to read user input from.
     * @param out The PrintStream to print output to.
     * @param handle The predicate to validate user input.
     * @return The valid user input.
     * @throws IOException If an I/O error occurs.
     */
    public static String getUserInput(
            String prompt,
            String redoPrompt,
            BufferedReader inputReader,
            PrintStream out,
            Predicate<String> handle
    ) throws IOException {
        String input;

        while (true) {
            out.print(prompt);
            input = inputReader.readLine();
            if (input == null) {
                throw new EOFException("End of input reached");
            }
            input = input.trim();
            if (handle.test(input)) {
                return input;
            } else {
                out.println(redoPrompt);
            }
        }
    }

    // public static void main(String[] args) throws IOException {
    //     Set<String> validOptions = Set.of("option1", "option2", "option3");
    //     Predicate<String> isInSet = validOptions::contains;

    //     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //     PrintStream out = System.out;

    //     String userChoice = ViewUtils.getUserInput(
    //             "Enter an option: ",
    //             "Option not valid, please try again: ",
    //             reader,
    //             out,
    //             isInSet // Passing the predicate as a method reference
    //     );

    //     out.println("Your choice was: " + userChoice);
    // }


}


