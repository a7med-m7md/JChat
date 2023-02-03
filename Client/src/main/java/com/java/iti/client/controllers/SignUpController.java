package com.java.iti.client.controllers;

import com.java.iti.client.serverconnection.*;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private JFXTextField signUpPhoneNumberField;

    @FXML
    private JFXTextField signUpPasswordField;

    @FXML
    private JFXTextField signUpConfirmPasswordField;

    @FXML
    private Button signUpNextBtn1;
//    private Parent signUpPane2;

    @FXML
    void handleNextBtn(MouseEvent event) throws IOException {
        if (validateFields()) {
            Parent signUpPane2 = FXMLLoader.load(getClass().getResource("/FXML/sign-up-2.fxml"));
            Scene currentScene = signUpNextBtn1.getScene();
            signUpPane2.translateXProperty().set(currentScene.getWidth());

            signInPane.getChildren().add(signUpPane2);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(signUpPane2.translateXProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(Duration.seconds(.7), kv);
            timeline.getKeyFrames().add(kf);
//            timeline.setOnFinished(t -> {
//                LoginController.s.getChildren().remove(signUpPane2);
//            });
            timeline.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//        try {
//            signUpPane2 = FXMLLoader.load(getClass().getResource("/FXML/sign-up-2.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//            Parent signUpPane3 = FXMLLoader.load(getClass().getResource("/FXML/sign-up-3.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    private boolean validateFields() {
        boolean validationFlag = true;

        //Required Field Validation
        RequiredFieldValidator requiredField = new RequiredFieldValidator();
        requiredField.setMessage("Required Field");
        signUpPhoneNumberField.getValidators().add(requiredField);
        signUpPasswordField.getValidators().add(requiredField);
        signUpConfirmPasswordField.getValidators().add(requiredField);

        //Phone Number Validation
        PhoneNumberValidator validNumber = new PhoneNumberValidator();
        validNumber.setMessage("Enter a 11 Digit Valid Phone Number");
        signUpPhoneNumberField.getValidators().add(validNumber);

        //Password security Validation
        PasswordAndConfirmPasswordValidator securePassword = new PasswordAndConfirmPasswordValidator(signUpPasswordField);
        securePassword.setMessage("Weak Password!");
        signUpPasswordField.getValidators().add(securePassword);

        //Password Confirmation Validation
        PasswordAndConfirmPasswordValidator confirmPassword = new PasswordAndConfirmPasswordValidator(signUpPasswordField);
        confirmPassword.setMessage("Password Doesn't Match");
        signUpConfirmPasswordField.getValidators().add(confirmPassword);

        //Checking Fields
        if (!signUpPhoneNumberField.validate())
            validationFlag = false;
        if (!signUpPasswordField.validate())
            validationFlag = false;
        if (!signUpConfirmPasswordField.validate())
            validationFlag = false;
        return validationFlag;
    }

}