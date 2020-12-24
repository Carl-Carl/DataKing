/*
 * @Author: your name
 * @Date: 2020-12-11 17:10:11
 * @LastEditTime: 2020-12-23 20:50:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\Statement.java
 */
package inter;

import core.Pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import sql.Parser;
import sql.Request;
import core.*;
import core.sql.*;

public class Statement {

    private final boolean active = true;
    private final Connection connection;
    String root;
    ArrayList<Pack> packs;
    public ResultSet resultSet;

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
    public boolean executeQuery(String sql) throws FileNotFoundException {
        if (!active)
            return false;
        Request[] requests = Parser.parse(sql);
        SQLHandler handler = new SQLHandler();
        assert requests != null;
        for (Request request : requests) {
            if(request.getType().equals(Request.Type.SELECT)) handler.Handle(request);
            else System.out.println("Can't resolve \"" + sql +"\" in this query!\n");
        }
        return true;
    }

    /**
     * Execute sql sentences which modify the database
     * @param sql
     * @return If you change the database successfully, the function
     * will return true, otherwise it will return false.
     */
    public boolean executeUpdate(String sql) throws FileNotFoundException {
        if (!active)
            return false;
        Request[] requests = Parser.parse(sql);
        SQLHandler handler = new SQLHandler();
        assert requests != null;
        for (Request request : requests) {
            if(request.getType().equals(Request.Type.DELETE)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.INSERT)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.DROP)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.UPDATE)) handler.Handle(request);
            else if(request.getType().equals(Request.Type.CREATE)) handler.Handle(request);
            else System.out.println("Can't resolve \"" + sql +"\" in this query!\n");
        }
        return true;
    }

    private Pack getPack(String table) throws FileNotFoundException {
        for (Pack pack : packs) {
            if(table.equals(pack.getTable())){
                return pack;
            }
        }
        File file = new File(root);
        String[] file_list = file.list();
        assert file_list != null;
        for (String s : file_list) {
            if(table.equals(s)){
                FileSwitch fileSwitch = new FileSwitch();
                return fileSwitch.ToPack(root, s);
            }
        }
        for (Pack pack : packs) {
            if (table.equals(pack.getTable())){
                return pack;
            }
        }
        return null;
    }

    class SQLHandler {

        private SQLHandler() {
        }
    
        public void Handle(Request request) throws FileNotFoundException {
                Query query = switch (request.getType()) {
                    case SELECT -> select;
                    case CREATE -> create;
                    case UPDATE -> update;
                    case INSERT -> insert;
                    case DELETE -> delete;
                    case DROP   -> drop;
                };
                query.query(request);
        }
    
        private final Query select = new Query() {
            @Override
            public void query(Request request) {
                String[] table = request.getFrom();
                Pack pack = null;
                try {
                    pack = getPack(table[0]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String[] names = request.getSet();
                int size = names.length;
                ArrayList<Class<?>> col = new ArrayList<Class<?>>();
                ArrayList<Integer> chosen = new ArrayList<Integer>();
                assert pack != null;
                var a = pack.getHeads();
                for (int i = 0; i < a.length; i++){
                    if(names[i].equals(a[i].getName())){
                        col.add(a[i].getKind());
                        chosen.add(a[i].getId());
                    }
                }
                try {
                    resultSet = new ResultSet(root, table[0], names, (Class<?>[])col.toArray(new Class<?>[size]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                var items = pack.getAll();
                ArrayList<Object> temp = new ArrayList<Object>();
                for (Object[] item : items) {
                    for (Integer integer : chosen) {
                        temp.add(item[integer]);
                    }
                    Object[] element = (Object[])temp.toArray(new Object[size]);
                    resultSet.add(element);
                    temp.clear();
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
                    }
                }
                String[] temp = request.getSet();
                ArrayList<String> name_ = new ArrayList<String>();
                ArrayList<Class<?>> columns_ = new ArrayList<Class<?>>();
                for (String s : temp){
                    String[] split = s.split("=");
                    if (split.length != 2){
                        System.out.println("Wrong SQL expression!\n");
                        return;
                    }
                    name_.add(split[0]);
                    if (split[1].equals("String")){
                        columns_.add(String.class);
                    }if (split[1].equals("Integer")){
                        columns_.add(Integer.class);
                    }if (split[1].equals("Double")){
                        columns_.add(Double.class);
                    }
                    else System.out.println("Unknown type!\n");
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
                try {
                    Pack pack = getPack(table[0]);
                    var items = pack.getAll();
                    for (Object[] item : items) {
                        item = null;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    
        private final Query insert = new Query() {
            @Override
            public void query(Request request) {

                String[] table = request.getFrom();
                try {
                    Pack pack = getPack(table[0]);
                    assert pack != null;
                    pack.add(request.getValues());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

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

                String[] table = request.getFrom();
                File file = new File(root + File.separator + table[0] + ".db");
                if (file.exists()) {
                    file.delete();
                }
            }
        };
    };
    public boolean isActive() {
        return active;
    }

    public String getRoot() {
        return root;
    }

}
