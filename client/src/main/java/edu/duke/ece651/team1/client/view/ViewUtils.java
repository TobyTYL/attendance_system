package edu.duke.ece651.team1.client.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.EOFException;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.PrintStream;
public class ViewUtils {
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
}
