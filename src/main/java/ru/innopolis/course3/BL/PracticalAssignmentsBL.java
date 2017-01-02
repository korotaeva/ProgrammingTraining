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
    DaoFactory factory;
    Connection connection;
    UniversalDao practicalDao;

    public PracticalAssignmentsBL() throws DataException {
        factory = new MySqlDaoFactory();
        connection = (Connection) factory.getContext();
        practicalDao = factory.getDao(connection, PracticalAssignments.class);
    }

    public List<PracticalAssignments> getAll() throws DataException {
        List<PracticalAssignments> list = practicalDao.getAll();
        return list;
    }

    public List<PracticalAssignments> getAllBySubject(String subject) throws DataException {
        List<PracticalAssignments> list =  (List<PracticalAssignments>) practicalDao.getByKey(subject,"subject");
        return list;
    }

    public PracticalAssignments getByPK(Integer id) throws DataException {
        return (PracticalAssignments)practicalDao.getByPK(id);
    }

    public PracticalAssignments create(PracticalAssignments practicalAssignments) throws DataException {
        return (PracticalAssignments)practicalDao.createByObject(practicalAssignments);
    }

    public void delete(PracticalAssignments subject) throws DataException {
        practicalDao.delete(subject);
    }

    public void update(PracticalAssignments subject) throws DataException {
        practicalDao.update(subject);
    }
}
