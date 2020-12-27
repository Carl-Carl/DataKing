/*
 * @Author: your name
 * @Date: 2020-12-16 14:00:09
 * @LastEditTime: 2020-12-27 09:49:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\pack.java
 */
package core;

import java.util.*;
import com.google.gson.annotations.Expose;

public class Pack  {
    
    @java.io.Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * The name of table
     */
    @Expose
    private String table;

    /**
     * The name of data base
     */
    @Expose
    private String rootName;

    /**
     * The head of the table.
     */
    @Expose
    private final HashMap<String, Head> head  = new HashMap<String, Head>();

    /**
     * Elements of the table
     */
    @Expose
    private HashMap<Data, Object[]> elements = new HashMap<Data, Object[]>();

    /**
     * The number of columns of the table
     */
    @Expose
    private final int length;
    

    /**
     * The constructor initialize the names and types of columns
     * 
     * @param names name of each column
     * @param columns Kind of each column
     */
    public Pack(String root, String table, String[] names, Class<?>[] columns) throws Exception {
        if (names.length != columns.length || names.length == 0)
            throw new Exception("The length of two arrays are incompatible.");
        
        this.table = table;
        length = columns.length;
        for (int i = 0; i < length; i++) {
            head.put(names[i], new Head(i, columns[i], names[i]));
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

    public boolean add(Object[] newElement) {
        if (newElement.length != length)
            return false;
        
        elements.put(new Data(newElement), newElement);
        return true;
    }

    public Object[] getItem(Object keyValue) {
        var temp = new Object[length];
        temp[0] = keyValue;
        return elements.get(new Data(temp));
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
        
        return obj[hd.getId()];
    }

    public Head[] getHeads() {
        var temp = head.values().toArray(new Head[0]);
        Arrays.sort(temp, (Object a, Object b)->
            { return ((Head)a).getId() < ((Head)b).getId() ? -1 : 1;});
        return temp;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getRootName() {
        return rootName;
    }

    public HashMap<String, Head> getHead() {
        return head;
    }
}

