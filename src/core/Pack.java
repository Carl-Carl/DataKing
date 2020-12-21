/*
 * @Author: your name
 * @Date: 2020-12-16 14:00:09
 * @LastEditTime: 2020-12-21 19:16:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\pack.java
 */
package core;

import java.util.*;
import java.sql.Statement;

public class Pack  {
    
    @java.io.Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * The head of the table.
     */
    private final HashMap<String, Head> head  = new HashMap<String, Head>();

    /**
     * Elements of the table
     */
    private HashMap<Object, Object[]> elements;

    /**
     * The number of columns of the table
     */
    private final int length;

    /**
     * Head
     */
    private class Head {

        private final Class<?> kind;
        private final int id;

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
    public Pack(String[] names, Class<?>[] columns) throws Exception {
        if (names.length != columns.length || names.length == 0)
            throw new Exception("The length of two arrays are incompatible.");
        
        length = columns.length;
        for (int i = 0; i < length; i++) {
            head.put(names[i], new Head(i, columns[i]));
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

    /**
     * The method find the index of specific column name
     * 
     * @param key the name of column
     * @return the index
     */
    public int findIndex(String key) {
        return head.get(key).getId();
    }

    public Object[] getItem(Object keyValue) {
        return elements.get(keyValue);
    }

    public Class<?> getKind(Object columnName) {
        return head.get(columnName).getKind();
    }

    public Collection<Object[]> getAll() {
        return elements.values();
    }
    
    public Object getContent(Object key, String column) {
        Object[] obj = elements.get(key);
        Head hd = head.get(column);

        if (hd == null || obj == null)
            return null;
        
        return obj[hd.id];
    }

    public static void main(String[] args) {
        Class<?>[] a = new Class[3];
        a[0] = Integer.class;
        a[1] = Double.class;
        a[2] = Integer.class;

        Object obj = 15;
        System.out.println(obj.getClass());
        for (var i : a) {
            System.out.println(i.cast(obj));
            System.out.println(i);
        }

        try {
            new Pack(new String[]{"1", "2", "3"}, a);
        } catch (Exception e) {
            System.out.println(e);
        }

        
    }
}

