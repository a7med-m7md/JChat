package Server.business.services.serversservices;

import javafx.scene.chart.PieChart;
import model.user.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Services {

    public List<PieChart.Data> getCountriesStatistic(List<UserEntity> users) {
        System.out.println("hm.size()");
        List<PieChart.Data> list = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (UserEntity user : users) {
            Integer j = hm.get(user.getCountry());
            hm.put(user.getCountry(), (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
            list.add(new PieChart.Data(val.getKey(), val.getValue().doubleValue()));
        }
        return list;
    }

    public List<PieChart.Data> getGenderStatistic(List<UserEntity> users) {
        List<PieChart.Data> list = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (UserEntity user : users) {
            Integer j = hm.get(user.getGender());
            hm.put(user.getCountry(), (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
            list.add(new PieChart.Data(val.getKey(), val.getValue().doubleValue()));
        }
        return list;
    }

    public List<PieChart.Data> getUserStatusStatistic(List<UserEntity> users) {
        System.out.println("hm.size()");
        List<PieChart.Data> list = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (UserEntity user : users) {
            Integer j = hm.get(user.getStatus());
            hm.put(user.getCountry(), (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
            list.add(new PieChart.Data(val.getKey(), val.getValue().doubleValue()));
        }
        return list;
    }

}
