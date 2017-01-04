package dao;

import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.Identified;
import ru.innopolis.course3.dao.UniversalDao;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import ru.innopolis.course3.mysql.MySqlDaoFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;


public class MySqlDaoTest extends UniversalDaoTest<Connection> {

    private Connection connection;

    private UniversalDao dao;

    private static final DaoFactory<Connection> factory = new MySqlDaoFactory();

    @Parameterized.Parameters
    public static Collection getParameters() {
        return Arrays.asList(new Object[][]{
                {Subject.class, new Subject()}
        });
    }

    @Before
    public void setUp() throws DataException, SQLException {
        connection = factory.getContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, daoClass);
    }

    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }

    @Override
    public UniversalDao dao() {
        return dao;
    }

    @Override
    public Connection context() {
        return connection;
    }

    public MySqlDaoTest(Class clazz, Identified<Integer> notPersistedDto) {
        super(clazz, notPersistedDto);
    }
}