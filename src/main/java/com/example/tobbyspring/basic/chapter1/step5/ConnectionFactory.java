package com.example.tobbyspring.basic.chapter1.step5;

public class ConnectionFactory {

    public UserDao_4 userDao = new UserDao_4(nUserConnection());

    public ConnectionMaker nUserConnection() {
        return new NConnectionMaker();
    }

    public ConnectionMaker dUserConnection() {
        return new DConnectionMaker();
    }
}
