package ru.innopolis.course3.BL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.Pojo.PracticalAssignments;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с практическими заданиями
 */
public class PracticalAssignmentsBL {
    public static Logger logger = LoggerFactory.getLogger(SubjectBL.class);
    DaoFactory factory;
    Connection connection;
    UniversalDao practicalDao;

    public PracticalAssignmentsBL() {
        try{
            factory = new MySqlDaoFactory();
            connection = (Connection) factory.getContext();
            practicalDao = factory.getDao(connection, PracticalAssignments.class);
        } catch (DataException e) {
            logger.error("Error", e);
        }
    }

    public List<PracticalAssignments> getAll(){
        List<PracticalAssignments> list = null;

        try{
            list = practicalDao.getAll();
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return list;
    }

    public List<PracticalAssignments> getAllBySubject(String subject){
        List<PracticalAssignments> list = null;

        try{
            //list = practicalDao.getAllBySubject();
            list = (List<PracticalAssignments>) practicalDao.getByKey(subject,"subject");
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return list;
    }

    public PracticalAssignments getByPK(Integer id){
        PracticalAssignments practical = null;

        try{
            practical = (PracticalAssignments)practicalDao.getByPK(id);
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return practical;
    }

    public PracticalAssignments create(PracticalAssignments practicalAssignments){
        try{
            practicalAssignments = (PracticalAssignments)practicalDao.createByObject(practicalAssignments);
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return practicalAssignments;
    }

    public void delete(PracticalAssignments subject){
        try{
            practicalDao.delete(subject);
        } catch (DataException e) {
            logger.error("Error", e);
        }

    }

    public void update(PracticalAssignments subject){
        try{
            practicalDao.update(subject);
        } catch (DataException e) {
            logger.error("Error", e);
        }
    }
}
