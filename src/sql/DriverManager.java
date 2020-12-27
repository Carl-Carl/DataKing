/*
 * @Author: your name
 * @Date: 2020-12-11 17:09:53
 * @LastEditTime: 2020-12-27 09:49:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\DriverManager.java
 */
package sql;

public class DriverManager {

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
