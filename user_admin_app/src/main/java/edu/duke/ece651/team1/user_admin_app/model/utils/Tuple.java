package edu.duke.ece651.team1.user_admin_app.model.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.stream.StreamSupport;

// 230202 cz

public final class Tuple implements Iterable<Object> {

    public static final Integer INTEGER = 0;
    public static final Double DOUBLE = 0.0;
    public static final Float FLOAT = 0.0f;
    public static final Long LONG = 0L;
    public static final Boolean BOOLEAN = false;
    public static final Character CHARACTER = '\0';
    public static final Byte BYTE = 0;
    public static final Short SHORT = 0;
    public static final String STRING = "";

    private final Object[] elements;

    public Tuple(Object... elements) {

        for (Object elem : elements) {
            if (!(elem instanceof Integer || elem instanceof Long || elem instanceof Double ||
                    elem instanceof Float || elem instanceof Boolean || elem instanceof Character ||
                    elem instanceof Byte || elem instanceof Short || elem instanceof String)) {
                throw new IllegalArgumentException("Only boxed primitives and Strings are allowed. What you have: " + elements.getClass().getName());
            }
        }
        this.elements = elements;
    }

    public static Tuple of(Object... elements) {
        return new Tuple(elements);
    }

    @Deprecated
    public static Tuple of(Iterable<Object> iterable) {
        return new Tuple(StreamSupport.stream(iterable.spliterator(), false).toArray());
    }


    public int size() {
        return elements.length;
    }

    public Object get(int index) {
        if (index >= 0 && index < elements.length) {
            return elements[index];
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.length);
        }
    }


    public boolean looksLike(Tuple other) {
        if (this.elements.length != other.elements.length) {
            return false;
        }

        for (int i = 0; i < this.elements.length; i++) {
            // Check if the types are the same
            if (!this.elements[i].getClass().equals(other.elements[i].getClass())) {
                return false;
            }
        }

        return true;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Arrays.equals(elements, tuple.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public String toString() {
        return "Tuple" + Arrays.toString(elements);
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < elements.length;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[currentIndex++];
            }
        };
    }


    public String toString(String delimiter) {
        return toString(delimiter, "", "");
    }


    public String toString(String delimiter, String prefix) {
        return toString(delimiter, prefix, "");
    }

    // useful for handling csv files
    public String toString(String delimiter, String prefix, String postfix) {
        StringJoiner joiner = new StringJoiner(delimiter, prefix, postfix);
        for (Object element : elements) {
            joiner.add(element.toString());
        }
        return joiner.toString();
    }

}

