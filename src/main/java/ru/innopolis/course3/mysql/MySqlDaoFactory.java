package ru.innopolis.course3.mysql;


import ru.innopolis.course3.Pojo.PracticalAssignments;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика объектов для работы с базой данных на mysql
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "123498765";//Пароль пользователя
    private String url = "jdbc:mysql://localhost:3306/programming_training";//URL адрес
    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера


    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws DataException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataException(e);
        }
        return  connection;
    }

    @Override
    public UniversalDao getDao(Connection connection, Class dtoClass) throws DataException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new DataException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<Class, DaoCreator>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public UniversalDao create(Connection connection) {
                return new MySqlUserDao(connection);
            }
        });

        creators.put(Subject.class, new DaoCreator<Connection>() {
            @Override
            public UniversalDao create(Connection connection) {
                return new MySqlSubjectDao(connection);
            }
        });

        creators.put(PracticalAssignments.class, new DaoCreator<Connection>() {
            @Override
            public UniversalDao create(Connection connection) {
                return new MySqlPracticalDao(connection);
            }
        });
    }
}
