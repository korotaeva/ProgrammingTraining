package ru.innopolis.course3.BL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;
import ru.innopolis.course3.mysql.MySqlUserDao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.List;
import java.util.Random;

/**
 *
 * Бизнес сервер для работы с пользователями
 */
public class UserBL {

    DaoFactory factory;
    Connection connection;
    UniversalDao userDao;

    public UserBL() throws DataException {
        factory = new MySqlDaoFactory();
        connection = (Connection) factory.getContext();
        userDao = factory.getDao(connection, User.class);
    }

    public UserBL(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    public List<User> getAll() throws DataException {
        List<User> list = userDao.getAll();
        return list;
    }

    public User getByPK(Integer id) throws DataException {
        return (User)userDao.getByPK(id);
    }

    public Integer getIdUser(String name, String password) throws DataException {
        return MySqlUserDao.getUserId(name, password);
    }

    public User create(User user) throws DataException {
        return (User)userDao.createByObject(user);
    }

    public static String md5Apache(String st, String salt) {
        String md5Hex = DigestUtils.md5Hex(st + salt);
        return md5Hex;
    }



}
