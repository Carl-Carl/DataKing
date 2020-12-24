/*
 * @Author: your name
 * @Date: 2020-12-18 10:46:45
 * @LastEditTime: 2020-12-18 10:48:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\ResultSet.java
 */

package core.sql;

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
}
