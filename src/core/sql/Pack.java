/*
 * @Author: your name
 * @Date: 2020-12-16 14:00:09
 * @LastEditTime: 2020-12-17 20:31:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\pack.java
 */
package core.sql;

import java.util.*;
import java.sql.Statement;

public class Pack  {
    
    @java.io.Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * The head of the table.
     */
    final HashMap<String, Head> head;

    /**
     * Elements of the table
     */
    HashSet<Data> elements;

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

        private Class<?> kind;
        private int id;

        public Class<?> getKind() {
            return this.kind;
        }

        public int getId() {
            return this.id;
        }
        
		public Head(int id, Class<?> kind) {
			this.id = id;
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
        
    }

    /**
     * Get the number of columns of the table
     * 
     * @return The number of columns of table
     */
    public int getWidth() {
        return length;
    }

    public int getInt(Data row, String columns) {
        Head temp = head.get(columns);
        return (Integer)(row.content[temp.getId()]);
    }

    public String getString(Data row, String columns) {
        Head temp = head.get(columns);
        return (String)temp.getKind().cast(row.content[temp.getId()]);
    }

    public double getReal(Data row, String columns) {
        Head temp = head.get(columns);
        return (Double)temp.getKind().cast(row.content[temp.getId()]);
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

