/*
 * @Author: your name
 * @Date: 2020-12-11 17:10:47
 * @LastEditTime: 2020-12-15 23:44:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \DataKing\src\sql\Parser.java
 */
package sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

public class Parser {

    /**
     * Parse several sql strings
     * 
     * @param cmd sql strings divided by ";"
     * 
     * @return Number of sql sentences which failed to process
     */
    public static Request[] parse(String str) {
        var res = new ArrayList<Request>();

        String cur_str = new String(str).replace('\n', ' ');
        
        while (!cur_str.isBlank()) {
            boolean error = true;
            for (var st : sentences) {
                var matcher = st.getPattern().matcher(cur_str);
                if (matcher.matches()) {
                    error = false;
                    res.add(st.convert(matcher));

                    for (int i = 0; i < matcher.groupCount(); i++) {
                        System.out.println(matcher.group(i));
                    }
                    cur_str = new String(matcher.group(matcher.groupCount()));
                    break;
                }
            }
            if (error)
                break;
        }

        return res.toArray(new Request[0]);
    }

    /**
     * Sentence
     */
    private static class Sentence {
    
        private final Pattern pattern;
        private final Converter converter;

        public Sentence(Pattern pattern, Converter convert) {
            this.pattern = pattern;
            this.converter = convert;
        }

        public Pattern getPattern() {
            return pattern;
        }

        Request convert(Matcher matcher) {
            return this.converter.convert(matcher);
        }
        
    }

    /**
     * convert
     */
    @FunctionalInterface
    public interface Converter {
        public Request convert(Matcher matcher);
    }

    /**
     * Models for pattern
     */
    private static final String SPLIT = "\\s*,\\s*";
    private static final String FROM = "\\s+from\\s+(.+?)";
    private static final String WHERE = "\\s+where\\s+(.+?)";
    private static final String VALUES = "\\s+values\\s+\\((.+?)\\)";
    private static final String ORDER = 
        "\\s+order\\s+by\\s+\\((.+?)\\)\\s+(asc|desc)";

    /**
     * Basic Patterns
     */
    private static final Pattern CREATE_PATTERN = Pattern.compile(
        "(?i)\\s*create\\s+(\\S+)\\s+(.+?)\\s*;(.*)"
    );

    private static final Pattern SELECT_PATTERN = Pattern.compile(
        "(?i)\\s*select"+FROM+WHERE+ORDER+";(.*)"
    );

    private static final Pattern UPDATE_PATTERN = Pattern.compile(
        "(?i)\\s*update\\s+(\\S+)\\s+set\\s+(.+)"+WHERE+";(.*)"
    );

    private static final Pattern INSERT_PATTERN = Pattern.compile(
        "(?i)\\s*insert into\\s+(\\S+)\\s+\\((.+)\\)"+VALUES+";(.*)"
    );

    private static final Pattern DELETE_PATTERN = Pattern.compile(
        "(?i)\\s*delete"+FROM+WHERE+";(.*)"
    );

    private static final Pattern DROP_PATTERN = Pattern.compile(
        "(?i)\\s*drop\\s+(.+)"+";(.*)"
    );

    private static Sentence[] sentences = {
        new Sentence(CREATE_PATTERN, (Matcher matcher) -> {
            String[] set = matcher.group(2).trim().split(SPLIT);
            return new Request(Request.Type.CREATE, 
                new String[]{matcher.group(1)}, set, null, null, null);
        }
        ),

        new Sentence(SELECT_PATTERN, (Matcher matcher) -> {
            String[] from = matcher.group(1).split(SPLIT);
            String[] where = matcher.group(2).split(SPLIT);
            var order = new ArrayList<String>(Arrays.asList(matcher.group(3).split(SPLIT)));
            order.add(0, matcher.group(4));
            return new Request(Request.Type.SELECT, 
                from, null, where, null, order.toArray(new String[0]));
        }
        ),

        new Sentence(UPDATE_PATTERN, (Matcher matcher) -> {
            String[] set = matcher.group(2).split(SPLIT);
            String[] where = matcher.group(3).split(SPLIT);
            return new Request(Request.Type.UPDATE, 
                new String[]{matcher.group(1)}, set, where, null, null);
        }
        ),

        new Sentence(INSERT_PATTERN, (Matcher matcher) -> {
            String[] set = matcher.group(2).split(SPLIT);
            String[] values = matcher.group(3).split(SPLIT);
            return new Request(Request.Type.INSERT, 
                new String[]{matcher.group(1)}, set, null, values, null);
        }
        ),

        new Sentence(DELETE_PATTERN, (Matcher matcher) -> {
            String[] from = matcher.group(1).split(SPLIT);
            String[] where = matcher.group(2).split(SPLIT);
            return new Request(Request.Type.DELETE, 
                from, null, where, null, null);
        }
        ),

        new Sentence(DROP_PATTERN, (Matcher matcher) -> {
            String[] from = matcher.group(1).split(SPLIT);
            return new Request(Request.Type.DROP, 
                from, null, null, null, null);
        }
        ),
    };

    public static void main(String[] args) {
        for (var i : parse("""
        create file age=int,name=string;  
        select   from  file  where  a=1, b  =2 , c = 3  order by  (123, 2)  asc;
        update ;
        insert ;
        delete ;
        drop a, b , c,d,e;
        """)) {
            System.out.println(i.toString());
        }
    }
}
