package edu.duke.ece651.team1.client.model.utils;

import java.util.*;
/**
 * Represents a relation with column names and tuples.
 */
public class Relation implements Iterable<Tuple>{


    private final Tuple columnNames;
    private final Tuple template;
    private final Set<Tuple> tuples;
    /**
     * Constructs a Relation object with given column names and template.
     *
     * @param columnNamesIterable An iterable containing the column names.
     * @param templateIterable    An iterable containing the template for tuple types.
     * @throws IllegalArgumentException if the size of column names does not match the size of the template.
     */
    public Relation(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable) {
        Tuple columnNames = Tuples.asTuple(columnNamesIterable);
        Tuple template = Tuples.asTuple(templateIterable);

        if (columnNames.size() != template.size()) {
            throw new IllegalArgumentException("The size of column names must match the size of the template");
        }
        Tuples.checkColumnNames(columnNames);

        this.columnNames = columnNames;
        this.template = template;
//        this.tuples = new TreeSet<>(new TupleComparator());
        this.tuples = new LinkedHashSet<>();
    }


    /**
     * Adds a tuple to the relation.
     *
     * @param tuple The tuple to be added.
     * @throws IllegalArgumentException if the structure of the tuple does not match the template.
     */
    public void add(Tuple tuple) {
        if (!tuple.looksLike(this.template)) {
            throw new IllegalArgumentException("Tuple structure does not match the template");
        }
        tuples.add(tuple);
    }
    /**
     * Gets the number of tuples in the relation.
     *
     * @return The number of tuples in the relation.
     */
    public int size() {
        return tuples.size();
    }

    public static class TupleComparator implements Comparator<Tuple> {
        @Override
        public int compare(Tuple t1, Tuple t2) {
            for (int i = 0; i < t1.size(); i++) {
                int result = compareElements(t1.get(i), t2.get(i));
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }

        public int compareElements(Object o1, Object o2) {
            // if (o1 == null && o2 == null) return 0;
            // if (o1 == null) return -1;
            // if (o2 == null) return 1;
            return ((Comparable) o1).compareTo(o2);
        }
    }
    /**
     * Gets the column names tuple of the relation.
     *
     * @return The column names tuple.
     */
    public Tuple getColumnNames() {
        return columnNames;
    }
     /**
     * Gets the template tuple of the relation.
     *
     * @return The template tuple.
     */
    public Tuple getTemplate() {
        return template;
    }
    /**
     * Returns an iterator over the tuples in the relation.
     *
     * @return An iterator.
     */
    @Override
    public Iterator<Tuple> iterator() {
        return tuples.iterator();
    }

    /**
     * Returns a string representation of the relation.
     *
     * @return A string representation of the relation.
     */
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(columnNames.toString(", "));

        for (Tuple tuple : tuples) {
            sj.add(tuple.toString(", "));
        }

        return sj.toString();
    }
    

}

