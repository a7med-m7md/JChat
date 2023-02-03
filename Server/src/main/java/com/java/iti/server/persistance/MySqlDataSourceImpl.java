package com.java.iti.persistance;

import com.java.iti.persistance.utils.Constants;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MySqlDataSourceImpl {
    private static InputStream inputStream;
    private static MysqlDataSource mysqlDataSource;

    public static MysqlDataSource getMysqlDataSource() {
        try {
            Properties prop = new Properties();
            inputStream = MySqlDataSourceImpl.class.getClassLoader().getResourceAsStream("configuration/db.properties");
            prop.load(inputStream);
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(prop.getProperty(Constants.URL_DB));
            mysqlDataSource.setUser(prop.getProperty(Constants.USER_NAME_DB));
            mysqlDataSource.setPassword(prop.getProperty(Constants.PASSWORD_DB));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try{
                    inputStream.close();
                }catch (IOException exc){
                    exc.printStackTrace();
                }
            }
        }
        return mysqlDataSource;
    }
}
