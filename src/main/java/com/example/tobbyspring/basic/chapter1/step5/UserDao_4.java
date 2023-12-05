package com.example.tobbyspring.basic.chapter1.step5;

import com.example.tobbyspring.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao_4 {
    private ConnectionMaker connectionMaker;

    public UserDao_4(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id, name, password) values(?,?,?)");

        preparedStatement.setLong(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String id) throws SQLException, ClassNotFoundException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");

        preparedStatement.setString(1, id);

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
}
