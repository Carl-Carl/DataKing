/*
 * @Author: your name
 * @Date: 2020-12-18 10:46:45
 * @LastEditTime: 2020-12-25 17:07:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\ResultSet.java
 */

package core.sql;

import java.util.*;
import core.*;

public class ResultSet {

    private final HashMap<String, Head> heads;
    private final Pack parent;
    private Iterator<Object[]> pointer;
    private Object[] current;

    public ResultSet(Pack pack) {
        heads = pack.getHead();
        pointer = pack.getAll().iterator();
        parent = pack;
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
        return  Integer.class.cast(String.class.cast(current[head.getId()]));
    }

    public Double getDouble(String keyword) throws Exception {
        Head head = heads.get(keyword);
        if (head == null || head.getKind() != Double.class) 
            throw new Exception("Not a valid keyword.");
        return  Double.class.cast(String.class.cast(current[head.getId()]));
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
