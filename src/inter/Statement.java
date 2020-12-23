/*
 * @Author: your name
 * @Date: 2020-12-11 17:10:11
 * @LastEditTime: 2020-12-23 19:27:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\Statement.java
 */
package inter;

import core.Pack;
import java.util.ArrayList;

import sql.Parser;
import sql.Request;
import core.*;
import core.sql.*;

public class Statement {

    String root;
    Pack[] packs;

    class SQLHandler {

        private SQLHandler() {
        }
    
        public void Handle(String sql) {
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
    
        private final Query select = new Query() {
            @Override
            public void query(Request request) {
    
            }
        };
    
        private final Query create = new Query() {
            @Override
            public void query(Request request) {
    
                Statement sta = new Statement();
                String[] table = request.getFrom();
                for (Pack pack : sta.packs) {
                    if (table[0].equals(pack.getTable())){
                        /*
                        * create table重复的异常
                        * */
                        System.out.println(table[0]+"already exists!\n");
                        return;
                    }
                }
    
                String[] temp = request.getSet();
                ArrayList<String> name_ = new ArrayList<String>();
                ArrayList<Class<?>> columns_ = new ArrayList<Class<?>>();
                for (String s : temp){
                    String[] split = s.split("=");
                    if (split.length != 2){
                        /*
                        * set 输入异常
                        * */
                        System.out.println("Wrong SQL expression!\n");
                        return;
                    }
                    name_.add(split[0].toString());
                    if (split[1].equals("String")){
                        columns_.add(String.class);
                    }if (split[1].equals("Integer")){
                        columns_.add(Integer.class);
                    }if (split[1].equals("Double")){
                        columns_.add(Double.class);
                    }
                }
    
                int size_of_name = name_.size();
                int size_of_columns = columns_.size();
                String[] name = (String[]) name_.toArray(new String[size_of_name]);
                Class<?>[] columns = (Class<?>[]) columns_.toArray(new Class<?>[size_of_columns]);
                int i;
                for (i = 0; i < 10; i++) {
                    if(sta.packs[i] == null){
                        try {
                            sta.packs[i] = new Pack(root, table[0], name, columns);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    
        private final Query update = new Query() {
            @Override
            public void query(Request request) {
    
            }
        };
    
        private final Query insert = new Query() {
            @Override
            public void query(Request request) {
    
                Statement sta = new Statement();
                String[] table = request.getFrom();
                for (Pack pack : sta.packs) {
                    if (table[0].equals(pack.getTable())){
                        if (request.getSet() == null){
                            /*
                            * 此处没有检查主关键字值是否相同
                            * */
                            pack.add(request.getValues());
                        }
                        else {
                            String[] name = request.getSet();
                            ArrayList<Object> item_new = new ArrayList<Object>();
                            /*
                            * 表头列表进行对应
                            * */
    
                            int size = item_new.size();
                            Object[] item = (Object[])item_new.toArray(new Object[size]);
                            pack.add(item);
                            /*
                            * 此处不检查主关键字是否相同
                            * 加入后提示成功
                            * */
                            System.out.println("Insert item succeed!\n");
                        }
                        return;
                    }
                }
                /*
                * 无用以插入新值的table
                * */
                System.out.println("No table exist!\n");
            }
        };
    
        private final Query delete = new Query() {
            @Override
            public void query(Request request) {
    
            }
        };
    
        private final Query drop = new Query() {
            @Override
            public void query(Request request) {
                Statement sta = new Statement();
                String[] table = request.getFrom();
                for (int i = 0; i < sta.packs.length; i++) {
                    if(table[0].equals(sta.packs[i].getTable())){
                        sta.packs[i] = null;
                        /*
                        * 删除原有的pack
                        * */
                        System.out.println(table[0]+"drop success!\n");
                        break;
                    }
                }
                /*
                * 没有要删除的表
                * */
                System.out.println(table[0]+"doesn't exist!\n");
            }
        };
    
    }
}
