package Test;

import dao.StudentDAO;
import dop.DaoException;
import dop.GeneralConnectionPool;
import dto.Student;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.StudentService;

import java.sql.Connection;
import java.sql.Date;

public class Test_Student {
    GeneralConnectionPool generalConnectionPool;
@BeforeClass
public void getGeneralPool(){
    try {
        generalConnectionPool=new GeneralConnectionPool();
        generalConnectionPool.init("db.properties");
    } catch (DaoException e) {
        e.printStackTrace();
    }
}

   @Test
    public void testAddStudent() throws DaoException {
       Student student=new Student();
       student.setFirstName("Masha");
       student.setSecondName("Svirid");
       student.setBirthDay(Date.valueOf("2001-04-05"));
       student.setEnterYear(1);
       StudentDAO studentDAO=new StudentService(generalConnectionPool.getConnection());
       studentDAO.add(generalConnectionPool.getConnection(),student);
       int idStudent =student.getId();
       Student studentFromDB=studentDAO.findStudent(generalConnectionPool.getConnection(), idStudent);
       Assert.assertEquals(student.getSecondName(),studentFromDB.getSecondName());

    }



}
