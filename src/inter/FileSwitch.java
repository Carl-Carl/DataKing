/*
 * @Author: your name
 * @Date: 2020-12-24 20:51:36
 * @LastEditTime: 2020-12-25 13:38:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\inter\FileSwitch.java
 */
package inter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.Pack;

import java.io.*;
import java.util.ArrayList;

public class FileSwitch {

    public FileSwitch() {
    }

    public static Pack ToPack(String root, String table) {
        Gson gson = new Gson();
        File file = new File(root + File.separator + table +".temp");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            String str;
            str = reader.readLine();
            String[] names = gson.fromJson(str, new TypeToken<String[]>(){}.getType());
            str = reader.readLine();
            String[] types = gson.fromJson(str, new TypeToken<String[]>(){}.getType());
            ArrayList<Class<?>> columns = new ArrayList<Class<?>>();
            for (String type : types) {
                if(type.equals("str")){
                    columns.add(String.class);
                }
                else if(type.equals("int")){
                    columns.add(String.class);
                }
                else columns.add(Double.class);
            }
            int length = columns.size();
            Class<?>[] columns_ = (Class<?>[]) columns.toArray(new Class<?>[length]);
            Pack pack = new Pack(root, table, names, columns_);
            while(null != (str = reader.readLine())){
                Object[] item = gson.fromJson(str, new TypeToken<Object[]>(){}.getType());
                pack.add(item);
            }
            return pack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        FileSwitch fs = new FileSwitch();
        Pack pack = fs.ToPack("Dataking/Class4", "Student");
        var a = pack.getAll();
        for (Object[] objects : a) {
            System.out.println(gson.toJson(objects));
        }
    }
}
