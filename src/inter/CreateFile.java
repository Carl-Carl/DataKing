package inter;
import com.google.gson.Gson;
import core.Pack;
import core.sql.*;

import java.io.*;
import java.util.Collection;

public class CreateFile {

    public static String createJsonString(Pack pack){

        StringBuilder s = new StringBuilder();
        Gson gson = new Gson();

        var a = pack.getAll();
        for (Object[] objects : a) {
            s.append(gson.toJson(objects));
        }
        return s.toString();
    }

    public static boolean createJsonFile(String jsonString, String filePath, String filename){
        boolean file_created = true;

        String fullPath = filePath + File.separator + filename + ".json";

        try {
            File file = new File(fullPath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(file.exists()){
                file.delete();
            }

            file.createNewFile();
            Writer King = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            King.write(jsonString);
            King.flush();
            King.close();

        } catch (IOException e) {
            file_created = false;
            e.printStackTrace();
        }

        return file_created;
    }

    public static void main(String[] args) throws Exception {

        String table = "Student";
        String[] name = {"name", "score", "age"};
        Class<?>[] columns = {String.class, Integer.class, Integer.class};
        Pack pack = new Pack(table, name, columns);
        pack.add(new Object[]{"a", 10, 9});
        pack.add(new Object[]{"b", 100, 89});
        System.out.println(pack.toString());
        String s = createJsonString(pack);
        System.out.println(s);

    }

}
