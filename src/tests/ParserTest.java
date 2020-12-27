package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import parser.Parser;
import java.util.Arrays;
import java.util.Collection;
import parser.*;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class ParserTest {

    private Request expected;

    private String input;

    private Parser parser = null;

    @Parameterized.Parameters
    public static Collection prepareData()
    {
        Object[][] objects = {
                {"insert into students values (黄蓉,桃花岛,2016-3-2);",
                        new Request(Request.Type.INSERT, new String[]{"students"}, null,
                                null, new String[]{"黄蓉","桃花岛","2016-3-2"}, null)},

                {"update Class4 set name=Mike, y=2,z=1 where k = 2 ; ",
                        new Request(Request.Type.UPDATE, new String[]{"Class4"},
                                new String[]{"name=Mike", "y=2", "z=1"}, "k = 2", null, null)},

                {"delete from Websites where name=Facebook  ;",
                        new Request(Request.Type.DELETE, new String[]{"Websites"},
                                null, "name=Facebook", null, null)},

                {"select  a,    b from  file  where  a=1, b  =2 , c = 3  order by  (123)  asc;",
                        new Request(Request.Type.SELECT, new String[]{"file"},
                                new String[]{"a", "b"}, "a=1, b  =2 , c = 3", null, new String[]{"asc", "123"})},

                {"drop    students  ; ",
                        new Request(Request.Type.DROP, new String[]{"students"},null,null,null, null)},

                {"create    City name = String, num =integer, population= integer;",
                        new Request(Request.Type.CREATE, new String[]{"City"},
                                new String[]{"name = String", "num =integer", "population= integer"}, null, null, null)}


        };
        return Arrays.asList(objects);
    }

    public ParserTest(String input,Request expected){
        this.expected = expected;
        this.input = input;
    }

    @Test
    public void parseSet() {
        parser.Request[] temp = Parser.parse(this.input);
        assertNotNull(temp);
        assertArrayEquals(this.expected.getSet(),
                temp[0].getSet());
        System.out.println("Parse Set correct!");
    }
    @Test
    public void parseOrder() {
        parser.Request[] temp = Parser.parse(this.input);
        assertNotNull(temp);
        assertArrayEquals(this.expected.getOrder(),
                temp[0].getOrder());
        System.out.println("Parse Order correct!");
    }
    @Test
    public void parseValues() {
        parser.Request[] temp = Parser.parse(this.input);
        assertNotNull(temp);
        assertArrayEquals(this.expected.getValues(),
                temp[0].getValues());
        System.out.println("Parse Values correct!");
    }

    @Test
    public void parseWhere() {
        parser.Request[] temp = Parser.parse(this.input);
        assertNotNull(temp);
        assertEquals(this.expected.getWhere(),
                temp[0].getWhere());
        System.out.println("Parse Where correct!");
    }
    @Test
    public void parseFrom() {
        parser.Request[] temp = Parser.parse(this.input);
        assertNotNull(temp);
        assertArrayEquals(this.expected.getFrom(),
                temp[0].getFrom());
        System.out.println("\n"+this.input);
        System.out.println("Parse From correct!");
    }
    @Test
    public void parseType() {
        parser.Request[] temp = Parser.parse(this.input);
        assertNotNull(temp);
        assertEquals(this.expected.getType(),
                temp[0].getType());
        System.out.println("Parse Type correct!");
    }
}