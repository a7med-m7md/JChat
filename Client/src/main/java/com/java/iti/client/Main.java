package com.java.iti.client;

import com.java.iti.client.repository.GroupDao.GroupDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

//        Group newGroup = new Group("JETS", "Hello, JETS", 1);


        GroupDao groupDao = new GroupDao();
//        groupDao.save(newGroup);
//        Optional<Group> g2 = groupDao.findById(2);
//        g2.ifPresent((e)-> System.out.println("BY ID: " + e.getName()));
        groupDao.findAll().forEach(g->{
            System.out.println("Name :: " + g.getName());
            System.out.println("DES :: " + g.getDescription());
            System.out.println("Owner :: " + g.getOwner_id());
        });

        launch();

    }
}