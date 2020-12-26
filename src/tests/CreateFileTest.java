package tests;

import core.Pack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import core.CreateFile;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

import com.google.gson.*;

public class CreateFileTest {

    @Test
    public void Create_String_from_Pack1() throws Exception {

        Pack pack1 = new Pack("/", "Class",
                new String[]{"name", "score", "age", "address", "height"},
                new Class<?>[]{String.class, Integer.class, Integer.class, String.class, Double.class});
        pack1.add(new Object[]{"Mike", 98, 17, "Beijing", 168.5});
        pack1.add(new Object[]{"Louis", 100, 16, "Chengdu", 172.9});
        pack1.add(new Object[]{"Winter", 99, 17, "Wuhan", 165.6});
        pack1.add(new Object[]{"Linda", 97, 16, "Shanghai", 153.4});

        var output = CreateFile.createJsonString(pack1);
        String act = "[\"name\",\"score\",\"age\",\"address\",\"height\"]\n" +
                "[\"Str\",\"Int\",\"Int\",\"Str\",\"Dbl\"]\n" +
                "[\"Mike\",98,17,\"Beijing\",168.5]\n" +
                "[\"Winter\",99,17,\"Wuhan\",165.6]\n" +
                "[\"Linda\",97,16,\"Shanghai\",153.4]\n" +
                "[\"Louis\",100,16,\"Chengdu\",172.9]\n";
        assertEquals(output, act);
        System.out.println("Expected String in file:\n" + act);
        System.out.println("Actual String in file:\n" + output);

    }

    @Test
    public void Create_String_from_Pack2() throws Exception {

        Pack pack2 = new Pack("/", "Class",
                new String[]{"City", "temperature", "square", "population"},
                new Class<?>[]{String.class, Double.class, Integer.class, Integer.class});
        pack2.add(new Object[]{"Beijing", 32.5, 10102, 876});
        pack2.add(new Object[]{"Chengdu", 29.3, 9509, 1232});
        pack2.add(new Object[]{"Shanghai", 26.9, 6789, 890});
        pack2.add(new Object[]{"Wuhan", 33.0, 12563, 1345});

        var output = CreateFile.createJsonString(pack2);
        String act = "[\"City\",\"temperature\",\"square\",\"population\"]\n" +
                "[\"Str\",\"Dbl\",\"Int\",\"Int\"]\n" +
                "[\"Beijing\",32.5,10102,876]\n" +
                "[\"Shanghai\",26.9,6789,890]\n" +
                "[\"Chengdu\",29.3,9509,1232]\n" +
                "[\"Wuhan\",33.0,12563,1345]\n";
        assertEquals(output, act);
        System.out.println("Expected String in file:\n" + act);
        System.out.println("Actual String in file:\n" + output);
    }
}