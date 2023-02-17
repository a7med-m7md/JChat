package Server.business.services.serversservices;

import Server.persistance.dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import model.user.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Services {

    UserDao userDao = new UserDao();
    List<UserEntity> users = userDao.findAll();
    public ObservableList<UserEntity> userEntities = FXCollections.observableArrayList(users);

    public List<XYChart.Series<String, Double>> getCountriesStatistic() {
        List<XYChart.Series<String, Double>> series1 = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (UserEntity user : users) {
            Integer j = hm.get(user.getCountry());
            hm.put(user.getCountry(), (j == null) ? 1 : j + 1);
        }
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(val.getKey().toString(), val.getValue().doubleValue()));
            series1.add(series);
        }
        return series1;
    }

    public List<PieChart.Data> getGenderStatistic() {
        List<PieChart.Data> list = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (UserEntity user : users) {
            Integer j = hm.get(user.getGender().getGenderName());
            hm.put(user.getGender().getGenderName(), (j == null) ? 1 : j + 1);
        }
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            list.add(new PieChart.Data(val.getKey(), val.getValue().intValue()));
        }
        return list;
    }

    public List<PieChart.Data> getUserStatusStatistic() {
        List<PieChart.Data> list = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (UserEntity user : users) {
            Integer j = hm.get(user.getStatus().name());
            hm.put(user.getStatus().name(), (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            list.add(new PieChart.Data(val.getKey(), val.getValue().intValue()));
        }
        return list;
    }

}