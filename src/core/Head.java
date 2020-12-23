/*
 * @Author: your name
 * @Date: 2020-12-22 12:11:28
 * @LastEditTime: 2020-12-23 19:06:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\core\Head.java
 */
package core;

import com.google.gson.annotations.Expose;

public class Head {

    @Expose
    private final Class<?> kind;
    @Expose
    private final int id;
    @Expose
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