/*
 * @Author: your name
 * @Date: 2020-12-15 16:04:06
 * @LastEditTime: 2020-12-15 21:27:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\sql\Request.java
 */

package sql;

public class Request {
    private final Type type;
    private final String[] from;
    private final String[] set;
    private final String[] where;
    private final String[] values;
    private final String[] order;

    public Request(Type type, String[] from, String[] set, String[] where, String[] values, String[] order) {
        this.type  = type;
        this.from = from;
        this.set = set;
        this.values = values;
        this.where = where;
        this.order  = order;
    }

    public enum Type {
        SELECT,
        CREATE,
        UPDATE,
        INSERT,
        DELETE,
        DROP,
    }

    public Type getType() {
        return this.type;
    }

    public String[] getFrom() {
        return this.from;
    }

    public String[] getWhere() {
        return this.where;
    }

    public String[] getOrder() {
        return this.order;
    }

    public String[] getSet() {
        return set;
    }

    @Override
    public String toString() {
        return 
            "type: "    +   type +
            "\nfrom: " +    from +
            "\nwhere: " +   where +
            "\nrules: " +   order + 
            "\nset: " +     set + 
            "\nvalues: " +  values + 
            "\n";
    }

    public String[] getValues() {
        return values;
    }
    
}
