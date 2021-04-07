package dop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class GeneralConnection2 {
    /*private final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "ab1986ab";
    private final static int MAX_SIZE = 10;*/

    /*protected static Connection connect;
    {
        try {
            Class.forName(DB_DRIVER);
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }*/
    public static Connection getConnection(){
        Connection connect=null;
        /*try {
            Class.forName(DB_DRIVER);
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
      //  C:\Users\Admin\IdeaProjects\Student\src\resources\db.properties
        //resources/db.properties
        Properties properties=new Properties();
        InputStream inputStream=null;
        File file = new File("db.properties");
        System.out.println(file.getAbsolutePath());
        try{
            inputStream=GeneralConnection2.class.getResourceAsStream("/resources/db.properties");
            properties.load(inputStream);
            Class.forName(properties.getProperty("DB_DRIVER"));
           String  userName=properties.getProperty("DB_USER");
           String  password=properties.getProperty("DB_PASSWORD");
           int maxPoolSize=Integer.parseInt(properties.getProperty("MAX_SIZE"));
            String databaseUrl=properties.getProperty("DB_URL");
            System.out.println(userName+ password+""+maxPoolSize);
            connect = DriverManager.getConnection(databaseUrl, userName, password);
           // connectionPool=new ConnectionPool(databaseUrl,userName,password,maxPoolSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}
