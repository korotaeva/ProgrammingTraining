package ru.innopolis.course3.mysql;

import ru.innopolis.course3.Pojo.PracticalAssignments;
import ru.innopolis.course3.Pojo.Subject;
import ru.innopolis.course3.dao.CrudJDBCDao;
import ru.innopolis.course3.dao.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlPracticalDao extends CrudJDBCDao<PracticalAssignments, Integer> {

    private class PracticalById extends PracticalAssignments {
        public void setId(int id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT id, name, description,subject FROM programming_training.practical_assignments";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO programming_training.practical_assignments (name, description,subject) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE programming_training.practical_assignments SET `name`= ?, `description`= ?, subject = ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM programming_training.practical_assignments WHERE id= ?;";
    }

    @Override
    public PracticalAssignments create() throws DataException {
        PracticalAssignments PracticalAssignments = new PracticalAssignments();
        return createByObject(PracticalAssignments);
    }

    public MySqlPracticalDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<PracticalAssignments> parseResultSet(ResultSet rs) throws DataException {
        LinkedList<PracticalAssignments> result = new LinkedList<PracticalAssignments>();
        try {
            while (rs.next()) {

                PracticalById practicalById = new PracticalById();
                practicalById.setId(rs.getInt("id"));
                practicalById.setName(rs.getString("name"));
                practicalById.setDescription(rs.getString("description"));
                Subject subject = new Subject();
                subject.setId(rs.getInt("subject"));
                practicalById.setSubject(subject);
                //subject
                result.add(practicalById);
            }
        } catch (Exception e) {
            throw new DataException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, PracticalAssignments object) throws DataException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getSubject().getId());
        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, PracticalAssignments object) throws DataException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getSubject().getId());
            statement.setInt(4, object.getId());

        } catch (Exception e) {
            throw new DataException(e);
        }
    }
}
