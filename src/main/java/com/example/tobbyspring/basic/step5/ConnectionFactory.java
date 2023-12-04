package com.example.tobbyspring.basic.step5;

public class ConnectionFactory {

    public ConnectionMaker nUserConnection() {
        return new NConnectionMaker();
    }

    public ConnectionMaker dUserConnection() {
        return new DConnectionMaker();
    }
}
