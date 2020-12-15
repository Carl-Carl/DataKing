/*
 * @Author: your name
 * @Date: 2020-12-14 14:38:41
 * @LastEditTime: 2020-12-15 23:44:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\SQLHandler.java
 */
package core.sql;

import sql.Request;

/**
 * The universal class to handle queries to the database
 */
public class SQLHandler {

    private SQLHandler() {
    }

    public static void Handle(Request request) {
        Query query = switch (request.getType()) {
            case SELECT -> select;
            case CREATE -> create;
            case UPDATE -> update;
            case INSERT -> insert;
            case DELETE -> delete;
            case DROP   -> drop;
            default -> null;
        };

        if (request != null)
            query.query(request);
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
