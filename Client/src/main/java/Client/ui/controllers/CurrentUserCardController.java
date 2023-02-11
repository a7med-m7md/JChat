package Client.ui.controllers;

import Client.ui.models.CurrentUserAccount;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.user.UserStatus;

import java.net.URL;
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
        ObservableList<String> statuses =
                FXCollections.observableArrayList(
                        Stream.of(UserStatus.values())
                                .map(UserStatus::getStatusName)
                                .collect(Collectors.toList())
                );
        userStatus.setItems(statuses);

        //Binding current user data from db to the Current User Card in GUI
        CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();

        userStatus.valueProperty().bindBidirectional(currentUserAccount.statusProperty());
        currentUserDisplayName.textProperty().bind(currentUserAccount.nameProperty());
        currentUserBio.textProperty().bind(currentUserAccount.bioProperty());

        currentUserAvatar.fillProperty().bind(Bindings.createObjectBinding(() -> {
            return currentUserAccount == null ? null : new ImagePattern(currentUserAccount.getPicture());
        }));

        currentUserAvatar.strokeProperty().bind(Bindings.createObjectBinding(() -> {
            return currentUserAccount.getStatus().getColor();
        }));


    }
}