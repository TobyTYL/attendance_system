package edu.duke.ece651.team1.user_admin_app.model.utils;

import java.util.*;

public class Tuples {
    public static Tuple asTuple(Iterable<Object> iterable) {
        List<Object> elementsList = new ArrayList<>();
        for (Object element : iterable) {
            elementsList.add(element);
        }
        return new Tuple(elementsList.toArray(new Object[0]));
    }

    public static Tuple readTuple(Iterable<Object> template, Iterable<String> segmentedLine) {
        List<Object> values = new ArrayList<>();
        Iterator<String> lineIterator = segmentedLine.iterator();
        for (Object typeSample : template) {
            if (!lineIterator.hasNext()) {
                throw new IllegalArgumentException("Insufficient data in line to match the template");
            }
            String stringValue = lineIterator.next().trim();
            values.add(convertStringToType(stringValue, typeSample));
        }
        if (lineIterator.hasNext()) {
            throw new IllegalArgumentException("Line contains more data than the template expects");
        }
        return asTuple(values);
    }

    public static Object convertStringToType(String stringValue, Object typeSample) {
        if (typeSample instanceof Integer) {
            return Integer.parseInt(stringValue);
        } else if (typeSample instanceof Double) {
            return Double.parseDouble(stringValue);
        } else if (typeSample instanceof Float) {
            return Float.parseFloat(stringValue);
        } else if (typeSample instanceof Long) {
            return Long.parseLong(stringValue);
        } else if (typeSample instanceof Boolean) {
            return Boolean.parseBoolean(stringValue);
        } else if (typeSample instanceof Character) {
            if (stringValue.length() != 1) {
                throw new IllegalArgumentException("String cannot be converted to Character: " + stringValue);
            }
            return stringValue.charAt(0);
        } else if (typeSample instanceof Byte) {
            return Byte.parseByte(stringValue);
        } else if (typeSample instanceof Short) {
            return Short.parseShort(stringValue);
        } else if (typeSample instanceof String) {
            return stringValue;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + typeSample.getClass().getName());
        }
    }

    public static void checkColumnNames(Tuple columnNames) {
        Set<String> nameSet = new HashSet<>();
        for (Object colNameObj : columnNames) {
            if (!(colNameObj instanceof String)) {
                throw new IllegalArgumentException("Column names must be strings.");
            }
            String colName = ((String) colNameObj).trim().toLowerCase();
            if (colName.isEmpty()) {
                throw new IllegalArgumentException("Column names cannot be empty.");
            }
            if (!nameSet.add(colName)) {
                throw new IllegalArgumentException("Duplicate column name: " + colName);
            }
        }
    }

}

