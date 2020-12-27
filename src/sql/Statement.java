/*
 * @Author: your name
 * @Date: 2020-12-11 17:10:11
 * @LastEditTime: 2020-12-27 10:07:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\Statement.java
 */
package sql;

import core.Head;
import core.Pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import core.*;
import parser.*;

public class Statement implements AutoCloseable {

    private boolean active = true;
    private final Connection connection;
    private String root;
    ArrayList<Pack> packs;
    private ResultSet[] multiResultSet;


    Statement (Connection connection, String root, Collection<Pack> packs) {
        this.connection = connection;
        this.root = root;
        this.packs = new ArrayList<Pack>(packs);
        for (Pack pack : packs) {
            this.packs.add(pack);
        }
    }

    /**
     * Execute sql sentences which query the database
     *
     * @param sql
     * @return The result
     */
    public ResultSet executeQuery(String sql){
        if (!active)
            return null;
        Request[] requests = Parser.parse(sql);
        if(requests == null) return null;
        SQLHandler handler = new SQLHandler();
        for (Request request : requests) {
            if(request.getType().equals(Request.Type.SELECT)) handler.Handle(request);
            // else System.out.println("Can't resolve \"" + sql +"\" in this query!\n");
        }
        return multiResultSet[0];
    }

    /**
     * Execute sql sentences which query several tables the database
     *
     * @param sql
     * @return The result
     */
    public ResultSet[] executeMultiQuery(String sql){
        if (!active)
            return null;
        Request[] requests = Parser.parse(sql);
        if(requests == null || requests[0].getFrom().length < 2)
            return null;
        SQLHandler handler = new SQLHandler();
        for (Request request : requests) {
            if(request.getType().equals(Request.Type.SELECT)) handler.Handle(request);
            // else System.out.println("Can't resolve \"" + sql +"\" in this query!\n");
        }
        return multiResultSet;
    }

    /**
     * Execute sql sentences which modify the database
     * @param sql
     * @return If you change the database successfully, the function
     * will return true, otherwise it will return false.
     */
    public boolean executeUpdate(String sql) {
        if (!active)
            return false;
        Request[] requests = Parser.parse(sql);
        if(requests == null) return false;
        SQLHandler handler = new SQLHandler();
        for (Request request : requests) {
            if(request.getType().equals(Request.Type.DELETE)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.INSERT)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.DROP)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.UPDATE)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.CREATE)) handler.Handle(request);
            else return false;
        }
        return true;
    }

    private Pack getPack(String table)  {
        for (Pack pack : packs) {
            if(table.equals(pack.getTable())){
                return pack;
            }
        }
        File file = new File(root);
        String[] file_list = file.list();
        assert file_list != null;
        for (String s : file_list) {
            String substring = s.substring(0, s.length() - 3);
            if(table.equals(substring)){
                packs.add(FileSwitch.ToPack(root, substring));
                return packs.get(packs.size() - 1);
            }
        }
        
        return null;
    }

    private Object[] check_where(String where, Head[] heads){

        if(where == null){
            return null;
        }
        String order = where.trim();
        Object[] key_value = new Object[3];
        if(order.contains("=")) {

            String[] orders = order.split("\\s*=\\s*");
            if(orders.length > 2) return null;

            for (Head head : heads) {
                if(head.getName().equals(orders[0])){
                    key_value[0] = head.getId();
                    break;
                }
            }
            if(key_value[0] == null) return null;
            key_value[1] = orders[1];
            key_value[2] = 0;
        }
        else if(order.contains("<")) {

            String[] orders = order.split("\\s*<\\s*");
            if(orders.length > 2) return null;

            for (Head head : heads) {
                if(head.getName().equals(orders[0])){
                    key_value[0] = head.getId();
                    break;
                }
            }
            if(key_value[0] == null) return null;
            key_value[1] = orders[1];
            key_value[2] = -1;
        }
        else if(order.contains(">")) {

            String[] orders = order.split("\\s*>\\s*");
            if(orders.length > 2) return null;

            for (Head head : heads) {
                if(head.getName().equals(orders[0])){
                    key_value[0] = head.getId();
                    break;
                }
            }
            if(key_value[0] == null) return null;
            key_value[1] = orders[1];
            key_value[2] = 1;
        }
        return key_value;
    }

    private boolean satisfy_where(Object[] key_value, Object[] item, Class<?> class_type){
        if(key_value == null) return true;

        if(class_type.equals(Integer.class)){
            int num = (int)key_value[0];
            var x1 = Integer.class.cast(item[num]);
            var x2 = Integer.class.cast(Integer.parseInt((String)key_value[1]));
            if(x1.compareTo(x2) == (int)key_value[2]) return true;
            else return false;
        }
        else if(class_type.equals(String.class)){
            int num = (int)key_value[0];
            var x1 = String.class.cast(item[num]);
            var x2 = String.class.cast(key_value[1]);
            if(x1.compareTo(x2) == (int)key_value[2]) return true;
            else return false;
        }
        else if(class_type.equals(Double.class)){
            int num = (int)key_value[0];
            var x1 = Double.class.cast(item[num]);
            var x2 = Double.class.cast(Double.parseDouble((String)key_value[1]));
            if(x1.compareTo(x2) == (int)key_value[2]) return true;
            else return false;
        }
        return true;
    }

    class SQLHandler {

        private SQLHandler() {
        }

        public void Handle(Request request) {
                Query query = switch (request.getType()) {
                    case SELECT -> select;
                    case CREATE -> create;
                    case UPDATE -> update;
                    case INSERT -> insert;
                    case DELETE -> delete;
                    case DROP   -> drop;
                };
            try {
                query.query(request);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private final Query select = new Query() {
            @Override
            public void query(Request request) {

                int Length = 0;
                String[] table = request.getFrom();
                String[] order_by = request.getOrder();
                boolean sort = order_by != null;
                boolean legal_sort = false;
                boolean ascend = true;
                String key = null;
                if(order_by != null){
                    if(order_by.length != 2) return;
                    if(order_by[0].equals("asc")){
                        ascend = true;
                    }
                    else if(order_by[0].equals("desc")){
                        ascend = false;
                    }
                    else return;
                    key = order_by[1];
                }
                Pack pack = null;
                for (String s : table) {
                    pack = getPack(s);
                    if (pack == null) {
                        System.out.println("No table found!");
                        break;
                    }
                    String[] names = request.getSet();
                    int size = names.length;
                    ArrayList<Class<?>> col = new ArrayList<Class<?>>();
                    ArrayList<Integer> chosen = new ArrayList<Integer>();
                    ArrayList<String> names_ = new ArrayList<String>();
                    var a = pack.getHeads();
                    if(order_by != null){
                        for (Head head : a) {
                            if(key.equals(head.getName())){
                                legal_sort = true;
                                break;
                            }
                        }
                        if(!legal_sort){
                            return;
                        }
                    }
                    try{
                        int i, j;
                        for (i = 0, j = 0; i < a.length && j < size; i++){
                            if(names[0].equals("*")){
                                col.add(a[i].getKind());
                                chosen.add(a[i].getId());
                                names_.add(a[i].getName());
                            }
                            else if(names[j].equals(a[i].getName())){
                                col.add(a[i].getKind());
                                chosen.add(a[i].getId());
                                names_.add(a[i].getName());
                                j++;
                            }
                        }
                        int len = chosen.size();
                        Pack result = new Pack(root, s, (String[])names_.toArray(new String[len]), (Class<?>[])col.toArray(new Class<?>[len]));
                        var items = pack.getAll();
                        ArrayList<Object> temp = new ArrayList<Object>();
                        Object[] key_value = check_where(request.getWhere(), pack.getHeads());
                        var class_type = key_value == null ? null : a[(int)key_value[0]].getKind();
                        for (Object[] item : items) {
                            if(satisfy_where(key_value, item, class_type)){
                                for (Integer integer : chosen) {
                                    temp.add(item[integer]);
                                }
                                Object[] element = (Object[])temp.toArray(new Object[size]);
                                result.add(element);
                                temp.clear();
                            }
                        }
                        if(!sort)
                            multiResultSet[Length++] = new ResultSet(result);
                        else
                            multiResultSet[Length++] = new ResultSet(result, ascend, key);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        private final Query create = new Query() {
            @Override
            public void query(Request request) {

                String[] table = request.getFrom();
                for (Pack pack : packs) {
                    if(table[0].equals(pack.getTable())){
                        System.out.println("Table already created!");
                        return;
                    }
                }

                File file = new File(root + File.separator + table[0] + ".dk");
                if(file.exists()){
                    System.out.println("Table already created! Please choose another name!");
                    return;
                }

                String[] temp = request.getSet();
                ArrayList<String> name_ = new ArrayList<String>();
                ArrayList<Class<?>> columns_ = new ArrayList<Class<?>>();
                for (String s : temp){
                    String[] split = s.split("(\\s*)=(\\s*)");
                    if (split.length != 2){
                        System.out.println("Wrong SQL expression!\n");
                        return;
                    }
                    name_.add(split[0]);
                    if (split[1].equalsIgnoreCase("string")){
                        columns_.add(String.class);
                    } else if (split[1].equalsIgnoreCase("Integer")){
                        columns_.add(Integer.class);
                    } else if (split[1].equalsIgnoreCase("Double")){
                        columns_.add(Double.class);
                    }
                    else
                        System.out.println("Unknown type!\n");
                }

                int size_of_name = name_.size();
                int size_of_columns = columns_.size();
                String[] name = name_.toArray(new String[size_of_name]);
                Class<?>[] columns = columns_.toArray(new Class<?>[size_of_columns]);
                Pack pack = null;
                try {
                    pack = new Pack(root, table[0], name, columns);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                packs.add(pack);
            }
        };

        private final Query update = new Query() {
            @Override
            public void query(Request request) {

                String[] table = request.getFrom();
                for (String value : table) {
                    Pack pack = getPack(value);
                    if (pack == null) {
                        System.out.println("Warning: Table-" + value + "-not found!");
                        break;
                    }
                    Head[] heads = pack.getHeads();
                    String[] set = request.getSet();
                    var items = pack.getAll();
                    Object[] key_value = check_where(request.getWhere(), pack.getHeads());
                    var class_type = key_value == null ? null : heads[(int) key_value[0]].getKind();
                    for (Object[] item : items) {
                        if (satisfy_where(key_value, item, class_type)) {
                            for (String s : set) {
                                String s_ = s.trim();
                                Object[] data_new = check_where(s_, heads);
                                if (data_new != null && (int) data_new[2] == 0) {
                                    var class_t = heads[(int) data_new[0]].getKind();
                                    if (class_t.equals(Integer.class)) {
                                        var x1 = Integer.class.cast(Integer.parseInt((String) data_new[1]));
                                        item[(int) data_new[0]] = x1;
                                    } else if (class_t.equals(String.class)) {
                                        var x1 = String.class.cast(((String) data_new[1]));
                                        item[(int) data_new[0]] = x1;
                                    } else if (class_t.equals(Double.class)) {
                                        var x1 = Double.class.cast(Double.parseDouble((String) data_new[1]));
                                        item[(int) data_new[0]] = x1;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        };

        private final Query insert = new Query() {
            @Override
            public void query(Request request) {

                String[] table = request.getFrom();
                Pack pack = getPack(table[0]);
                if (pack == null) {
                    System.out.println("No table found!");
                    return;
                }
                String[] item = request.getValues();
                Head[] heads = pack.getHeads();
                if (item.length != heads.length) {
                    System.out.println("Wrong insert data!");
                    return;
                }
                Object[] element = new Object[item.length];
                for (int i = 0; i < item.length; i++) {
                    if(heads[i].getKind().equals(String.class)){
                        element[i] = item[i];
                    }
                    else if(heads[i].getKind().equals(Integer.class)){
                        element[i] = Integer.parseInt(item[i]);
                    }
                    else element[i] = Double.parseDouble(item[i]);
                }
                pack.add(element);
            }
        };

        private final Query delete = new Query() {
            @Override
            public void query(Request request) {
                String[] table = request.getFrom();
                for (String s : table) {
                    Pack pack = null;
                    pack = getPack(s);
                    if (pack == null) {
                        System.out.println("Warning: Table-" + s + "-not found!");
                        break;
                    }
                    Collection<Object[]> items = null;
                    items = pack.getAll();
                    var a = pack.getHeads();
                    Object[] key_value = check_where(request.getWhere(), pack.getHeads());
                    var class_type = key_value == null ? null : a[(int)key_value[0]].getKind();
                    items.removeIf(objects -> satisfy_where(key_value, objects, class_type));
                }

            }
        };

        private final Query drop = new Query() {
            @Override
            public void query(Request request) {

                String[] table = request.getFrom();
                for (Pack pack : packs) {
                    if(table[0].equals(pack.getTable())){
                        packs.remove(pack);
                        return;
                    }
                }
                File file = new File(root + File.separator + table[0] + ".dk");
                if (file.exists()) {
                    file.delete();
                    return;
                }
                else{
                    file = new File(root + File.separator+table[0] + ".temp");
                    if(file.exists()){
                        file.delete();
                    }
                }

            }
        };
    };

    public void close(){
        ArrayList<String> temp = new ArrayList<String>();
        for (Pack pack : packs) {
            CreateFile.createJsonFile(pack, root);
            temp.add(pack.getTable());
        }
        int size = temp.size();
        String[] list = (String [])temp.toArray(new String[size]);
        connection.addTemp(list);
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public String getRoot() {
        return root;
    }

}
