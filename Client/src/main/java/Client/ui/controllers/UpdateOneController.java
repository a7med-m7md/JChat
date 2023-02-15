package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ErrorMessageUi;
import Client.ui.controllerutils.PasswordAndConfirmPasswordValidator;
import Client.ui.controllerutils.PasswordFieldSkin;
import Client.ui.controllerutils.PhoneNumberValidator;
import Client.ui.models.CurrentUserAccount;
import com.jfoenix.controls.JFXTextField;
import exceptions.DuplicateUserException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateOneController implements Initializable {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private VBox errorContainer;

    @FXML
    private JFXTextField signUpPhoneNumberField;

    @FXML
    private JFXTextField signUpPasswordField;

    @FXML
    private JFXTextField signUpConfirmPasswordField;

    @FXML
    private Button signUpNextBtn1;

    @FXML
    private VBox errorMessageContainer;

    //    private Parent signUpPane2;

    @FXML
    void handleNextBtn(MouseEvent event) throws IOException {
        if (!signUpConfirmPasswordField.validate() && !signUpPasswordField.validate())
            errorMessageContainer.getChildren().setAll(new ErrorMessageUi("Please, Enter valid fields", true));
        else {

                //After checking all the data is valid and phone number is unique
                //populate currentUserModel with the entered data
                CurrentUserAccount newUserAccount = CurrentUserAccount.getInstance();
                newUserAccount.setPhoneNumber(signUpPhoneNumberField.getText());
                newUserAccount.setPassword(signUpConfirmPasswordField.getText());

                //switch to step 2
                Parent signUpPane2 = FXMLLoader.load(getClass().getResource("/FXML/update-account-2.fxml"));
                Scene currentScene = signUpNextBtn1.getScene();
                signUpPane2.translateXProperty().set(currentScene.getWidth());
                signInPane.getChildren().add(signUpPane2);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(signUpPane2.translateXProperty(), 0, Interpolator.EASE_BOTH);
                KeyFrame kf = new KeyFrame(Duration.seconds(.7), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        signUpPasswordField.setSkin(new PasswordFieldSkin(signUpPasswordField));
//        signUpConfirmPasswordField.setSkin(new PasswordFieldSkin(signUpPasswordField));
        validateFields();
        signUpPhoneNumberField.setText(CurrentUserAccount.getInstance().getMobile());
        signUpConfirmPasswordField.setEditable(true);
        signUpPhoneNumberField.setEditable(false);

    }

    private void validateFields() {


        PasswordAndConfirmPasswordValidator passwordValidator = new PasswordAndConfirmPasswordValidator(signUpPasswordField);
        passwordValidator.setMessage("Enter a valid password");
        signUpPasswordField.getValidators().add(passwordValidator);

        PasswordAndConfirmPasswordValidator confirmPasswordValidator = new PasswordAndConfirmPasswordValidator(signUpPasswordField);
        confirmPasswordValidator.setMessage("Passwords doesn't match");
        signUpConfirmPasswordField.getValidators().add(confirmPasswordValidator);

        signUpPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            signUpPasswordField.validate();
        });

        signUpConfirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            signUpConfirmPasswordField.validate();
        });

    }


}