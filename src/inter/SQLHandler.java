/*
 * @Author: your name
 * @Date: 2020-12-14 14:38:41
 * @LastEditTime: 2020-12-21 20:58:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\SQLHandler.java
 */
package inter;

import java.sql.Connection;
import java.sql.DriverManager;
import sql.Parser;
import sql.Request;
import core.*;
import core.sql.*;

/**
 * The universal class to handle queries to the database
 */
public class SQLHandler {

    private SQLHandler() {
    }

    public static void Handle(String sql) {
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
            Statement a = new Statement();
            
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
