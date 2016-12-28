package ru.innopolis.course3.BL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.Pojo.PracticalAssignments;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.List;

/**
 * Created by korot on 24.12.2016.
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
}
