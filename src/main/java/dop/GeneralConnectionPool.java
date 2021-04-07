package dop;

import dao.DaoConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GeneralConnectionPool {
    private GeneralConnectionPool generalConnectionPool;
    protected Stack<DatabaseConnection> freePool;
    protected Set<DatabaseConnection> usedPool;
    private int maxQuantity;
    private String nameFileProperty;
public GeneralConnectionPool(){

}
    /*private GeneralConnectionPool(String nameFileProperty) throws  DaoException{
        try {
            nameFileProperty=nameFileProperty;
            connectionForPool = new ConnectionForPool();
            maxQuantity = 10;
            freePool = new Stack<>();
            freePool.add(da.getConnection(nameFileProperty));
            usedPool = new HashSet<>();
        }catch (Exception e){
            throw new DaoException("Соединение не получено");
        }
    }*/
public void init(String nameFileProperty) throws DaoException {
    this.nameFileProperty=nameFileProperty;
    DatabaseConnection databaseConnection=new DatabaseConnection(this,nameFileProperty);
    maxQuantity = 10;
    freePool = new Stack<>();
    freePool.add(databaseConnection);
    usedPool = new HashSet<>();
}
    public void returnConnection(DatabaseConnection databaseConnection) throws DaoException {
            usedPool.remove(databaseConnection);
        if (databaseConnection != null) {
                if (freePool.size() < maxQuantity) {
                freePool.push(databaseConnection);
               // System.out.println("over_2");
            } else {
                try {
                    databaseConnection.getConnection().close();
                 //   System.out.println("over_3");
                } catch (SQLException e) {
                    throw new DaoException("Ошибка закрытия");
                }
            }
        }
    }


    public Connection getConnection() throws DaoException {
        DatabaseConnection conn = null;
        if (!freePool.isEmpty()) {
            conn = freePool.pop();
            usedPool.add(conn);
            System.out.println("con_1");
        } else {
            try {
                conn = new DatabaseConnection(this,this.nameFileProperty);
                usedPool.add(conn);
                System.out.println("con_2");
            }catch(Exception e){
                throw new DaoException("Соединение не получено");
            }
        }
        return conn;
    }



}
/*public GeneralConnectionPool getInstance()throws DaoException {
       try {
           if (generalConnectionPool == null) {
               synchronized (GeneralConnectionPool.class) {
                   if (generalConnectionPool == null) {
                       generalConnectionPool = new GeneralConnectionPool();
                       System.out.println("ONE!!!");
                   }
               }
               System.out.println("TWO!!!");
           }
           System.out.println("Three!!!");
       } catch (Exception e) {
           throw new DaoException(e);
       }
       return generalConnectionPool;
   }*/

        /*public GeneralConnectionPool getInstance()throws DaoException {
            GeneralConnectionPool generalConnectionPool=null;
        try {
            if (freePool == null && usedPool==null) {
                synchronized (GeneralConnectionPool.class) {
                    if (freePool == null && usedPool==null ) {
                        generalConnectionPool = new GeneralConnectionPool();
                    }
                }
                generalConnectionPool = new GeneralConnectionPool();
            }
        }catch (Exception e){
            throw new DaoException(e);
        }
        return generalConnectionPool;
    }*/
