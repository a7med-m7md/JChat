package com.java.iti.client;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField phoneNumberField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private Button signInBtn;


    @FXML
    void handleSignIn(MouseEvent event) {

    }

    @FXML
    void handleSignUp(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //RequiredField
        RequiredFieldValidator requiredField = new RequiredFieldValidator();
        requiredField.setMessage("Required Field");
        phoneNumberField.getValidators().add(requiredField);
        phoneNumberField.focusedProperty().addListener((o, oldval, newval) -> {
            if (!newval) phoneNumberField.validate();
        });
        passwordField.getValidators().add(requiredField);
        passwordField.focusedProperty().addListener((o, oldval, newval) -> {
            if (!newval) passwordField.validate();
            requiredField.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
        });


        //NumberField Only
        NumberValidator requiredNumber = new NumberValidator();
        requiredNumber.setMessage("enter a proper phone number");
        phoneNumberField.getValidators().add(requiredNumber);
        phoneNumberField.focusedProperty().addListener((o, oldval, newval) -> {
            if (!newval) phoneNumberField.validate();
            requiredNumber.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
        });

    }
}
