package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.models.CurrentUserAccount;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.user.UserStatus;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrentUserCardController implements Initializable {
    @FXML
    private Label currentUserDisplayName;
    @FXML
    private Label currentUserBio;

    @FXML
    private Circle currentUserAvatar;

    @FXML
    private ComboBox<String> userStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
        ObservableList<String> statuses =
                FXCollections.observableArrayList(
                        Stream.of(UserStatus.values())
                                .map(UserStatus::getStatusName)
                                .collect(Collectors.toList())
                );
        userStatus.setItems(statuses);
        userStatus.setValue(currentUserAccount.getStatus().getStatusName());

        //Binding current user data from db to the Current User Card in GUI


        currentUserAccount.statusProperty().bind(Bindings.createObjectBinding(() -> {
            String selectedStatus = userStatus.getValue();
            return UserStatus.getStatus(selectedStatus);
        }, userStatus.valueProperty()));

        currentUserDisplayName.textProperty().bind(currentUserAccount.nameProperty());
        currentUserBio.textProperty().bind(currentUserAccount.bioProperty());

        currentUserAvatar.fillProperty().bind(Bindings.createObjectBinding(() -> {
            return currentUserAccount == null ? null : new ImagePattern(currentUserAccount.getImage());
        }));

        currentUserAvatar.strokeProperty().bind(Bindings.createObjectBinding(() -> {
            String selectedStatus = userStatus.getValue();
            UserStatus userStatus = UserStatus.getStatus(selectedStatus);
            if (userStatus == null) {
                return Color.WHITE;
            }
            return userStatus.getColor();
        }, userStatus.valueProperty()));

        userStatus.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            try {
                RMIClientServices.tellMyStatus(currentUserAccount.getMobile(),UserStatus.getStatus(userStatus.getValue()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });


    }
}