package ru.innopolis.course3.mysql;


import ru.innopolis.course3.Pojo.Role;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.CrudJDBCDao;
import ru.innopolis.course3.dao.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
/**
 * Реализациия класса тем на mysql
 */
public class MySqlSubjectDao extends CrudJDBCDao<Subject, Integer> {



    private class SubjectById extends Subject {
        public void setId(int id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT id, name, description FROM programming_training.subject";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO programming_training.subject (name, description) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `programming_training`.`subject` SET `name`= ?, `description`= ? WHERE `id`= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM programming_training.subject WHERE id= ?;";
    }

    @Override
    public Subject create() throws DataException {
        Subject subject = new Subject();
        return createByObject(subject);
    }

    public MySqlSubjectDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Subject> parseResultSet(ResultSet rs) throws DataException {
        LinkedList<Subject> result = new LinkedList<Subject>();
        try {
            while (rs.next()) {
                SubjectById subject = new SubjectById();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                result.add(subject);
            }
        } catch (Exception e) {
            throw new DataException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Subject object) throws DataException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());

        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Subject object) throws DataException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getId());

        } catch (Exception e) {
            throw new DataException(e);
        }
    }
}
