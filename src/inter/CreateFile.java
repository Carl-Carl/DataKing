package inter;
import com.google.gson.Gson;
import core.Head;
import core.Pack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CreateFile {

    public CreateFile() {
    }

    public static String createJsonString(Pack pack){

        StringBuilder s = new StringBuilder();
        Gson gson = new Gson();
        String str = gson.toJson(pack);
        System.out.println(str);
        var h = pack.getHeads();
        ArrayList<String> names_ = new ArrayList<String>();
        ArrayList<String> columns_ = new ArrayList<String>();
        for (Head head : h) {
            names_.add(head.getName());
            if (Integer.class.equals(head.getKind())) {
                columns_.add("Int");
            } else if (Double.class.equals(head.getKind())) {
                columns_.add("Dbl");
            } else
                columns_.add("Str");
        }
        s.append(gson.toJson(names_));
        s.append(gson.toJson(columns_));
        var a = pack.getAll();
        for (Object[] objects : a) {
            String item;
            item = gson.toJson(objects);
            s.append(item);
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
            Writer King = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
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
        Class<?>[] columns = {Integer.class, String.class, Integer.class};
        Pack pack = new Pack(table, name, columns);
        pack.add(new Object[]{"a", 10, 9});
        pack.add(new Object[]{"b", 100, 89});

        var a = createJsonString(pack);
        System.out.println(a);
    }

}
