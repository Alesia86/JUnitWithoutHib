package service;

import dao.SubjectDAO;
import dop.DaoException;
import dop.GeneralConnection;
import dto.Student;
import dto.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SubjectService extends GeneralConnection implements SubjectDAO {
    PreparedStatement preparedStatementAdd = null;
    PreparedStatement preparedStatementFind = null;
    PreparedStatement preparedStatementFindSubjectId = null;
    PreparedStatement preparedStatementFindSubjectName = null;
    PreparedStatement preparedStatementUpdate = null;
    PreparedStatement preparedStatementRemove = null;
    String sqlQueryAdd = "INSERT INTO SUBJECT (" +
            "NAME_SUBJECT, " +
            "NAME_TEACHER, " +
            "KAFEDRA) " +
            " VALUES (?,?,?)";
    String sqlQueryUpdate = "UPDATE SUBJECT SET " +
            "NAME_SUBJECT=?, " +
            "NAME_TEACHER=?, " +
            "KAFEDRA=? " +
            "WHERE ID=?";
    String sqlQueryRemove = "DELETE FROM SUBJECT " +
            "WHERE ID=?";
    String sqlQueryFind = "SELECT " +
            "SUB.NAME_SUBJECT, " +
            "M.MARK " +
            "FROM SUBJECT SUB INNER JOIN MARK M " +
            "ON SUB.ID=M.SUBJECT_ID " +
            "WHERE M.STUDENT_ID=?";
    String sqlQueryFindSubjectId = "SELECT " +
            "ID, "+
            "NAME_SUBJECT, " +
            "NAME_TEACHER, " +
            "KAFEDRA " +
            "FROM SUBJECT "+
            "WHERE ID=?";
    String sqlQueryFindSubjectName = "SELECT " +
            "ID, "+
            "NAME_SUBJECT, " +
            "NAME_TEACHER, " +
            "KAFEDRA " +
            "FROM SUBJECT "+
            "WHERE NAME_SUBJECT LIKE ? ";


    public SubjectService() throws DaoException {
        if (connect == null) {
            throw new DaoException("No connection");
        }

        try {
            preparedStatementAdd = connect.prepareStatement(sqlQueryAdd, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementFind = connect.prepareStatement(sqlQueryFind);
            preparedStatementFindSubjectId = connect.prepareStatement(sqlQueryFindSubjectId);
            preparedStatementFindSubjectName = connect.prepareStatement(sqlQueryFindSubjectName);
            preparedStatementUpdate = connect.prepareStatement(sqlQueryUpdate);
            preparedStatementRemove = connect.prepareStatement(sqlQueryRemove);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    @Override
    public void close() throws DaoException {
        PreparedStatement izspiskaPrSt ;
        ArrayList<PreparedStatement> prSt =new ArrayList<>();
        prSt.add(preparedStatementAdd);
        prSt.add(preparedStatementFind);
        prSt.add(preparedStatementFindSubjectId);
        prSt.add(preparedStatementFindSubjectName);
        prSt.add(preparedStatementUpdate);
        prSt.add(preparedStatementRemove);
        try {
            for (int i = 0; i < prSt.size(); i++) {
                if ((izspiskaPrSt = prSt.get(i)) != null) {
                    izspiskaPrSt.close();
                    System.out.println("PrepareStatement" + (i + 1) + " is closed");
                }
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        finally {
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
    public Subject findSubject(int id) throws DaoException{
        ResultSet resultset = null;
        Subject subject = new Subject();
        try {

            preparedStatementFindSubjectId.setInt(1, id);
            resultset = preparedStatementFindSubjectId.executeQuery();
            if (resultset.next()) {
                subject.setId(resultset.getInt(1));
                subject.setName_subject(resultset.getString(2));
                subject.setName_teacher(resultset.getString(3));
                subject.setKafedra(resultset.getString(4));
            }
            else{
                System.out.println("Subject was not found");
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
            return subject;
        }
    }
    @Override
    public Collection<Subject> findSubjectName(String nameSubject) throws DaoException{
        ArrayList <Subject> list=new ArrayList<>();
        ResultSet resultset = null;
        Subject subject = new Subject();
        try {
            String name='%'+nameSubject+'%';
            preparedStatementFindSubjectName.setString(1, name);
            resultset = preparedStatementFindSubjectName.executeQuery();
            while (resultset.next()) {
                subject.setId(resultset.getInt(1));
                subject.setName_subject(resultset.getString(2));
                subject.setName_teacher(resultset.getString(3));
                subject.setKafedra(resultset.getString(4));
                list.add(subject);
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
            return list;
        }
    }
    @Override
    public void addSubject(Subject subject) throws DaoException {
        ResultSet resultset = null;
        try {
            preparedStatementAdd.setString(1, subject.getName_subject());
            preparedStatementAdd.setString(2, subject.getName_teacher());
            preparedStatementAdd.setString(3, subject.getKafedra());
            preparedStatementAdd.executeUpdate();
            resultset = preparedStatementAdd.getGeneratedKeys();
            if( resultset.next()) {
                subject.setId(resultset.getInt(1));
                System.out.println(subject.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

            }
        }
    }


    @Override
    public void updateSubject(Subject subject) throws DaoException {
        try {
            preparedStatementUpdate.setString(1, subject.getName_subject());
            preparedStatementUpdate.setString(2, subject.getName_teacher());
            preparedStatementUpdate.setString(3, subject.getKafedra());
            preparedStatementUpdate.setInt(4, subject.getId());
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Проверьте правильность введенных данных");
            throw new DaoException(e);
        }
    }

    @Override
    public void removeSubject(int id) throws DaoException {
        try {
            preparedStatementRemove.setInt(1, id);
            preparedStatementRemove.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Проверьте правильность введенных данных");
            throw new DaoException(e);
        }
    }

    @Override
    public Collection<Subject> readAllSubject() throws DaoException {
        String sqlQuery = "SELECT ID, NAME_SUBJECT, NAME_TEACHER, KAFEDRA FROM SUBJECT";
        ArrayList<Subject> listSubject = new ArrayList<>();
        ResultSet resultset = null;
        Statement statement = null;
        try {
            statement = connect.createStatement();
            resultset = statement.executeQuery(sqlQuery);
            while (resultset.next()) {
                Subject subject =new Subject();
                subject.setId(resultset.getInt(1));
                subject.setName_subject(resultset.getString(2));
                subject.setName_teacher(resultset.getString(3));
                subject.setKafedra(resultset.getString(4));
                listSubject.add(subject);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResultSet is closed");
                } catch (Exception e) {
                    throw new DaoException(e);                }
            }
            if (statement != null) {
                try {
                    statement.close();
                    System.out.println("Statement is closed");
                } catch (Exception e) {
                    throw new DaoException(e);
                }
            }
        }
        return listSubject;
    }

    public Map<String, Integer> findAllMark(Student student) throws DaoException {
        Map<String, Integer> spisok = null;ResultSet resultset = null;
        try {
            preparedStatementFind.setInt(1, student.getId());
            resultset = preparedStatementFind.executeQuery();
            spisok = new HashMap<>();
            while (resultset.next()) {
                spisok.put(resultset.getString(1), resultset.getInt(2));
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return spisok;
    }
}
