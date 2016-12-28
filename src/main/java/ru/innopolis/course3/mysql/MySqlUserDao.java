package ru.innopolis.course3.mysql;


import ru.innopolis.course3.Pojo.Role;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.CrudJDBCDao;
import ru.innopolis.course3.dao.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlUserDao extends CrudJDBCDao<User, Integer> {

    private class UserById extends User {
        public void setId(int id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT id, name, password, email, phone, role FROM programming_training.user";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO programming_training.user (name, password, email, phone, role) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE programming_training.user SET name= ? password = ? email = ? phone = ? role = ?  WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM programming_training.user WHERE id= ?;";
    }

    @Override
    public User create() throws DataException {
        User user = new User();
        return createByObject(user);
    }
    static Connection connections;
    public MySqlUserDao(Connection connection) {
        super(connection);
        connections = connection;
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DataException {
        LinkedList<User> result = new LinkedList<User>();
        try {
            while (rs.next()) {
                UserById user = new UserById();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(Role.ROLE_USER);

                result.add(user);
            }
        } catch (Exception e) {
            throw new DataException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws DataException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getPhone());
            statement.setString(5, Role.ROLE_USER.toString());
        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws DataException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getPhone());
            statement.setString(5, Role.ROLE_USER.toString());
            statement.setInt(6, object.getId());

        } catch (Exception e) {
            throw new DataException(e);
        }
    }


    public static Integer getUserId(String name, String password) throws DataException {
        Integer id = null;
        String sql = "SELECT id FROM programming_training.user where name = ? and password = ?;";
        try (PreparedStatement statement = connections.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            throw new DataException(e);
        }
        return id;
    }
}
