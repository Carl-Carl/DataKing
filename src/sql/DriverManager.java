/*
 * @Author: your name
 * @Date: 2020-12-11 17:09:53
 * @LastEditTime: 2020-12-26 20:35:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\DriverManager.java
 */
package sql;

import java.io.*;
import java.util.HashMap;
import com.google.gson.Gson;

public class DriverManager {

    private static Gson gson = new Gson();
    private static HashMap<String, String> list;

    public static Connection getConnection(String URL) {
        try {
            Connection con = new Connection(URL);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
