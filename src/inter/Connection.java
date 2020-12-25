/*
 * @Author: your name
 * @Date: 2020-12-15 12:26:17
 * @LastEditTime: 2020-12-25 13:52:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\Connection.java
 */
package inter;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Pack;

public class Connection {
    
    private String root;
    private boolean active = true;
    HashSet<String> temps = new HashSet<String>();

    /**
     * Locate a directory as the root of database
     * 
     * @param URL
     * @param user
     * @param password
     * @throws Exception
     */
    Connection(String URL) throws Exception {
        Pattern pt = Pattern.compile("\\s*dataking\\s*:\\s*([\\w]+)\\.db\\s*");
        Matcher mt = pt.matcher(URL);
        if (!mt.matches())
            throw new Exception("Invalid URL.\nIt should be \"dataking:name.db\"\n");

        root = "dataking/" + mt.group(1);
        File rootPath = new File(root);

        if (rootPath.exists()) {
            // Open the existing database
        } else {
            // Create a new one
            rootPath.mkdir();
        }
    }

    /**
     * Get the statement of database.
     * @return The statement.
     */
    public Statement getStatement() {
        List<Pack> packs = new ArrayList<Pack>();
        for (var temp : temps) {
            File tempFile = new File(root + "/" + temp + ".temp");
            if (tempFile.exists()) {
                try {
                    packs.add(FileSwitch.ToPack(root, temp + ".temp"));
                } catch (Exception e) {
                    System.err.println("File not find: " + temp);
                }
            }
        }
        
        Statement statement = new Statement(this, root.substring(9), packs);
        return statement;
    }

    public String getRoot() {
        return root;
    }

    /**
     * Commit all the changes to the database
     */
    public void commit() {
        for (String str : temps) {
            File tempFile = new File(root + "/" + str + ".temp");
            File originFile = new File(root + "/" + str + ".dk");
            if (tempFile.exists() && tempFile.exists()) {
                originFile.delete();
                tempFile.renameTo(originFile);
            }
        }
    }

    void addTemp(String[] tables) {
        temps.addAll(Arrays.asList(tables));
    }

    /**
     * Close the connection and clear temporary files.
     */
    public void close() {
        active = false;
        for (String str : temps) {
            File f = new File(root + "/" + str + ".temp");
            if (f.exists())
                f.delete();
        }
        this.temps = null;
    }

    public static void main(String[] args) {
        var con = DriverManager.getConnection("dataking:URL.db");
        var str = con.getRoot();
        con.temps.add("a");
        con.commit();
        con.close();
    }

    public boolean isActive() {
        return active;
    }
}
