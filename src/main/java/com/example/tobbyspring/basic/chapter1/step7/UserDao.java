package com.example.tobbyspring.basic.chapter1.step7;

import com.example.tobbyspring.entity.User;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name, password) values(?,?)");

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User findUser = new User();
        findUser.setId(resultSet.getLong("id"));
        findUser.setName(resultSet.getString("name"));
        findUser.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return findUser;
    }

    public int update(User user) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ?, password = ? where id = ?");

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setLong(3, user.getId());

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return result;
    }

    public int userCount() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from users");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int result = resultSet.getInt(1);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return result;
    }

    public void deleteAll() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("delete from users");

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
