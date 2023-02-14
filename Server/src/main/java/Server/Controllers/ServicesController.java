package Server.Controllers;

import Server.business.services.serversservices.Services;
import Server.network.RMIConnectionManager;
import Server.persistance.dao.UserDao;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable
        ;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.user.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {

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

    public ServicesController() {
        list = new ArrayList<>();
        UserDao userDao = new UserDao();
        list = userDao.findAll();
        services = new Services();
        pieChart = new PieChart();
    }

    public void loadCountries() {
        List<XYChart.Series<String, Double>> countriesStatistic = services.getCountriesStatistic(list);
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        ObservableList<XYChart.Series<String, Double>> countryList = FXCollections.observableArrayList(countriesStatistic);
        BarChart barChart = new BarChart<>(xAxis, yAxis);
        barChart.setData(countryList);
        chart.getChildren().clear();
        chart.getChildren().add(barChart);
    }

    public void loadGender() {
        pieChartDataList = services.getGenderStatistic(list);
        ObservableList<PieChart.Data> genderList = FXCollections.observableArrayList(pieChartDataList);
        pieChart.setData(genderList);
        chart.getChildren().clear();
        chart.getChildren().add(pieChart);
        genderList.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), String.format("%.2f", data.getPieValue())
                        )
                )
        );
    }

    public void loadOnlineOfline() {
        pieChartDataList = services.getUserStatusStatistic(list);
        ObservableList<PieChart.Data> statusList = FXCollections.observableArrayList(pieChartDataList);
        pieChart.setData(statusList);
        chart.getChildren().clear();
        chart.getChildren().add(pieChart);
        statusList.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), String.format("%.2f", data.getPieValue())
                        )
                )
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        country.setOnAction((ev) -> {
            loadCountries();
        });
        gender.setOnAction((ev) -> {
            loadGender();
        });
        online_ofline.setOnAction((ev) -> {
            loadOnlineOfline();
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