package dao;

import dop.DaoException;
import dto.Student;
import dto.Subject;

import java.util.Collection;
import java.util.Map;

public interface SubjectDAO {
    public void addSubject(Subject subject)throws DaoException;
    public void updateSubject(Subject subject)throws DaoException;
    public void removeSubject(int id)throws DaoException;
    public Subject findSubject(int id)throws DaoException;
    public Collection<Subject> findSubjectName(String nameSubject)throws DaoException;
    public Collection<Subject> readAllSubject()throws DaoException;
    public Map<String, Integer> findAllMark(Student student)throws DaoException;
    public void close() throws DaoException;
}
