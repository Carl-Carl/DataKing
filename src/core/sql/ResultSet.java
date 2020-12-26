/*
 * @Author: your name
 * @Date: 2020-12-18 10:46:45
 * @LastEditTime: 2020-12-26 15:28:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\ResultSet.java
 */

package core.sql;

import java.util.*;
import core.*;

public class ResultSet {

    private final HashMap<String, Head> heads;
    // private ArrayList<Item> items;
    // private Iterator<Item> current = items.iterator();
    private ArrayList<Object[]> items = new ArrayList<Object[]>();
    private Iterator<Object[]> pointer;
    private Object[] current;
    private final Pack parent;
    private final int sortKey;
    private final Class<?> kindKey;

    public ResultSet(Pack pack, boolean ascend, String key) {
        parent = pack;
        heads = pack.getHead();
        sortKey = pack.getHead().get(key).getId();
        kindKey = pack.getHead().get(key).getKind();
        items.addAll(pack.getAll());
        var h = pack.getHeads();
        for (Head head : h) {
            int id = head.getId();
            if (head.getKind() == Integer.class)
                for (var item : items)
                    item[id] = Integer.parseInt((String)(item[id]));

            if (head.getKind() == Double.class)
                for (var item : items)
                    item[id] = Double.parseDouble((String)(item[id]));
        }

        items.sort((Object[] a, Object[] b) -> {
            return ((Comparable)a[sortKey]).compareTo((Comparable)b[sortKey]);
        });
        pointer = items.iterator();

    }

    public ResultSet(Pack pack) {
        heads = pack.getHead();
        parent = pack;
        sortKey = -1;
        kindKey = null;
        items.addAll(pack.getAll());
        pointer = items.iterator();
    }

    public boolean next() {
        if (!pointer.hasNext()) {
            return false;
        } else {
            current = pointer.next();
            return true;
        }
    }

    public String getString(String keyword) throws Exception {
        Head head = heads.get(keyword);
        if (head == null || head.getKind() != String.class) 
            throw new Exception("Not a valid keyword.");
        return  String.class.cast(current[head.getId()]);
    }

    public Integer getInt(String keyword) throws Exception {
        Head head = heads.get(keyword);
        if (head == null || head.getKind() != Integer.class) 
            throw new Exception("Not a valid keyword.");
        return  (Integer)current[head.getId()];
    }

    public Double getDouble(String keyword) throws Exception {
        Head head = heads.get(keyword);
        if (head == null || head.getKind() != Double.class) 
            throw new Exception("Not a valid keyword.");
        return  (Double)current[head.getId()];
    }

    public void print(){
        System.out.println("**********"+parent.getTable()+"**********");

        for (Head head : parent.getHeads())
            System.out.print(head.getName()+"\t");
        System.out.println("");

        var items = parent.getAll();
        for (Object[] item : items) {
            for (int i = 0; i <item.length; i++) {
                System.out.print(item[i] + "\t|");
            }
            System.out.println("");
        }
    }
}
