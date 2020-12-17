/*
 * @Author: your name
 * @Date: 2020-12-15 12:26:17
 * @LastEditTime: 2020-12-17 14:03:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\Connection.java
 */
package inter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {
    
    private String root;

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

    public String getRoot() {
        return root;
    }

    public static void main(String[] args) {
        var con = DriverManager.getConnection("dataking:URL.db");
        var str = con.getRoot();
        
    }
}
