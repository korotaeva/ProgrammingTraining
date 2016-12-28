package ru.innopolis.course3.BL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.Identified;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.List;

/**
 * Created by korot on 23.12.2016.
 */
public class SubjectBL {
    public static Logger logger = LoggerFactory.getLogger(SubjectBL.class);

    DaoFactory factory;
    Connection connection;
    UniversalDao subjectDao;

    public SubjectBL() {
        try{
            factory = new MySqlDaoFactory();
            connection = (Connection) factory.getContext();
            subjectDao = factory.getDao(connection, Subject.class);
        } catch (DataException e) {
            logger.error("Error", e);
        }
    }

    public List<Subject> getAll(){
        List<Subject> list = null;

        try{
            list = subjectDao.getAll();
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return list;
    }

    public Subject create(Subject subject){
        try{
            subject = (Subject)subjectDao.createByObject(subject);
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return subject;
    }

    public void delete(Subject subject){
        try{
            subjectDao.delete(subject);
        } catch (DataException e) {
            logger.error("Error", e);
        }

    }

    public void update(Subject subject){
        try{
            subjectDao.update(subject);
        } catch (DataException e) {
            logger.error("Error", e);
        }
    }

    public Subject getByPK(Integer id){
        Subject subject = null;

        try{
            subject = (Subject)subjectDao.getByPK(id);
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return subject;
    }


}
