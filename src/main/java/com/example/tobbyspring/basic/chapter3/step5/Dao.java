package com.example.tobbyspring.basic.chapter3.step5;

import com.example.tobbyspring.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class Dao {

    private final JdbcTemplate jdbcTemplate;

    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) throws SQLException {
        this.jdbcTemplate.update("insert into users(name, password) values(?,?)", user.getName(), user.getPassword());
    }

    public User get(Long id) throws SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));

            return user;
        });
    }

    public int update(User user) {
        return this.jdbcTemplate.update("update users set name = ?, password = ? where id = ?", user.getName(), user.getPassword(), user.getId());
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }
}
