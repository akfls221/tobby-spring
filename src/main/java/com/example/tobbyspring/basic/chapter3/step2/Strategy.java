package com.example.tobbyspring.basic.chapter3.step2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Strategy {

    PreparedStatement makePreparedStatement(Connection connection) throws SQLException;
}
