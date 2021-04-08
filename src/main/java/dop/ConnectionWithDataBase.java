package dop;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;


public class ConnectionWithDataBase {

public ConnectionWithDataBase() throws  DaoException{
}

    public Connection getConnection(String nameFileProperty) throws DaoException {
        Connection connect = null;
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(nameFileProperty);
            properties.load(inputStream);
            String driver = properties.getProperty("DB_DRIVER");
            String userName = properties.getProperty("DB_USER");
            String password = properties.getProperty("DB_PASSWORD");
            int maxPoolSize = Integer.parseInt(properties.getProperty("MAX_SIZE"));
            String databaseUrl = properties.getProperty("DB_URL");
            Class.forName(driver);
            connect = DriverManager.getConnection(databaseUrl, userName, password);
            System.out.println("Соединение с базой "+connect);
        } catch (Exception e) {
            throw new DaoException("No connect with DB");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new DaoException("Close error InputStream");
            }
        }
        return connect;
    }


}
//"/resources/db.properties"