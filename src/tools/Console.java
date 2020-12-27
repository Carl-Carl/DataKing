/*
* @Author: your name
* @Date: 2020-12-25 14:51:55
 * @LastEditTime: 2020-12-27 10:18:44
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
            System.out.print("Open the database:\nData King >> ");
            String dbName = sc.nextLine().trim();
            Connection con = DriverManager.getConnection("dataking:" + dbName + ".db");

            if (con == null)
                continue;

                System.out.println("Open database \""+ dbName + "\" successfully.");
                System.out.println("Please input SQL orders.");
                System.out.println("Input \"exit\" to close.\n");

            System.out.print("\nData King >> ");
            while (sc.hasNext()) {
                Statement st = con.getStatement();
                String sql = sc.nextLine().trim();
                if (sql.equalsIgnoreCase("exit")) {
                    sc.close();
                    return;
                }
                
                var res1 = st.executeQuery(sql);
                if (res1 != null)
                    res1.print();
                else {
                    var res2 = st.executeMultiQuery(sql);
                    if (res2 != null)
                        for (var r : res2)
                            r.print();
                    else if (st.executeUpdate(sql))
                        System.out.println("Execute successfully.");
                    else
                        System.out.println("Invalid input.");
                }
                
                st.close();
                con.commit();
                System.out.print("Data King >> ");
            }
            
        }
    }
}