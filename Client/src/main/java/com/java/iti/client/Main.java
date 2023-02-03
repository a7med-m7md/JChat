package com.java.iti.client;

import com.java.iti.client.business.dtos.UserDto;
import com.java.iti.client.business.mappers.UseMapperImpl;
import com.java.iti.client.business.mappers.UserMapper;
import com.java.iti.client.model.user.Gender;
import com.java.iti.client.model.user.User;
import com.java.iti.client.model.user.UserStatus;
import com.java.iti.client.repository.entities.UserEntity;
import com.java.iti.client.repository.userDao.UserDao;
import com.java.iti.utils.RMIConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JChat");
//        RMIConnection.connect();

//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}
