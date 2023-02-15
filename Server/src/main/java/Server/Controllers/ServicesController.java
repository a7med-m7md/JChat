package Server.Controllers;

import Server.business.services.serversservices.Services;
import Server.network.RMIConnectionManager;
import Server.persistance.dao.UserDao;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable
        ;
import javafx.scene.chart.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.user.UserDto;
import model.user.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

public class ServicesController implements Initializable {

    int index = -1;
    @FXML
    private VBox chart;
    @FXML
    private Button online_ofline;
    @FXML
    private Button country;
    @FXML
    private Button gender;
    @FXML
    private Button onButton;
    @FXML
    private Button offButton;

    protected PieChart pieChart;
    @FXML
    private Button announcement;
    List<UserEntity> list;
    Services services;
    List<PieChart.Data> pieChartDataList;
    ObservableList<XYChart.Series<String, Double>> countryList;
    ObservableList<PieChart.Data> genderList;
    ObservableList<PieChart.Data> statusList;

    public ServicesController() {
        list = new ArrayList<>();
        UserDao userDao = new UserDao();
        list = userDao.findAll();
        services = new Services();
        pieChart = new PieChart();
    }

    public void loadCountries() {
        List<XYChart.Series<String, Double>> countriesStatistic = services.getCountriesStatistic();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Country");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Occurrence");
        countryList = FXCollections.observableArrayList(countriesStatistic);
        StackedBarChart barChart = new StackedBarChart(xAxis, yAxis);
        barChart.setTitle("Country Statistics");
        xAxis.getChildrenUnmodifiable();
        barChart.setData(countryList);
        chart.getChildren().clear();
        chart.getChildren().add(barChart);

    }

    public void loadGender() {
        pieChartDataList = services.getGenderStatistic();
        genderList = FXCollections.observableArrayList(pieChartDataList);
        pieChart.setData(genderList);
        chart.getChildren().clear();
        chart.getChildren().add(pieChart);
        genderList.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );
    }

    public void loadOnlineOfline() {
        pieChartDataList = services.getUserStatusStatistic();
        statusList = FXCollections.observableArrayList(pieChartDataList);
        pieChart.setData(statusList);
        chart.getChildren().clear();
        chart.getChildren().add(pieChart);
        statusList.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            list = new ArrayList<>();
            UserDao userDao = new UserDao();
            list = userDao.findAll();
            services = new Services();
            pieChart = new PieChart();
            if(index==0)
                loadCountries();
            else if (index == 1)
                loadGender();
            else if (index==2)
                loadOnlineOfline();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        country.setOnAction((ev) -> {
            loadCountries();
            index = 0;
        });
        gender.setOnAction((ev) -> {
            loadGender();
            index = 1;
        });
        online_ofline.setOnAction((ev) -> {
            loadOnlineOfline();
            index = 2;
        });

        announcement.setOnAction((ev) -> {
            Scene home = null;
            try {
                home = new Scene(FXMLLoader.load(getClass().getResource("/FXML/ServerAnnouncement.fxml")));
                Node node = (Node) ev.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Stage homeStage = new Stage();
                homeStage.setScene(home);
                homeStage.setResizable(true);
                homeStage.show();
                stage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        onButton.setOnAction((ev) -> {
            offButton.setDisable(false);
            onButton.setDisable(true);
            RMIConnectionManager.getInstance().startServices();
        });

        offButton.setOnAction((ev) -> {
            offButton.setDisable(true);
            onButton.setDisable(false);
            RMIConnectionManager.getInstance().disconnect();
        });


    }

}