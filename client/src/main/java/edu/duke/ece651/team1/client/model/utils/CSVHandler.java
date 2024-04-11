package edu.duke.ece651.team1.client.model.utils;

import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * The CSVHandler class provides utility methods to read CSV (Comma-Separated Values) files.
 * It allows reading column names and data tuples from CSV files, both with and without headers.
 */
public class CSVHandler {
    public static final String COMMA_SPLITTER = ",";
    public static final String COMMA_PLACEHOLDER = "test"; // may use this in the future to handle commas in strings
    private BufferedReader reader;

    // public void setWriter(BufferedWriter writer) {
    //     this.writer = writer;
    // }

    // public BufferedWriter getWriter() {
    //     return writer;
    // }

    // private BufferedWriter writer;


    // public void setRelation(Relation relation) {
    //     this.relation = relation;
    // }

    private Relation relation;


    private CSVHandler() {}
    /**
     * Sets the BufferedReader to read input from the CSV file.
     *
     * @param reader The BufferedReader to read input from the CSV file.
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }
    /**
     * Reads the column names from the CSV file header.
     *
     * @return The Tuple representing the column names.
     * @throws IOException If an I/O error occurs while reading the header.
     */
    public Tuple readColumnNames() throws IOException {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                return parseHeader(line);
            }

        throw new IllegalArgumentException("End of file, no valid header found");
    }


    //    public Relation(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable)
    //    readTuple(Tuple template, Iterable<String> segmentedLine)
    /**
     * Reads a CSV file with headers and initializes a Relation based on the provided column names and template.
     *
     * @param columnNamesIterable An Iterable containing the column names.
     * @param templateIterable    An Iterable containing the template for tuple types.
     * @param reader              The Reader to read input from the CSV file.
     * @return The Relation initialized with the CSV data.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static Relation readFromCSVwithHeader(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable, Reader reader) throws IOException {
        CSVHandler handler = initFromReader(reader);
        handler.readColumnNames();
        handler.relation = new Relation(columnNamesIterable, templateIterable);
        handler.readAll();
        return handler.relation;
    }


     /**
     * Reads a CSV file with headers and initializes a Relation based on the provided template and the column names extracted from the file.
     *
     * @param templateIterable An Iterable containing the template for tuple types.
     * @param reader           The Reader to read input from the CSV file.
     * @return The Relation initialized with the CSV data.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static Relation readFromCSVwithHeader(Iterable<Object> templateIterable, Reader reader) throws IOException {
        CSVHandler handler = initFromReader(reader);
        Tuple columnNames = handler.readColumnNames();
        handler.relation = new Relation(columnNames, templateIterable);
        handler.readAll();
        return handler.relation;
    }
     /**
     * Reads a CSV file without headers and initializes a Relation based on the provided column names, template, and file data.
     *
     * @param columnNamesIterable An Iterable containing the column names.
     * @param templateIterable    An Iterable containing the template for tuple types.
     * @param reader              The Reader to read input from the CSV file.
     * @return The Relation initialized with the CSV data.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static Relation readFromCSVwithoutHeader(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable, Reader reader) throws IOException {
        CSVHandler handler = initFromReader(reader);
        handler.relation = new Relation(columnNamesIterable, templateIterable);
        handler.readAll();
        return handler.relation;
    }
    /**
     * Parses the header of a CSV file to extract column names.
     *
     * @param header The header line of the CSV file.
     * @return The Tuple containing the column names.
     */
    //    readTuple(Tuple template, Iterable<String> segmentedLine)
    public static Tuple parseHeader(String header) {
        String[] columnNames = Arrays.stream(header.split(COMMA_SPLITTER))
                .map(String::trim)
                .toArray(String[]::new);

        List<Object> template = Collections.nCopies(columnNames.length, Tuple.STRING);
        Tuple res = Tuples.readTuple(template, Arrays.asList(columnNames));
        Tuples.checkColumnNames(res);
        return res;
    }

    /**
     * Reads all data tuples from the CSV file and adds them to the Relation.
     *
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private void readAll() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            // System.out.println("curr line: " + line);
            relation.add(Tuples.readTuple(relation.getTemplate(), Arrays.asList(line.split(COMMA_SPLITTER))));
        }
    }
     /**
     * Initializes a CSVHandler instance with the provided Reader.
     *
     * @param reader The Reader to read input from the CSV file.
     * @return The initialized CSVHandler instance.
     */
    public static CSVHandler initFromReader(Reader reader) {
        if (reader == null) throw new NullPointerException("null reader!");
        BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        CSVHandler handler = new CSVHandler();
        handler.setReader(bufferedReader);
        return handler;
    }

    // public static CSVHandler initFromWriter(Writer writer) {
    //     if (writer == null) throw new NullPointerException("null writer!");
    //     BufferedWriter bufferedWriter = writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    //     CSVHandler handler = new CSVHandler();
    //     handler.setWriter(bufferedWriter);
    //     return handler;
    // }

}
