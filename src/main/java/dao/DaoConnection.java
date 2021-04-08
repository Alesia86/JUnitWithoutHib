package dao;

import dop.DaoException;

import java.sql.Connection;

public interface DaoConnection {
    public Connection isOpen() throws DaoException;
    public void isClose(Connection connection) throws DaoException;


}
