package com.java.iti.server;

import com.java.iti.model.user.User;
import com.java.iti.persistance.utils.Country;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.java.iti.model.user.Gender.MALE;
import static com.java.iti.model.user.UserStatus.AVAILABLE;

public class HelloApplication extends Application {
    List<PieChart.Data> list = new ArrayList<>();

    List<User> users = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        getList();
        PieChart countries = new PieChart();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (User user : users) {
            Integer j = hm.get(user.getCountry().getUrl());
            hm.put(user.getCountry().getUrl(), (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
            list.add(new PieChart.Data(val.getKey(), val.getValue().doubleValue()));
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(list);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), String.format("%.2f",data.getPieValue())
                        )
                )
        );
        countries.setData(pieChartData);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(countries);
        stage.setTitle("Hello!");
        Scene scene = new Scene(stackPane, 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void getList() {
        users.add(new User("", "", "", "", "", MALE, Country.AFGHANISTAN, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.AFGHANISTAN, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ALBANIA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ALBANIA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ALBANIA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ALBANIA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ANGOLA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ANGOLA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ANGOLA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ANGOLA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ARGENTINA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.ARGENTINA, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.BAHAMAS, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.BAHAMAS, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.BAHAMAS, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.EGYPT, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.EGYPT, "", "", AVAILABLE));
        users.add(new User("", "", "", "", "", MALE, Country.EGYPT, "", "", AVAILABLE));
    }
}

