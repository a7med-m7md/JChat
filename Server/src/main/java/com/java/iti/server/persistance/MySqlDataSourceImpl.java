package com.java.iti.server.persistance;

import com.java.iti.server.persistance.utils.Constants;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MySqlDataSourceImpl {
    private static FileInputStream fileInputStream;
    private static MysqlDataSource mysqlDataSource;

    public static MysqlDataSource getMysqlDataSource() {
        try {
            fileInputStream = new FileInputStream("F:\\Adel\\ITI 9 Months\\Projects\\Chat App\\JTalk\\Server\\src\\main\\resources\\com\\java\\iti\\server\\properties\\db.properties");
            Properties prop = new Properties();
            prop.load(fileInputStream);
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(prop.getProperty(Constants.URL_DB));
            mysqlDataSource.setUser(prop.getProperty(Constants.USER_NAME_DB));
            mysqlDataSource.setPassword(prop.getProperty(Constants.PASSWORD_DB));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch (IOException exc){
                    exc.printStackTrace();
                }
            }
        }
        return mysqlDataSource;
    }
}
