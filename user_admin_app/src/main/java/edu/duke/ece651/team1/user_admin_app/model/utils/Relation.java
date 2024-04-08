// package edu.duke.ece651.team1.user_admin_app.model.utils;

// import java.util.*;

// public class Relation implements Iterable<Tuple>{


//     private final Tuple columnNames;
//     private final Tuple template;
//     private final Set<Tuple> tuples;

//     public Relation(Iterable<Object> columnNamesIterable, Iterable<Object> templateIterable) {
//         Tuple columnNames = Tuples.asTuple(columnNamesIterable);
//         Tuple template = Tuples.asTuple(templateIterable);

//         if (columnNames.size() != template.size()) {
//             throw new IllegalArgumentException("The size of column names must match the size of the template");
//         }
//         Tuples.checkColumnNames(columnNames);

//         this.columnNames = columnNames;
//         this.template = template;
// //        this.tuples = new TreeSet<>(new TupleComparator());
//         this.tuples = new LinkedHashSet<>();
//     }



//     public void add(Tuple tuple) {
//         if (!tuple.looksLike(this.template)) {
//             throw new IllegalArgumentException("Tuple structure does not match the template");
//         }
//         tuples.add(tuple);
//     }

//     public int size() {
//         return tuples.size();
//     }

//     public static class TupleComparator implements Comparator<Tuple> {
//         @Override
//         public int compare(Tuple t1, Tuple t2) {
//             for (int i = 0; i < t1.size(); i++) {
//                 int result = compareElements(t1.get(i), t2.get(i));
//                 if (result != 0) {
//                     return result;
//                 }
//             }
//             return 0;
//         }

//         public int compareElements(Object o1, Object o2) {
//             // if (o1 == null && o2 == null) return 0;
//             // if (o1 == null) return -1;
//             // if (o2 == null) return 1;
//             return ((Comparable) o1).compareTo(o2);
//         }
//     }

//     public Tuple getColumnNames() {
//         return columnNames;
//     }

//     public Tuple getTemplate() {
//         return template;
//     }

//     @Override
//     public Iterator<Tuple> iterator() {
//         return tuples.iterator();
//     }



//     @Override
//     public String toString() {
//         StringJoiner sj = new StringJoiner(System.lineSeparator());
//         sj.add(columnNames.toString(", "));

//         for (Tuple tuple : tuples) {
//             sj.add(tuple.toString(", "));
//         }

//         return sj.toString();
//     }


// }

