//package edu.duke.ece651.team1.user_admin_app.model.utils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class CSVHandler {
//    public static final String COMMA_SPLITTER = ",";
//    public static final String COMMA_PLACEHOLDER = "test"; // may use this in the future to handle commas in strings
//    private BufferedReader reader;
//
//    // public void setWriter(BufferedWriter writer) {
//    //     this.writer = writer;
//    // }
//
//    // public BufferedWriter getWriter() {
//    //     return writer;
//    // }
//
//    // private BufferedWriter writer;
//
//
//    // public void setRelation(Relation relation) {
//    //     this.relation = relation;
//    // }
//
//    private Relation relation;
//
//
//    private CSVHandler() {}
//
//    public void setReader(BufferedReader reader) {
//        this.reader = reader;
//    }
//
//    public Tuple readColumnNames() throws IOException {
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.trim().isEmpty()) continue;
//                return parseHeader(line);
//            }
//
//        throw new IllegalArgumentException("End of file, no valid header found");
//    }
//
//
//    //    public Relation(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable)
//    //    readTuple(Tuple template, Iterable<String> segmentedLine)
//    public static Relation readFromCSVwithHeader(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable, Reader reader) throws IOException {
//        CSVHandler handler = initFromReader(reader);
//        handler.readColumnNames();
//        handler.relation = new Relation(columnNamesIterable, templateIterable);
//        handler.readAll();
//        return handler.relation;
//    }
//
//
//
//    public static Relation readFromCSVwithHeader(Iterable<Object> templateIterable, Reader reader) throws IOException {
//        CSVHandler handler = initFromReader(reader);
//        Tuple columnNames = handler.readColumnNames();
//        handler.relation = new Relation(columnNames, templateIterable);
//        handler.readAll();
//        return handler.relation;
//    }
//
//    public static Relation readFromCSVwithoutHeader(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable, Reader reader) throws IOException {
//        CSVHandler handler = initFromReader(reader);
//        handler.relation = new Relation(columnNamesIterable, templateIterable);
//        handler.readAll();
//        return handler.relation;
//    }
//
//    //    readTuple(Tuple template, Iterable<String> segmentedLine)
//    public static Tuple parseHeader(String header) {
//        String[] columnNames = Arrays.stream(header.split(COMMA_SPLITTER))
//                .map(String::trim)
//                .toArray(String[]::new);
//
//        List<Object> template = Collections.nCopies(columnNames.length, Tuple.STRING);
//        Tuple res = Tuples.readTuple(template, Arrays.asList(columnNames));
//        Tuples.checkColumnNames(res);
//        return res;
//    }
//
//
//    private void readAll() throws IOException {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            if (line.trim().isEmpty()) continue;
//            // System.out.println("curr line: " + line);
//            relation.add(Tuples.readTuple(relation.getTemplate(), Arrays.asList(line.split(COMMA_SPLITTER))));
//        }
//    }
//
//    public static CSVHandler initFromReader(Reader reader) {
//        if (reader == null) throw new NullPointerException("null reader!");
//        BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
//        CSVHandler handler = new CSVHandler();
//        handler.setReader(bufferedReader);
//        return handler;
//    }
//
//    // public static CSVHandler initFromWriter(Writer writer) {
//    //     if (writer == null) throw new NullPointerException("null writer!");
//    //     BufferedWriter bufferedWriter = writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
//    //     CSVHandler handler = new CSVHandler();
//    //     handler.setWriter(bufferedWriter);
//    //     return handler;
//    // }
//
//}
