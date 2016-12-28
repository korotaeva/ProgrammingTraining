package ru.innopolis.course3.BL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.List;

/**
 * Created by korot on 24.12.2016.
 */
public class UserBL {

    DaoFactory factory;
    Connection connection;
    UniversalDao userDao;

    public UserBL() {
        try{
            factory = new MySqlDaoFactory();
            connection = (Connection) factory.getContext();
            userDao = factory.getDao(connection, User.class);
        } catch (DataException e) {
            logger.error("Error", e);
        }
    }

    public UserBL(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    public static Logger logger = LoggerFactory.getLogger(SubjectBL.class);

    public List<User> getAll(){
        List<User> list = null;

        try{
            list = userDao.getAll();
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return list;
    }

}
