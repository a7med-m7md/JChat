package Client.ui.controllers;

import Client.ui.components.ErrorMessageUi;
import Client.ui.controllerutils.Country;
import Client.ui.controllerutils.EmailValidator;
import Client.ui.models.CurrentUserAccount;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.user.Gender;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateTwoController implements Initializable {

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
    private VBox errorContainer;

    @FXML
    void handleNextBtn(MouseEvent event) throws IOException {
        if (!validateFields()) {
            errorContainer.getChildren().setAll(new ErrorMessageUi("Please fill all fields", true));
        } else {
            CurrentUserAccount newUserAccount = CurrentUserAccount.getInstance();
            newUserAccount.setEmail(signUpEmailField.getText());
            newUserAccount.setCountry(countryComboBox.getValue());
            newUserAccount.setGender(Gender.getGender(genderComboBox.getValue()));

            //converting LocalDate from datepicker to String
            LocalDate localDate = dateOfBirth.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = localDate.format(formatter);
            newUserAccount.setDateOfBirth(dateString);

            //Switching to next step screen
            Parent signUpPane3 = FXMLLoader.load(getClass().getResource("/FXML/update-account-3.fxml"));
            Scene currentScene = signUpEmailField.getScene();
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
        if (!signUpEmailField.validate())
            validationFlag = false;
        if (genderComboBox.getValue() == null)
            validationFlag = false;
        if (countryComboBox.getValue() == null)
            validationFlag = false;
//        if (dateOfBirth.getValue() == null)
//            validationFlag = false;
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
        countryComboBox.setValue(Country.EGYPT.getCountry());

        ObservableList<String> genderList =
                FXCollections.observableArrayList(
                        Stream.of(Gender.values())
                                .map(Gender::getGenderName)
                                .collect(Collectors.toList())
                );
        genderComboBox.setItems(genderList);
        signUpEmailField.setText(CurrentUserAccount.getInstance().getEmail());
        countryComboBox.setValue(CurrentUserAccount.getInstance().getCountry());
        genderComboBox.setValue(CurrentUserAccount.getInstance().getGender().getGenderName());
        String birthDate = CurrentUserAccount.getInstance().getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(birthDate, formatter);
        dateOfBirth.setValue(date);
    }
}
