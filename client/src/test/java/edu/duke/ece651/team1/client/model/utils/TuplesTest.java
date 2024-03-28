package edu.duke.ece651.team1.client.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TuplesTest {
    @Test
    public void testAsTuple_ConvertsIterableToTuple() {
        List<Object> elementsList = Arrays.asList(1, "two", 3.0, true);
        Tuple tuple = Tuples.asTuple(elementsList);
        assertNotNull(tuple);
        assertEquals(4, tuple.size());
        assertEquals(1, tuple.get(0));
        assertEquals("two", tuple.get(1));
        assertEquals(3.0, tuple.get(2));
        assertEquals(true, tuple.get(3));
    }
    @Test
    public void testReadTuple_ConvertsStringsToCorrectTypes() {
    
        Iterable<Object> template = Arrays.asList(Tuple.INTEGER,Tuple.STRING, Tuple.DOUBLE, Tuple.BOOLEAN,Tuple.CHARACTER,Tuple.BYTE,Tuple.SHORT,Tuple.FLOAT);
        Iterable<String> segmentedLine = Arrays.asList("42", "Hello", "3.14", "true","c","127","1","1.23");
        
    
        Tuple tuple = Tuples.readTuple(template, segmentedLine);
        
    
        assertNotNull(tuple);
        assertEquals(8, tuple.size());
        assertTrue(tuple.get(0) instanceof Integer);
        assertTrue(tuple.get(1) instanceof String);
        assertTrue(tuple.get(2) instanceof Double);
        assertTrue(tuple.get(3) instanceof Boolean);
        assertTrue(tuple.get(4) instanceof Character);
        assertTrue(tuple.get(5) instanceof Byte);
        assertTrue(tuple.get(6) instanceof Short);
        assertTrue(tuple.get(7) instanceof Float);
        assertEquals(42, tuple.get(0));
        assertEquals("Hello", tuple.get(1));
        assertEquals(3.14, tuple.get(2));
        assertEquals(true, tuple.get(3));
    }


    @Test
    public void testCheckColumnNames_DetectsDuplicates() {
        Tuple columnNames = Tuple.of("id", "name", "id");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Tuples.checkColumnNames(columnNames));
        assertTrue(exception.getMessage().contains("Duplicate column name"));
    }
    @Test
    public void testConvertStringToType_UnsupportedType_ThrowsException() {
        String stringValue = "unsupported";
        Object unsupportedTypeSample = new Object();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Tuples.convertStringToType(stringValue, unsupportedTypeSample));
        assertTrue(exception.getMessage().contains("Unsupported type"));
    }
    @Test
    void testConvertStringToType_StringTooLongForCharacter_ThrowsException() {
        String tooLong = "ab"; // String with length > 1
        assertThrows(IllegalArgumentException.class, () -> {
            Tuples.convertStringToType(tooLong, Tuple.CHARACTER);
        });
    }
    @Test
    void testCheckColumnNames_NonString_ThrowsException() {
        Tuple columnNamesWithNonString = Tuple.of("id", "name", 123); 
        assertThrows(IllegalArgumentException.class, () -> {
            Tuples.checkColumnNames(columnNamesWithNonString);
        });
    }

    @Test
    void testCheckColumnNames_EmptyString_ThrowsException() {
        Tuple columnNamesWithEmptyString = Tuple.of("id", "", "email"); 
        assertThrows(IllegalArgumentException.class, () -> {
            Tuples.checkColumnNames(columnNamesWithEmptyString);
        });
    }
    @Test
    void testReadTuple_TooManyDataElements_ThrowsException() {
        Iterable<Object> template = Arrays.asList(Tuple.INTEGER, Tuple.STRING);
        Iterable<String> segmentedLine = Arrays.asList("42", "Test", "ExtraData"); 

        assertThrows(IllegalArgumentException.class, () -> {
            Tuples.readTuple(template, segmentedLine);
        });
}








}
