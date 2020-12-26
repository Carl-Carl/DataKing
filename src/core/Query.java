/*
 * @Author: your name
 * @Date: 2020-12-14 19:36:34
 * @LastEditTime: 2020-12-26 19:13:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\Query.java
 */
package core;

import java.io.FileNotFoundException;

import parser.Request;

/**
 * An universal interface for query
 */
@FunctionalInterface
public interface Query {
    public void query(Request request) throws FileNotFoundException;
}
