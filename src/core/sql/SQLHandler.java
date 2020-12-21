/*
 * @Author: your name
 * @Date: 2020-12-14 14:38:41
 * @LastEditTime: 2020-12-18 10:58:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\SQLHandler.java
 */
package core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import sql.Parser;
import sql.Request;
import core.*;

/**
 * The universal class to handle queries to the database
 */
public class SQLHandler {

    private SQLHandler() {
    }

    public static void Handle(String sql, Pack[] packs) {
        Request[] request = Parser.parse(sql);

        for (Request i : request) {
            Query query = switch (i.getType()) {
                case SELECT -> select;
                case CREATE -> create;
                case UPDATE -> update;
                case INSERT -> insert;
                case DELETE -> delete;
                case DROP   -> drop;
                default -> null;
            };

            if (i != null)
                query.query(i);
        }
        
    }

    private static final Query select = new Query() {
        @Override
        public void query(Request request) {

        }
    };

    private static final Query create = new Query() {
        @Override
        public void query(Request request) {

        }
    };

    private static final Query update = new Query() {
        @Override
        public void query(Request request) {

        }
    };

    private static final Query insert = new Query() {
        @Override
        public void query(Request request) {

        }
    };

    private static final Query delete = new Query() {
        @Override
        public void query(Request request) {

        }
    };

    private static final Query drop = new Query() {
        @Override
        public void query(Request request) {

        }
    };

}
