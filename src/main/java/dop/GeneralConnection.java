package dop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GeneralConnection {
    private final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "ab1986ab";
    private final static int MAX_SIZE = 10;

    protected static Connection connect;
    {
        try {
            Class.forName(DB_DRIVER);
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    /*public static Connection getConnection(){
        Connection connect=null;
        try {
            Class.forName(DB_DRIVER);
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }*/
}
