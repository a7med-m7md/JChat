package Client.ui.controllers;

import Client.ui.controllerutils.Country;
import Client.ui.models.CurrentUserAccount;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import model.user.Gender;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountInfoController implements Initializable {


    @FXML
    private JFXTextField displayNameField;

    @FXML
    private JFXTextField accountEmailField;

    @FXML
    private JFXComboBox<String> countryComboBox;

    @FXML
    private JFXComboBox<Gender> genderComboBox;

    @FXML
    private DatePicker dateOfBirth;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initializing country combobox
        ObservableList<String> countryList =
                FXCollections.observableArrayList(
                        Stream.of(Country.values())
                                .map(Country::getCountry)
                                .collect(Collectors.toList())
                );
        ObservableList<Gender> genders =
                FXCollections.observableArrayList(
                        Stream.of(Gender.values())
                                .collect(Collectors.toList())
                );
        countryComboBox.setItems(countryList);
        genderComboBox.setItems(genders);

        //populating fields with data
        CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();

        //TODO Bind Avatar
        displayNameField.textProperty().bindBidirectional(currentUserAccount.nameProperty());
        accountEmailField.textProperty().bindBidirectional(currentUserAccount.emailProperty());
        countryComboBox.valueProperty().bindBidirectional(currentUserAccount.countryProperty());
        genderComboBox.valueProperty().bindBidirectional(currentUserAccount.genderProperty());
        //TODO Make dateofbirth localdate
//        dateOfBirth.valueProperty().bindBidirectional(currentUserAccount.dateOfBirthProperty());

    }
}