/*
 * @Author: your name
 * @Date: 2020-12-11 17:09:53
 * @LastEditTime: 2020-12-16 21:37:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\DriverManager.java
 */
package inter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static Connection getConnection(String URL, String user, String password) {
        Connection con = new Connection();
        

        return con;
    }

    public static void newUser(String name, String password) throws Exception {
        if (list.get(name) != null)
            list.put(name, password);
        else
            throw new Exception("User has existed.");
    }

    public static void main(String[] args) {
        
        System.out.println(list.get("key"));
    }
}
