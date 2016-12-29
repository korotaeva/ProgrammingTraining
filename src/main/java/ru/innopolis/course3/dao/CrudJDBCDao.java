package ru.innopolis.course3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
/**
 *
 * Абстрактный класс предоставляющий базовую реализацию CRUD операций с использованием JDBC.
 *
 * @param <T>  тип объекта
 * @param <PK> тип первичного ключа
 */
public abstract class CrudJDBCDao<T extends Identified<PK>, PK extends Integer> implements UniversalDao<T, PK> {

    private Connection connection;


    /**
     * Возвращает sql запрос для получения всех записей.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getSelectQuery();

    /**
     * Возвращает sql запрос для вставки новой записи в базу данных.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Возвращает sql запрос для обновления записи.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    public abstract String getUpdateQuery();

    /**
     * Возвращает sql запрос для удаления записи из базы данных.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    public abstract String getDeleteQuery();

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws DataException;

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DataException;

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DataException;

    @Override
    public T createByObject(T object) throws DataException {
        T persistInstance;
        // Добавляем запись
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DataException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DataException(e);
        }
        // Получаем только что вставленную запись
        sql = getSelectQuery() + " WHERE id = last_insert_id();";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DataException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new DataException(e);
        }
        return persistInstance;
    }

    @Override
    public T getByPK(Integer key) throws DataException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DataException(e);
        }
        if (list == null || list.size() == 0) {
            throw new DataException("Record with PK = " + key + " not found.");
        }
        if (list.size() > 1) {
            throw new DataException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getByKey(String key, String name) throws DataException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE " + name+ " = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DataException(e);
        }
        if (list == null || list.size() == 0) {
            throw new DataException("Record with " + name +" = " + key + " not found.");
        }

        return list;
    }



    @Override
    public void update(T object) throws DataException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object); // заполнение аргументов запроса оставим на совесть потомков
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DataException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    @Override
    public void delete(T object) throws DataException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new DataException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DataException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    @Override
    public List<T> getAll() throws DataException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DataException(e);
        }
        return list;
    }

    public CrudJDBCDao(Connection connection) {
        this.connection = connection;
    }
}
