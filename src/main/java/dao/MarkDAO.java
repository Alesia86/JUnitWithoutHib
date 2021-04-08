package dao;

import dop.DaoException;
import dto.Mark;

import java.util.Collection;

public interface MarkDAO {
    public void addMark(Mark mark)throws DaoException;
    public void updateMark(Mark mark)throws DaoException;
    public void removeMark(int id)throws DaoException;
    public void close() throws DaoException;
    public Mark findMark(int id)throws DaoException;
    public Collection<Mark> findMarkStudent(int idStudent) throws DaoException;

}
