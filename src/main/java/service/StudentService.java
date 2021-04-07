package service;

import dao.DaoConnection;
import dao.StudentDAO;
import dop.*;
import dto.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class StudentService implements StudentDAO {
    PreparedStatement preparedStatementAdd;
    PreparedStatement preparedStatementFind;
    PreparedStatement preparedStatementFindMoreStudent;
    PreparedStatement preparedStatementUpdate;
    PreparedStatement preparedStatementRemove;
    PreparedStatement preparedStatementRead;
    PreparedStatement preparedStatementSelectStudent;
    PreparedStatement preparedStatementSelectGroupOfStudent;
    PreparedStatement preparedStatementCountStudents;

    /*{
        if (connectionPool == null || connectionPool.isFull()) {
            connect = GeneralConnection2.getConnection();
        }else{
            try {
                Connection connect1 = connectionPool.getConnection();
                connect=connect1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
*/
    private final static String sqlQueryAdd = "INSERT INTO STUDENT (" +
            "first_name, " +
            "second_name, " +
            "birth_day, " +
            "enter_year) " +
            "VALUES (?,?,?,?)";
    private final static String sqlQueryFind = "SELECT ID, FIRST_NAME, SECOND_NAME, BIRTH_DAY,ENTER_YEAR FROM STUDENT WHERE ID=?";
    private final static String sqlQueryUpdate = "UPDATE STUDENT SET " +
            "FIRST_NAME=?, " +
            "SECOND_NAME=?, " +
            "BIRTH_DAY=?, " +
            "ENTER_YEAR=? " +
            "WHERE ID=?";
    private final static String sqlQueryRemove = "DELETE FROM STUDENT WHERE ID=?";
    private final static String sqlQueryReadTenStudents = "SELECT ID, FIRST_NAME, SECOND_NAME, BIRTH_DAY,ENTER_YEAR FROM STUDENT LIMIT ?,10";
    private final static String sqlQueryFindMoreStudent = "SELECT ID, FIRST_NAME, SECOND_NAME, BIRTH_DAY,ENTER_YEAR FROM STUDENT WHERE SECOND_NAME=?";
    private final static String sqlQuerySelectStudent = "SELECT ID, FIRST_NAME, SECOND_NAME, BIRTH_DAY,ENTER_YEAR FROM STUDENT WHERE FIRST_NAME LIKE  ?  AND SECOND_NAME LIKE ? AND BIRTH_DAY LIKE ? AND ENTER_YEAR LIKE ? ";
    private final static String sqlQuerySelectTenStudent = "SELECT ID, FIRST_NAME, SECOND_NAME, BIRTH_DAY,ENTER_YEAR FROM STUDENT WHERE FIRST_NAME LIKE  ?  AND SECOND_NAME LIKE ? AND BIRTH_DAY LIKE ? AND ENTER_YEAR LIKE ? LIMIT ?,? ";
    private final static String sqlQueryCountStudents = "SELECT COUNT(ID) FROM STUDENT WHERE FIRST_NAME LIKE  ?  AND SECOND_NAME LIKE ? AND BIRTH_DAY LIKE ? AND ENTER_YEAR LIKE ? ";

    /*public StudentService() throws DaoException {
        try {
            connect=new GeneralConnectionPool().getInstance().getConnection();
            preparedStatementAdd = connect.prepareStatement(sqlQueryAdd, Statement.RETURN_GENERATED_KEYS);
            preparedStatementFind = connect.prepareStatement(sqlQueryFind);
            preparedStatementUpdate = connect.prepareStatement(sqlQueryUpdate);
            preparedStatementRemove = connect.prepareStatement(sqlQueryRemove);
            preparedStatementRead = connect.prepareStatement(sqlQueryReadTenStudents);
            preparedStatementFindMoreStudent = connect.prepareStatement(sqlQueryFindMoreStudent);
            preparedStatementSelectStudent = connect.prepareStatement(sqlQuerySelectStudent);
            preparedStatementSelectGroupOfStudent=connect.prepareStatement(sqlQuerySelectTenStudent);
            preparedStatementCountStudents=connect.prepareStatement(sqlQueryCountStudents);
            GeneralConnectionPool.getInstance().returnConnection(connect);
            System.out.println("после закрытия"+connect);
        } catch (SQLException throwables) {
            throw new DaoException("Error connection",throwables);
        }
    }*/

    public StudentService(Connection connect) throws DaoException {

        try {

            preparedStatementAdd = connect.prepareStatement(sqlQueryAdd, Statement.RETURN_GENERATED_KEYS);
            preparedStatementFind = connect.prepareStatement(sqlQueryFind);
            preparedStatementUpdate = connect.prepareStatement(sqlQueryUpdate);
            preparedStatementRemove = connect.prepareStatement(sqlQueryRemove);
            preparedStatementRead = connect.prepareStatement(sqlQueryReadTenStudents);
            preparedStatementFindMoreStudent = connect.prepareStatement(sqlQueryFindMoreStudent);
            preparedStatementSelectStudent = connect.prepareStatement(sqlQuerySelectStudent);
            preparedStatementSelectGroupOfStudent = connect.prepareStatement(sqlQuerySelectTenStudent);
            preparedStatementCountStudents = connect.prepareStatement(sqlQueryCountStudents);
            connect.close();
            System.out.println("in studentService" + connect);
            System.out.println("после закрытия" + connect);
        } catch (SQLException throwables) {
            throw new DaoException("Error connection", throwables);
        }
    }

    @Override
    public int countLines(Connection connect, String firstName, String secondName, String birthDay, String enterYear) throws DaoException {

        ResultSet resultset = null;
        int count = 0;
        if (firstName == null || firstName == "") {
            firstName = "%";
        }
        if (secondName == null || secondName == "") {
            secondName = "%";
        }
        if (birthDay == null || birthDay == "") {
            birthDay = "%";
        }
        if (enterYear == null || enterYear == "") {
            enterYear = "%";
        }
        try {

            preparedStatementCountStudents.setString(1, firstName);
            preparedStatementCountStudents.setString(2, secondName);
            preparedStatementCountStudents.setString(3, birthDay);
            preparedStatementCountStudents.setString(4, enterYear);
            resultset = preparedStatementCountStudents.executeQuery();
            if (resultset.next()) {
                count = resultset.getInt(1);
            }
            connect.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error Statement is closed", e);
                }
            }
        }
        return count;
    }

    /*@Override
    public int countLines() throws DaoException {
        ResultSet resultset = null;
        Statement statement = null;
        int count=0;
        String sqlQuery = "SELECT COUNT(ID) FROM STUDENT";
        try {
            statement = connect.createStatement();
            resultset = statement.executeQuery(sqlQuery);
            if (resultset.next()) {
                count = resultset.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error Statement is closed",e);
                }
                if (statement != null) {
                    try {
                        statement.close();
                        System.out.println("Statement is closed");
                    } catch (Exception e) {
                        throw new DaoException("Error Statement is closed",e);
                    }
                }

            }

        }
        return count;
    }*/
    @Override
    public void add(Connection connect, Student student) throws DaoException {

        ResultSet resultset = null;
        try {
            preparedStatementAdd.setString(1, student.getFirstName());
            preparedStatementAdd.setString(2, student.getSecondName());
            preparedStatementAdd.setDate(3, student.getBirthDay());
            preparedStatementAdd.setInt(4, student.getEnterYear());
            preparedStatementAdd.executeUpdate();
            resultset = preparedStatementAdd.getGeneratedKeys();
            if (resultset.next()) {
                student.setId(resultset.getInt(1));
                System.out.println(student.getId());
            }
        } catch (Exception e) {
            throw new DaoException("Resultset for add student is error", e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResultSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error resultSet is closed", e);
                } finally {
                    if (connect != null) {
                        try {
                            connect.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public Student findStudent(Connection connect, int id) throws DaoException {

        ResultSet resultset = null;
        Student student = new Student();
        try {

            preparedStatementFind.setInt(1, id);
            resultset = preparedStatementFind.executeQuery();
            if (resultset.next()) {
                student.setId(resultset.getInt(1));
                student.setFirstName(resultset.getString(2));
                student.setSecondName(resultset.getString(3));
                student.setBirthDay(resultset.getDate(4));
                student.setEnterYear(resultset.getInt(5));
            } else {
                System.out.println("Student was not found");
            }

        } catch (Exception e) {
            throw new DaoException("Student was not found", e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResultSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error resultSet is closed", e);
                } finally {
                    if (connect != null) {
                        try {
                            connect.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return student;
    }


    @Override
    public Collection<Student> findStudentMore(Connection connect, String secondName) throws DaoException {
       // connect=GeneralConnectionPool.getInstance().getConnection();
        ArrayList<Student> listStudent = new ArrayList<>();
        ResultSet resultset = null;
        try {

            preparedStatementFindMoreStudent.setString(1, secondName);
            resultset = preparedStatementFindMoreStudent.executeQuery();
            while (resultset.next()) {
                Student student = new Student();
                student.setId(resultset.getInt(1));
                student.setFirstName(resultset.getString(2));
                student.setSecondName(resultset.getString(3));
                student.setBirthDay(resultset.getDate(4));
                student.setEnterYear(resultset.getInt(5));
                listStudent.add(student);
            }
        } catch (Exception e) {
            throw new DaoException("Error find",e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error resultSet is closed", e);
                } finally {
                    if (connect != null) {
                        try {
                            connect.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return listStudent;
    }

    /*@Override
    public Collection<Student> readAll() throws DaoException {
        ArrayList<Student> listStudent = new ArrayList<>();
        ResultSet resultset = null;
        Statement statement = null;
        String sqlQuery = "SELECT ID, FIRST_NAME, SECOND_NAME, BIRTH_DAY,ENTER_YEAR FROM STUDENT";
        try {
            statement = connect.createStatement();
            resultset = statement.executeQuery(sqlQuery);

            while (resultset.next()) {
                Student student = new Student();
                student.setId(resultset.getInt(1));
                student.setFirstName(resultset.getString(2));
                student.setSecondName(resultset.getString(3));
                student.setBirthDay(resultset.getDate(4));
                student.setEnterYear(resultset.getInt(5));
                listStudent.add(student);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error Statement is closed",e);
                }
                if (statement != null) {
                    try {
                        statement.close();
                        System.out.println("Statement is closed");
                    } catch (Exception e) {
                        throw new DaoException("Error Statement is closed",e);
                    }
                }

            }

        }
        return listStudent;
    }*/

    @Override
    public void updateStudent(Connection connect, Student student) throws DaoException {

            try {
                preparedStatementUpdate.setString(1, student.getFirstName());
                preparedStatementUpdate.setString(2, student.getSecondName());
                preparedStatementUpdate.setDate(3, student.getBirthDay());
                preparedStatementUpdate.setInt(4, student.getEnterYear());
                preparedStatementUpdate.setInt(5, student.getId());
                preparedStatementUpdate.executeUpdate();
                daoConnection.isClose(connect);
            } catch (SQLException e) {
                System.out.println("Проверьте правильность введенных данных");
                throw new DaoException("Error update", e);
            } finally {
                if (connect != null) {
                    try {
                        connect.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    @Override
    public void removeStudent(Connection connect, int id) throws DaoException {
        //connect=GeneralConnectionPool.getInstance().getConnection();
        try {

            preparedStatementRemove.setInt(1, id);
            preparedStatementRemove.executeUpdate();

        } catch (Exception e) {
            System.out.println("Проверьте правильность введенных данных");
            throw new DaoException("Error remove",e);
        }
        finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
/*private void closeMini(Connection connect, ArrayList<PreparedStatement> prSt, int n) throws DaoException{
    PreparedStatement izspiskaPrSt;
    try {
        for (int i = n; i < prSt.size(); i++) {
            n = i;
            if ((izspiskaPrSt = prSt.get(i)) != null) {
                izspiskaPrSt.close();
                System.out.println("PrepareStatement" + (i + 1) + " is closed");
            }
        }
    }
    catch (Exception e) {
        n++;
        if(n<prSt.size()) {
            closeMini(prSt, n);
        }else{
            throw new DaoException(e);
        }
    }
}*/
    @Override
    public void close() throws DaoException {

        ArrayList<PreparedStatement> prSt = new ArrayList<>();
        ArrayList<SQLException> exceptionsFromClose = new ArrayList<>();
        prSt.add(preparedStatementAdd);
        prSt.add(preparedStatementFind);
        prSt.add(preparedStatementUpdate);
        prSt.add(preparedStatementRemove);
        prSt.add(preparedStatementFindMoreStudent);
        prSt.add(preparedStatementSelectStudent);
        prSt.add(preparedStatementSelectGroupOfStudent);
        prSt.add(preparedStatementCountStudents);
        for (PreparedStatement izspiskaPrSt : prSt) {
            try {
                if (izspiskaPrSt != null) {
                    izspiskaPrSt.close();
                }
            } catch (SQLException e) {
                exceptionsFromClose.add(e);
            }
        }
            /*for (int i = 0; i < prSt.size(); i++) {
                try {
                    PreparedStatement izspiskaPrSt;
                    if ((izspiskaPrSt = prSt.get(i)) != null) {
                        izspiskaPrSt.close();
                        System.out.println("PrepareStatement" + (i + 1) + " is closed");
                    }
                } catch (SQLException e) {
                    exceptionsFromClose.add(e);
                }
            }*/
                if (!exceptionsFromClose.isEmpty()) {

                           // exceptionsFromClose.stream().forEach((n)->n.getMessage());
                    throw new DaoException("Closing PreparedStatement have"+exceptionsFromClose.toString()+"errors");
                }
    }
    /*@Override
    public void close() throws DaoException {
        ArrayList<PreparedStatement> prSt = new ArrayList<>();
        prSt.add(preparedStatementAdd);
        prSt.add(preparedStatementFind);
        prSt.add(preparedStatementUpdate);
        prSt.add(preparedStatementRemove);
        prSt.add(preparedStatementFindMoreStudent);
        prSt.add(preparedStatementSelectStudent);
        // prSt.add(preparedStatementRead);
        prSt.add(preparedStatementSelectGroupOfStudent);
        prSt.add(preparedStatementCountStudents);
        int n = 0;
        try {
            closeMini(prSt, n);
        } catch (Exception e) {
            throw new DaoException("Error close PreparedStatement", e);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                    //connectionPool.returnConnection(connect);
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new DaoException("Error close connection", e);
                } finally {
                    try {
                        connect.close();
                    } catch (SQLException e) {
                        throw new DaoException("Error close connection", e);
                    }

                }
            }
        }
    }*/

    /*@Override
    public Collection<Student> readTenStudents(int i) throws DaoException {
        ArrayList<Student> listStudent = new ArrayList<>();
        ResultSet resultset = null;
        try {
            i=(i-1)*10;
            preparedStatementRead.setInt(1, i);
            resultset = preparedStatementRead.executeQuery();

            while (resultset.next()) {
                Student student = new Student();
                student.setId(resultset.getInt(1));
                student.setFirstName(resultset.getString(2));
                student.setSecondName(resultset.getString(3));
                student.setBirthDay(resultset.getDate(4));
                student.setEnterYear(resultset.getInt(5));
                listStudent.add(student);
            }
        } catch (SQLException e) {
            throw new DaoException("Error readTenStudent",e);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error close resultSet",e);
                }

            }

        }
        return listStudent;
    }*/
    @Override
    public Collection<Student> selectGroupOfStudents(Connection connect, String firstName, String secondName, String birthDay, String enterYear,int n, int m) throws DaoException {

        ResultSet resultset = null;
        ArrayList<Student> listStudent = new ArrayList<>();
        if (firstName == null||firstName=="") {
            firstName = "%";
        }
        if(secondName==null||secondName=="") {
            secondName = "%";
        }
        if(birthDay==null||birthDay==""){
            birthDay="%";
        }
        if(enterYear==null||enterYear==""){
            enterYear="%";
        }
        try {
            n=(n-1)*m;
            preparedStatementSelectGroupOfStudent.setString(1, firstName);
            preparedStatementSelectGroupOfStudent.setString(2, secondName);
            preparedStatementSelectGroupOfStudent.setString(3, birthDay);
            preparedStatementSelectGroupOfStudent.setString(4, enterYear);
            preparedStatementSelectGroupOfStudent.setInt(5, n);
            preparedStatementSelectGroupOfStudent.setInt(6, m);
            resultset = preparedStatementSelectGroupOfStudent.executeQuery();
            while (resultset.next()) {
                Student person = new Student();
                person.setId(resultset.getInt(1));
                person.setFirstName(resultset.getString(2));
                person.setSecondName(resultset.getString(3));
                person.setBirthDay(resultset.getDate(4));
                person.setEnterYear(resultset.getInt(5));
                listStudent.add(person);
            }


        }catch (Exception throwables) {
            throw new DaoException("Error select",throwables);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error close resultSet",e);
                }
                finally {
                    if (connect != null) {
                        try {
                            connect.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return listStudent;
    }
    @Override
    public Collection<Student> selectStudents(Connection connect, String firstName, String secondName, String birthDay, String enterYear) throws DaoException {
        //connect=GeneralConnectionPool.getInstance().getConnection();
        ResultSet resultset = null;
        ArrayList<Student> listStudent = new ArrayList<>();
            if (firstName == null||firstName=="") {
                firstName = "%";
            }
            if(secondName==null||secondName=="") {
                secondName = "%";
            }
            if(birthDay==null||birthDay==""){
                birthDay="%";
            }
            if(enterYear==null||enterYear==""){
                enterYear="%";
            }
        try {

            preparedStatementSelectStudent.setString(1, firstName);
            preparedStatementSelectStudent.setString(2, secondName);
            preparedStatementSelectStudent.setString(3, birthDay);
            preparedStatementSelectStudent.setString(4, enterYear);
            resultset = preparedStatementSelectStudent.executeQuery();
            while (resultset.next()) {
                Student person = new Student();
                person.setId(resultset.getInt(1));
                person.setFirstName(resultset.getString(2));
                person.setSecondName(resultset.getString(3));
                person.setBirthDay(resultset.getDate(4));
                person.setEnterYear(resultset.getInt(5));
                listStudent.add(person);
            }


        }catch (Exception throwables) {
            throw new DaoException("Error select",throwables);
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                    System.out.println("ResuitSet is closed");
                } catch (Exception e) {
                    throw new DaoException("Error close resultSet",e);
                }
                finally {
                    if (connect != null) {
                        try {
                            connect.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return listStudent;
    }
}
