/*
 * @Author: your name
 * @Date: 2020-12-22 12:11:28
 * @LastEditTime: 2020-12-22 12:28:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\Head.java
 */
package core;

class Head {

    private final Class<?> kind;
    private final int id;
    private final String name;

    public Class<?> getKind() {
        return kind;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public Head(int id, Class<?> kind, String name) {
        this.id = id;
        this.kind = kind;
        this.name = name;
    }
}