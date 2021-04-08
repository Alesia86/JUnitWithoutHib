package service;

import dao.MarkDAO;
import dop.DaoException;
import dop.GeneralConnection;
import dto.Mark;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class MarkService extends GeneralConnection implements MarkDAO {
    PreparedStatement preparedStatementAdd = null;
    PreparedStatement preparedStatementFind = null;
    PreparedStatement preparedStatementUpdate = null;
    PreparedStatement preparedStatementRemove = null;
    PreparedStatement preparedStatementFindMark=null;
    String sqlQueryAdd= "INSERT INTO MARK (" +
            "STUDENT_ID, " +
            "SECONDNAME_STUDENT, " +
            "SUBJECT_ID, " +
            "NAME_SUBJECT," +
            "MARK) " +
            "VALUES (?,?,?,?,?)";
    String sqlQueryUpdate = "UPDATE MARK SET " +

            "STUDENT_ID=?, " +
            "SECONDNAME_STUDENT=?, " +
            "SUBJECT_ID=?, " +
            "NAME_SUBJECT=?, " +
            "MARK=? "+
            "WHERE ID=?";
    String sqlQueryRemove = "DELETE FROM MARK " +
            "WHERE ID=?";
    String sqlQueryFind= "SELECT " +
            "ID, " +
            "STUDENT_ID, " +
            "SECONDNAME_STUDENT, " +
            "SUBJECT_ID, " +
            "NAME_SUBJECT, " +
            "MARK " +
            "FROM MARK "+
            "WHERE ID=? ";
    String sqlQueryFindMark= "SELECT " +
            "ID, " +
            "STUDENT_ID, " +
            "SECONDNAME_STUDENT, " +
            "SUBJECT_ID, " +
            "NAME_SUBJECT, " +
            "MARK " +
            "FROM MARK "+
            "WHERE STUDENT_ID=?";
    public MarkService() throws DaoException {
        if (connect == null) {
            throw new DaoException("No connection");
        }
        try {
            preparedStatementAdd = connect.prepareStatement(sqlQueryAdd);
            preparedStatementUpdate = connect.prepareStatement(sqlQueryUpdate);
            preparedStatementRemove = connect.prepareStatement(sqlQueryRemove);
            preparedStatementFind=connect.prepareStatement(sqlQueryFind);
            preparedStatementFindMark=connect.prepareStatement(sqlQueryFindMark);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    @Override
    public void close()throws DaoException {
        PreparedStatement izspiskaPrSt = null;
        ArrayList<PreparedStatement> prSt = new ArrayList<>();
        prSt.add(preparedStatementAdd);
        prSt.add(preparedStatementUpdate);
        prSt.add(preparedStatementRemove);
        prSt.add(preparedStatementFind);
        try {
            for (int i = 0; i < prSt.size(); i++) {
                if ((izspiskaPrSt = prSt.get(i)) != null) {
                    izspiskaPrSt.close();
                    System.out.println("PrepareStatement" + (i + 1) + " is closed");
                }
            }
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new DaoException(e);
                }
            }
        }
    }
    @Override
    public Collection<Mark> findMarkStudent(int idStudent) throws DaoException{
        ResultSet resultset = null;
        Mark mark = null;
        ArrayList<Mark> listMark = null;
        try {
            preparedStatementFindMark.setInt(1, idStudent);
            resultset = preparedStatementFindMark.executeQuery();
            listMark=new ArrayList<>();
            while (resultset.next()) {
                mark=new Mark();
                mark.setId(resultset.getInt(1));
                mark.setStudentId(resultset.getInt(2));
                mark.setSecondNameOfStudent(resultset.getString(3));
                mark.setSubjectId(resultset.getInt(4));
                mark.setNameOfSubject(resultset.getString(5));
                mark.setMark(resultset.getInt(6));
                listMark.add(mark);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResultSet is closed");
                } catch (Exception e) {
                    throw new DaoException(e);
                }
            }
            return listMark;
        }

    }
    @Override
    public Mark findMark(int id) throws DaoException{
        ResultSet resultset = null;
        Mark mark = new Mark();
        try {
            preparedStatementFind.setInt(1, id);
            resultset = preparedStatementFind.executeQuery();
            if (resultset.next()) {
                mark.setId(resultset.getInt(1));
                mark.setStudentId(resultset.getInt(2));
                mark.setSecondNameOfStudent(resultset.getString(3));
                mark.setSubjectId(resultset.getInt(4));
                mark.setNameOfSubject(resultset.getString(5));
                mark.setMark(resultset.getInt(6));
            }
            else{
                System.out.println("Mark was not found");
            }
        } catch (SQLException e) {
            throw new DaoException(e);

        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResultSet is closed");
                } catch (Exception e) {
                    throw new DaoException(e);
                }
            }
            return mark;
        }
    }
    @Override
    public void addMark(Mark mark) throws DaoException{
        ResultSet resultset = null;
        Statement statement = null;
        try {
            preparedStatementAdd.setInt(1,mark.getStudentId());
            preparedStatementAdd.setString(2,mark.getSecondNameOfStudent());
            preparedStatementAdd.setInt(3,mark.getSubjectId());
            preparedStatementAdd.setString(4,mark.getNameOfSubject());
            preparedStatementAdd.setInt(5,mark.getMark());
            preparedStatementAdd.executeUpdate();
            statement = connect.createStatement();
            resultset = statement.executeQuery("SELECT ID FROM MARK ORDER BY ID DESC");
            resultset.next();
            mark.setId(resultset.getInt(1));
            System.out.println(mark.getId());
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException(e);
                }
                if (statement != null) {
                    try {
                        statement.close();
                        System.out.println("Statement is closed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void updateMark(Mark mark) throws DaoException{

        try {
            preparedStatementUpdate.setInt(1,mark.getStudentId());
            preparedStatementUpdate.setString(2,mark.getSecondNameOfStudent());
            preparedStatementUpdate.setInt(3,mark.getSubjectId());
            preparedStatementUpdate.setString(4,mark.getNameOfSubject());
            preparedStatementUpdate.setInt(5,mark.getMark());
            preparedStatementUpdate.setInt(6,mark.getId());
            preparedStatementUpdate.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Проверьте правильность введенных данных");
            throw new DaoException(e);
        }
    }

    @Override
    public void removeMark(int id) throws DaoException{
        try{
            preparedStatementRemove.setInt(1,id);
            preparedStatementRemove.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Проверьте правильность введенных данных");
            throw new DaoException(e);
        }
    }
}
