package edu.duke.ece651.team1.client.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

public class RelationTest {
    @Test
    public void testRelationCreation_MatchingSizes_Success() {
        Iterable<Object> columnNamesIterable = Arrays.asList("ID", "Name", "Age");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.INTEGER, Tuple.STRING, Tuple.INTEGER);
        
        Relation relation = new Relation(columnNamesIterable, templateIterable);
        
        assertNotNull(relation);
        assertEquals(3, relation.getColumnNames().size());
        assertEquals(3, relation.getTemplate().size());
}

    @Test
    public void testRelationCreation_MismatchingSizes_ThrowsException() {
        Iterable<Object> columnNamesIterable = Arrays.asList("ID", "Name");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.INTEGER, Tuple.STRING, Tuple.INTEGER);

        assertThrows(IllegalArgumentException.class, () -> {
            new Relation(columnNamesIterable, templateIterable);
        });
}
    @Test
    public void testAdd_ValidTuple_Success() {
        Iterable<Object> columnNamesIterable = Arrays.asList("ID", "Name");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.INTEGER, Tuple.STRING);
        Relation relation = new Relation(columnNamesIterable, templateIterable);
        Tuple validTuple = Tuple.of(1, "Alice");
        relation.add(validTuple);
        assertEquals(1, relation.size());
    }
    @Test
    public void testAdd_InvalidTuple_ThrowsException() {
        Iterable<Object> columnNamesIterable = Arrays.asList("ID", "Name");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.INTEGER, Tuple.STRING);
        Relation relation = new Relation(columnNamesIterable, templateIterable);
        Tuple invalidTuple = Tuple.of("NotAnInteger", "Alice");
        assertThrows(IllegalArgumentException.class, () -> {
            relation.add(invalidTuple);
        });
    }
    @Test
    public void testIterator_SuccessfulIteration() {
        Iterable<Object> columnNamesIterable = Arrays.asList("ID", "Name");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.INTEGER, Tuple.STRING);
        Relation relation = new Relation(columnNamesIterable, templateIterable);
        Tuple tuple1 = Tuple.of(1, "Alice");
        Tuple tuple2 = Tuple.of(2, "Bob");
        relation.add(tuple1);
        relation.add(tuple2);
        int count = 0;
        for (Tuple tuple : relation) {
            assertNotNull(tuple);
            count++;
        }
        assertEquals(2, count);
    }
    @Test
    public void testToString_ContainsCorrectInformation() {
        Iterable<Object> columnNamesIterable = Arrays.asList("ID", "Name");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.INTEGER, Tuple.STRING);
        Relation relation = new Relation(columnNamesIterable, templateIterable);
        Tuple tuple1 = Tuple.of(1, "Alice");
        relation.add(tuple1);
        String expectedOutput = "ID, Name\n1, Alice";
        assertEquals(expectedOutput, relation.toString().trim());
    }
    @Test
    void testTupleComparator_EqualTuples() {
        Relation.TupleComparator comparator = new Relation.TupleComparator();
        Tuple t1 = new Tuple(1, "test", true);
        Tuple t2 = new Tuple(1, "test", true);

        assertEquals(0, comparator.compare(t1, t2));
    }

    @Test
    void testTupleComparator_FirstLessThanSecond() {
        Relation.TupleComparator comparator = new Relation.TupleComparator();
        Tuple t1 = new Tuple(1);
        Tuple t2 = new Tuple(2);

        assertTrue(comparator.compare(t1, t2) < 0);
    }
    @Test
    void testTupleComparator_FirstGreaterThanSecond() {
        Relation.TupleComparator comparator = new Relation.TupleComparator();
        Tuple t1 = new Tuple(2);
        Tuple t2 = new Tuple(1);

        assertTrue(comparator.compare(t1, t2) > 0);
    }
   
}
