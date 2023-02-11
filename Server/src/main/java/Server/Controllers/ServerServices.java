package Server.Controllers;

import Server.business.services.serversservices.Services;
import Server.persistance.dao.UserDao;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.user.Gender;
import model.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ServerServices extends AnchorPane {

    protected final VBox vBox;
    protected final ImageView imageView;
    protected final Label label;
    protected final Button button;
    protected final Button button0;
    protected final Button button1;
    protected final VBox vBox0;
    protected final PieChart pieChart;

    public ServerServices() {

        vBox = new VBox();
        imageView = new ImageView();
        label = new Label();
        button = new Button();
        button0 = new Button();
        button1 = new Button();
        vBox0 = new VBox();
        pieChart = new PieChart();
        List<UserEntity> list = new ArrayList<>();
        UserDao userDao = new UserDao();
        list = userDao.findAll();
        Services services = new Services();
        List<PieChart.Data> pieChartDataList = services.getCountriesStatistic(list);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pieChartDataList);
        pieChart.setData(pieChartData);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), String.format("%.2f", data.getPieValue())
                        )
                )
        );

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(600.0);
        setPrefWidth(900.0);

        vBox.setLayoutX(4.0);
        vBox.setLayoutY(-3.0);
        vBox.setPrefHeight(600.0);
        vBox.setPrefWidth(450.0);

        imageView.setFitHeight(600.0);
        imageView.setFitWidth(450.0);
        imageView.setImage(new Image(getClass().getResource("/images/hero.jpg").toExternalForm()));

        label.setLayoutX(577.0);
        label.setLayoutY(84.0);
        label.setText("Server Servicess");
        label.setFont(new Font("Segoe UI Black", 25.0));

        button.setLayoutX(469.0);
        button.setLayoutY(176.0);
        button.setStyle("-fx-background-color: #39CEFF;");
        button.setText("OnLine and OfLine User");
        button.setTextFill(javafx.scene.paint.Color.valueOf("#fafafa"));

        button0.setLayoutX(757.0);
        button0.setLayoutY(176.0);
        button0.setStyle("-fx-background-color: #39CEFF;");
        button0.getStyleClass().add("Primary-Button");
        button0.setText("User's Gender");
        button0.setTextFill(javafx.scene.paint.Color.valueOf("#fafafa"));

        button1.setLayoutX(639.0);
        button1.setLayoutY(177.0);
        button1.setStyle("-fx-background-color: #39CEFF;");
        button1.getStyleClass().add("Primary-Button");
        button1.setText("user's Country");
        button1.setTextFill(javafx.scene.paint.Color.valueOf("#fafafa"));

        vBox0.setLayoutX(464.0);
        vBox0.setLayoutY(231.0);
        vBox0.setPrefHeight(359.0);
        vBox0.setPrefWidth(429.0);

        vBox.getChildren().add(imageView);
        getChildren().add(vBox);
        getChildren().add(label);
        getChildren().add(button);
        getChildren().add(button0);
        getChildren().add(button1);
        vBox0.getChildren().add(pieChart);
        getChildren().add(vBox0);


    }
}
