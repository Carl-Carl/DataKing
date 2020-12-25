/*
 * @Author: your name
 * @Date: 2020-12-18 10:46:45
 * @LastEditTime: 2020-12-25 14:34:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\ResultSet.java
 */

package core.sql;

import core.Head;
import core.Pack;

public class ResultSet extends Pack{

    /**
     * The constructor initialize the names and types of columns
     *
     * @param root
     * @param table
     * @param names   name of each column
     * @param columns Kind of each column
     */
    public ResultSet(String root, String table, String[] names, Class<?>[] columns) throws Exception {
        super(root, table, names, columns);
    }

    public void Print(){
        System.out.println("**********"+this.getTable()+"**********");
        var a = this.getHeads();
        for (Head head : a) {
            System.out.print(head.getName()+"\t");
        }
        System.out.println("");
        var items = this.getAll();
        for (Object[] item : items) {
            for (int i = 0; i <item.length; i++) {
                System.out.print(item[i] + "\t|");
            }
            System.out.println("");
        }
    }
}
