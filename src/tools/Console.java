/*
* @Author: your name
* @Date: 2020-12-25 14:51:55
 * @LastEditTime: 2020-12-25 21:44:10
 * @LastEditors: Please set LastEditors
* @Description: In User Settings Edit
* @FilePath: \DataKing\src\tools\Console.java
*/

package tools;

import java.util.Scanner;
import sql.*;

public class Console {

    public static void main(String[] args) {
        while (true) {
            var sc = new Scanner(System.in);
            System.out.println("Open the database: ");
            String dbName = sc.nextLine().trim();
            Connection con = DriverManager.getConnection("dataking:" + dbName + ".db");

            if (con == null)
                continue;

                System.out.println("Open database \""+ dbName + "\" successfully.");
                System.out.println("Please input SQL orders.");
                System.out.println("Input \"exit\" to close.");
                
            while (true) {
                Statement st = con.getStatement();
                String sql = sc.nextLine().trim();
                if (sql.equalsIgnoreCase("exit")) {
                    sc.close();
                    return;
                }
                
                var res = st.executeQuery(sql);
                if (res != null)
                    res.print();
                else if (st.executeUpdate(sql))
                    System.out.println("Execute successfully.");
                else
                    System.out.println("Invalid input.");
                st.close();
                con.commit();
            }
        }
    }
}