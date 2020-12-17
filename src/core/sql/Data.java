/*
 * @Author: your name
 * @Date: 2020-12-17 08:48:10
 * @LastEditTime: 2020-12-17 08:58:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\sql\Data.class
 */

package core.sql;

/**
  * Data
  */
public class Data {

    Object[] content;

    Data(Object[] obj) throws Exception {
        if (obj.length == 0)
            throw new Exception("Empty table.");
        content = obj.clone();
    }

    @Override
    public int hashCode() {
        return content[0].hashCode();
    }

    public static void main(String[] args) {
        
    }
}