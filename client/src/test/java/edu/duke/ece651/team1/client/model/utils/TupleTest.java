package edu.duke.ece651.team1.client.model.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Iterator;

class TupleTest {
    @Test
public void testTupleCreation_ValidElements_Success() {
    assertDoesNotThrow(() -> {
        Tuple tuple = Tuple.of(1, "String", 1.0, true);
        assertNotNull(tuple);
        assertEquals(4, tuple.size());
    });
}
@Test
public void testTupleCreation_InvalidElements_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
        Tuple tuple = Tuple.of(1, new Object());
    });
}
@Test
public void testGetElement_ValidIndex_ReturnsCorrectElement() {
    Tuple tuple = Tuple.of(1, "Test", true);
    assertEquals("Test", tuple.get(1));
}

@Test
public void testGetElement_InvalidIndex_ThrowsException() {
    Tuple tuple = Tuple.of(1, "Test", true);
    assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(3));
}
@Test
public void testEquals_SameElements_ReturnsTrue() {
    Tuple tuple1 = Tuple.of(1, "Test", true);
    Tuple tuple2 = Tuple.of(1, "Test", true);
    assertEquals(tuple1, tuple2);
  
}

@Test
public void testEquals_DifferentElements_ReturnsFalse() {
    Tuple tuple1 = Tuple.of(1, "Test1", true);
    Tuple tuple2 = Tuple.of(1, "Test2", false);
    assertNotEquals(tuple1, tuple2);
}
@Test
public void testLooksLike_MatchingStructure_ReturnsTrue() {
    Tuple tuple1 = Tuple.of(10, "Hello", 2.5);
    Tuple tuple2 = Tuple.of(20, "World", 5.0);
    assertTrue(tuple1.looksLike(tuple2));
}

@Test
public void testLooksLike_NonMatchingStructure_ReturnsFalse() {
    Tuple tuple1 = Tuple.of(10, "Hello");
    Tuple tuple2 = Tuple.of(20, 5.0, "World");
    assertFalse(tuple1.looksLike(tuple2));
}
@Test
public void testToString_WithDelimiter_ReturnsExpectedString() {
    Tuple tuple = Tuple.of(1, "Test", true);
    assertEquals("1,Test,true", tuple.toString(","));
}

@Test
public void testToString_WithDelimiterPrefixPostfix_ReturnsExpectedString() {
    Tuple tuple = Tuple.of(1, "Test", true);
    assertEquals("[1-Test-true]", tuple.toString("-", "[", "]"));
}

@Test
public void testToString_WithDelimiterAndPrefix_ReturnsExpectedString() {
    Tuple tuple = Tuple.of(1, "Test", true);
    String result = tuple.toString("-", "Prefix: ");
    assertEquals("Prefix: 1-Test-true", result);
}
@Test
public void testToString_Default_ReturnsFormattedString() {
 
    Tuple tuple = Tuple.of(1, "Test", true, 2.5);
    String expected = "Tuple[1, Test, true, 2.5]";
    String actual = tuple.toString();
    assertEquals(expected, actual);
}

@Test
public void testLooksLike_DifferentLengths_ReturnsFalse() {
    Tuple tuple1 = Tuple.of(1, "Test");
    Tuple tuple2 = Tuple.of(1, "Test", true);
    assertFalse(tuple1.looksLike(tuple2));
}

@Test
public void testLooksLike_DifferentTypes_ReturnsFalse() {
    Tuple tuple1 = Tuple.of(1, "Test");
    Tuple tuple2 = Tuple.of(1, true);
    assertFalse(tuple1.looksLike(tuple2));
}

@Test
public void testEquals_NullComparison_ReturnsFalse() {
    Tuple tuple = Tuple.of(1, "Test");
    assertFalse(tuple.equals(null));
}

@Test
public void testEquals_DifferentClassComparison_ReturnsFalse() {
    Tuple tuple = Tuple.of(1, "Test");
    assertFalse(tuple.equals(new Object()));
}

@Test
public void testOfIterable_CreatesTupleFromIterable_Success() {
    List<Object> iterable = Arrays.asList(1, "Test", true);
    Tuple result = Tuple.of(iterable);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(1, result.get(0));
    assertEquals("Test", result.get(1));
    assertEquals(true, result.get(2));
}

@Test
public void testEquals_NullOrDifferentClass_False() {
    Tuple tuple = Tuple.of(1, "Test");
    assertFalse(tuple.equals(null)); // Test with null
    assertFalse(tuple.equals("a string")); // Test with a different type
    assertTrue(tuple.equals(tuple));

}
@Test
public void testIterator_NoSuchElement_ThrowsException() {
    Tuple tuple = Tuple.of(1, "Test");
    Iterator<Object> iterator = tuple.iterator();
    iterator.next(); // First element
    iterator.next();
    assertThrows(NoSuchElementException.class, iterator::next); // Should throw as there are no more elements
}


}