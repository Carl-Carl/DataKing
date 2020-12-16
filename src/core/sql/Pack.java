/*
 * @Author: your name
 * @Date: 2020-12-16 14:00:09
 * @LastEditTime: 2020-12-16 19:43:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\pack.java
 */
package core.sql;

import java.util.*;

public class Pack  {
    
    @java.io.Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * The head of the table.
     */
    final Head[] head;

    /**
     * Elements of the table
     */
    ArrayList<Object[]> elements;

    /**
     * The number of columns of the table
     */
    private final int length;

    public enum Kind {
        INT,
        DOUBLE,
        STRING,
    }

    /**
     * Head
     */
    private class Head {
        
        private String name;
        private Kind kind;

        public Object getName() {
            return this.name;
        }

        public Kind getKind() {
            return this.kind;
        }
        
		public Head(String name, Kind kind) {
			this.name = name;
			this.kind = kind;
		}
    }

    /**
     * The constructor initialize the names and types of columns
     * 
     * @param names name of each column
     * @param columns Kind of each column
     */
    public Pack(String[] names, Kind[] columns) throws Exception {
        head = new Head[columns.length];
        length = columns.length;
        elements = new ArrayList<Object[]>();

        if (names.length != columns.length) {
            throw new Exception("The size of names and columns are different.");
        }

        for (int i = 0; i < columns.length; i++) {
            head[i] = new Head(names[i], columns[i]);
        }
    }

    /**
     * Get the number of columns of the table
     * 
     * @return The number of columns of table
     */
    public int getWidth() {
        return length;
    }

    public static void main(String[] args) {
        Kind[] a = new Kind[3];
        a[0] = Kind.INT;
        a[1] = Kind.DOUBLE;
        a[2] = Kind.STRING;

        for (var i : a) {
            System.out.println(i);
        }

        try {
            new Pack(new String[]{"1", "2", "3"}, a);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

