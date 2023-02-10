package Client.ui.controllers;

import Client.ui.controllerutils.Country;
import Client.ui.controllerutils.EmailValidator;
import Client.ui.controllerutils.PasswordAndConfirmPasswordValidator;
import Client.ui.controllerutils.PhoneNumberValidator;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SignUp2Controller implements Initializable {

    @FXML
    private AnchorPane signUp2Pane;

    @FXML
    private JFXTextField signUpEmailField;

    @FXML
    private JFXComboBox<String> countryComboBox;

    @FXML
    private JFXComboBox<String> genderComboBox;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Button signUpNextBtn2;


    @FXML
    void handleNextBtn(MouseEvent event) throws IOException {
        if (validateFields()) {
            Parent signUpPane3 = FXMLLoader.load(getClass().getResource("/FXML/sign-up-3.fxml"));
            Scene currentScene = signUpNextBtn2.getScene();
            signUpPane3.translateXProperty().set(currentScene.getWidth());

            signUp2Pane.getChildren().add(signUpPane3);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(signUpPane3.translateXProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(Duration.seconds(.7), kv);
            timeline.getKeyFrames().add(kf);
//            timeline.setOnFinished(t -> {
//                LoginController.s.getChildren().remove(signUpPane2);
//            });
            timeline.play();
        }
    }

    private boolean validateFields() {
        boolean validationFlag = true;

        //Email Field Validation
        EmailValidator emailFieldValidaton = new EmailValidator();
        emailFieldValidaton.setMessage("Enter a valid email address");
        signUpEmailField.getValidators().add(emailFieldValidaton);

        //Required Field Validators
        RequiredFieldValidator genderValidation = new RequiredFieldValidator();
        genderValidation.setMessage("choose a gender");

        //Checking Fields
        if (!signUpEmailField.validate())
            validationFlag = false;
        return validationFlag;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countryList =
                FXCollections.observableArrayList(
                        Stream.of(Country.values())
                                .map(Country::getCountry)
                                .collect(Collectors.toList())
                );
        countryComboBox.setItems(countryList);
        countryComboBox.setValue(Country.EGYPT.toString());
    }
}
