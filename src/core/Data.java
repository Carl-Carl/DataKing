/*
 * @Author: your name
 * @Date: 2020-12-21 20:13:16
 * @LastEditTime: 2020-12-21 20:32:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\Data.java
 */

package core;

public class Data {
    
    Object[] obj;

    Data(Object[] obj) {
        this.obj = obj;
    }

    @Override
    public int hashCode() {
        return obj[0].hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.obj[0].hashCode() == obj.hashCode();
    }
}
