/*
 * @Author: your name
 * @Date: 2020-12-14 19:36:34
 * @LastEditTime: 2020-12-15 16:16:37
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\Query.java
 */
package core.sql;

import sql.Request;

/**
 * An universal interface for query
 */
@FunctionalInterface
public interface Query {
    public void query(Request request);
}
