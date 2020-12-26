/*
 * @Author: your name
 * @Date: 2020-12-11 17:09:53
 * @LastEditTime: 2020-12-17 13:55:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\DriverManager.java
 */
package sql;

import java.io.*;
import java.util.HashMap;

import com.google.gson.Gson;

public class DriverManager {

    private static final File log = new File("mark.log");
    private static Gson gson = new Gson();
    private static HashMap<String, String> list;

    
    static {
        try (var r = new BufferedReader(new FileReader(log))) {
            String info = "";

            while (true) {
                String temp = r.readLine();
                if (temp == null)
                    break;
                else
                    info += temp;
            }

            list = gson.fromJson(info, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
