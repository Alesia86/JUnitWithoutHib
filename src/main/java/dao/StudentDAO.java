package dao;

import dop.DaoException;
import dto.Student;

import java.sql.Connection;
import java.util.Collection;

public interface StudentDAO {
    DaoConnection daoConnection = null;
    public void add(Connection connect, Student student)throws DaoException;
    public Student  findStudent(Connection connect, int id)throws DaoException ;
    public Collection<Student> findStudentMore(Connection connect, String secondName)throws DaoException ;
    public void updateStudent(Connection connect, Student student)throws DaoException;
    public void removeStudent(Connection connect, int id)throws DaoException ;
    public void close()throws DaoException;
    public Collection<Student> selectStudents(Connection connect, String firstName, String secondName, String birthDay, String enterYear)  throws DaoException;
    public Collection<Student>  selectGroupOfStudents(Connection connect, String firstName, String secondName, String birthDay, String enterYear, int n, int m) throws DaoException;
    public int  countLines(Connection connect, String firstName, String secondName, String birthDay, String enterYear)throws DaoException ;
//public Collection<Student> readAll()throws DaoException;
    //  public Collection<Student> readTenStudents(int i)  throws DaoException;
}
