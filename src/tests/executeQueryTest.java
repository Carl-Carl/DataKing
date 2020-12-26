package tests;

import org.junit.Test;
import sql.Connection;
import sql.DriverManager;
import sql.ResultSet;
import sql.Statement;

import static org.junit.Assert.*;

public class executeQueryTest {

    @Test
    public void ResultNotNull() {
        Connection con = DriverManager.getConnection("dataking:" + "tests" + ".db");
        Statement statement = con.getStatement();
        String sql = "select * from Class where age=17 order by (height) asc  ;";
        ResultSet rs = statement.executeQuery(sql);
        assertNotNull(rs);
        System.out.println("Resultset not null\n");
    }

    @Test
    public void ResultGenerate1() {
        Connection con = DriverManager.getConnection("dataking:" + "tests" + ".db");
        assertNotNull(con);
        Statement statement = con.getStatement();
        ResultSet rs = statement.executeQuery("select * from Class where age=17 order by (height) asc  ;");
        System.out.println("\nExecuteQuery...");
        assertNotNull(rs);
        rs.print();
    }

    @Test
    public void ResultGenerate2() {
        Connection con = DriverManager.getConnection("dataking:" + "tests" + ".db");
        assertNotNull(con);
        Statement statement = con.getStatement();
        ResultSet rs = statement.executeQuery("select * from Map where temperature> 28 order by (square) asc ;");
        System.out.println("\nExecuteQuery...");
        assertNotNull(rs);
        rs.print();
    }
}