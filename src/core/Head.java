/*
 * @Author: your name
 * @Date: 2020-12-22 12:11:28
 * @LastEditTime: 2020-12-22 12:11:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\Head.java
 */
package core;

class Head {

    private final Class<?> kind;
    private final int id;

    public Class<?> getKind() {
        return this.kind;
    }

    public int getId() {
        return this.id;
    }
    
    public Head(int id, Class<?> kind) {
        this.id = id;
        this.kind = kind;
    }
}