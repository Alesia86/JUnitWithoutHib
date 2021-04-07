package Test;

import dao.StudentDAO;
import dop.DaoException;
import dop.DatabaseConnection;
import dop.GeneralConnectionPool;
import dto.Student;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.StudentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Test_Student {
    GeneralConnectionPool generalConnectionPool;

@BeforeClass
public void getGeneralPool() throws DaoException {

    try {
        generalConnectionPool=new GeneralConnectionPool();
        generalConnectionPool.init("db_unit.properties");
    } catch (DaoException e) {
        e.printStackTrace();
    }
    /*Connection connection=null;
    Statement statement=null;
    String sqlQueryDelete = "DELETE FROM STUDENT";
    try {
        connection=generalConnectionPool.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(sqlQueryDelete);
    } catch (SQLException  e) {
        e.printStackTrace();
    }
    finally {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            generalConnectionPool.returnConnection((DatabaseConnection) connection);
        }
    }*/

}
@BeforeMethod
public void cleanTable() throws DaoException {
    Connection connection=null;
    Statement statement=null;
    String sqlQueryDelete = "DELETE FROM STUDENT";
    try {
        connection=generalConnectionPool.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(sqlQueryDelete);
    } catch (SQLException  e) {
        e.printStackTrace();
    }
    finally {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            generalConnectionPool.returnConnection((DatabaseConnection) connection);
        }
    }

}

   @Test
    public void testAddStudent() throws DaoException {
       Student student=new Student();
       student.setFirstName("Vika");
       student.setSecondName("Svirid");
       student.setBirthDay(Date.valueOf("2001-04-05"));
       student.setEnterYear(1);
       StudentDAO studentDAO=new StudentService(generalConnectionPool.getConnection());
       studentDAO.add(generalConnectionPool.getConnection(),student);
       int idStudent =student.getId();
       Student studentFromDB=studentDAO.findStudent(generalConnectionPool.getConnection(), idStudent);
       Assert.assertEquals(student.getSecondName(),studentFromDB.getSecondName());

    }

    @Test
    public void testDeleteStudent() throws DaoException {
        Student student = new Student();
        student.setFirstName("Misha");
        student.setSecondName("Sidorov");
        student.setBirthDay(Date.valueOf("2005-06-21"));
        student.setEnterYear(2);
        StudentDAO studentDAO = new StudentService(generalConnectionPool.getConnection());
        studentDAO.add(generalConnectionPool.getConnection(), student);
        int idStudent = student.getId();
        studentDAO.removeStudent(generalConnectionPool.getConnection(), idStudent);
        Assert.assertEquals(new Student(), studentDAO.findStudent(generalConnectionPool.getConnection(), idStudent));
    }
    @Test
    public void testFindStudentFromSecondName() throws DaoException {
        StudentDAO studentDAO=new StudentService(generalConnectionPool.getConnection());
       String [] spisok={"Masha","Sasha","Vera","Olia","Katia","Vlada","Victoria","Ulia","Ilona","Vasia"};
       List<Student> listStudent=new ArrayList<>();
       for(int i=0;i<spisok.length;i++){
           Student student1 = new Student();
           student1.setFirstName(spisok[i]);
           student1.setSecondName("Sidorova");
           student1.setBirthDay(Date.valueOf("2005-06-21"));
           student1.setEnterYear(2);
           listStudent.add(student1);
           studentDAO.add(generalConnectionPool.getConnection(),student1);
           Student student2 = new Student();
           student2.setFirstName(spisok[i]);
           student2.setSecondName("Petrova");
           student2.setBirthDay(Date.valueOf("1997-07-30"));
           student2.setEnterYear(4);
           listStudent.add(student2);
           studentDAO.add(generalConnectionPool.getConnection(),student2);
       }
        Collection<Student>collection=studentDAO.findStudentMore(generalConnectionPool.getConnection(),"Sidorova");
        int numberOfStudent=spisok.length;
        System.out.println(numberOfStudent);
        int numberFoundStudent=collection.size();
        System.out.println(numberFoundStudent);
        Assert.assertEquals(numberOfStudent,numberFoundStudent);
    }
    @Test
    public void testUpdateStudent() throws DaoException {
        Student student = new Student();
        student.setFirstName("Vova");
        student.setSecondName("Kolosok");
        student.setBirthDay(Date.valueOf("2002-06-21"));
        student.setEnterYear(3);
        StudentDAO studentDAO = new StudentService(generalConnectionPool.getConnection());
        studentDAO.add(generalConnectionPool.getConnection(), student);
        int idStudent = student.getId();
        student.setEnterYear(4);
        studentDAO.updateStudent(generalConnectionPool.getConnection(),student);
        Student student1=studentDAO.findStudent(generalConnectionPool.getConnection(),idStudent);
        Assert.assertSame(student,student1);
    }

}
