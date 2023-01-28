package com.java.iti.client.utils;

// import java.sql.Connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static ConnectionManager instance;
    private  Connection connection;

    private ConnectionManager() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/configuration/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("password");
        try {
            this.connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
    public void close(){
        if(this.connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionManager getInstance(){
        try {
            if(instance == null || instance.connection.isClosed()){
                synchronized (ConnectionManager.class){
                    if(instance == null || instance.connection.isClosed()){
                        instance = new ConnectionManager();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
