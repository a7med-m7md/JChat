package com.java.iti;


import com.java.iti.business.dtos.UserDto;
import com.java.iti.business.mappers.UseMapperImpl;
import com.java.iti.business.mappers.UserMapper;
import com.java.iti.model.user.Gender;
import com.java.iti.model.user.UserStatus;
import com.java.iti.persistance.ConnectionManager;
import com.java.iti.repository.entities.UserEntity;
import com.java.iti.repository.userDao.UserDao;
import com.java.iti.utils.RMIConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    Connection connection;
    @Override
    public void start(Stage stage) throws IOException {
//        RMIConnection.connect();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("JChat Server!");
        ConnectionManager.getInstance().getConnection();

        UserDto userDto = new UserDto(
                "02211014",
                "mohamed mahmoud",
                "mohamed99@gmail",
                "picture str path",
                "1234password",
                Gender.MALE,
                "egypt",
                "1999-02-20",
                "software engineer",
                UserStatus.AVAILABLE
        );
        //TODO map from domain user to user entity
        UserDao userDao = new UserDao();
        UserMapper userMapper = new UseMapperImpl();
        UserEntity res = userDao.save(userMapper.domainToEntity(userDto));
        System.out.println(res.getStatus().name());


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}