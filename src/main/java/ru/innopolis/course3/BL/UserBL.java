package ru.innopolis.course3.BL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;
import ru.innopolis.course3.mysql.MySqlUserDao;

import java.sql.Connection;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с пользователями
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

    public User getByPK(Integer id){
        User user = null;
        try{
            user = (User)userDao.getByPK(id);
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return user;
    }

    public Integer getIdUser(String name, String password){
        Integer id = null;
        try {
            id = MySqlUserDao.getUserId(name, password);
        } catch (DataException e) {
            logger.error("Error", e);
        }

        return id;
    }

    public User create(User user){
        try{
            user = (User)userDao.createByObject(user);
        } catch (DataException e) {
            logger.error("Error", e);
        }
        return user;
    }


}
