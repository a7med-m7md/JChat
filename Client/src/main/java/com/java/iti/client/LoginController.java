package com.java.iti.client;

import com.java.iti.client.utils.PhoneNumberValidator;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane signInPane;

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

    }

    private boolean validateFields() {
        boolean validationFlag = false;

        //Required Field Validation
        RequiredFieldValidator requiredNumber = new RequiredFieldValidator();
        RequiredFieldValidator requiredPassword = new RequiredFieldValidator();
        requiredNumber.setMessage("\t\t\t\t\t\t\t\t     can't be empty!");
        requiredPassword.setMessage("\t\t\t\t\t\t\t\t     can't be empty!");
        phoneNumberField.getValidators().add(requiredNumber);
        passwordField.getValidators().add(requiredPassword);
        if (!phoneNumberField.validate())
            requiredNumber.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
        if (!passwordField.validate())
            requiredPassword.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
        else validationFlag = true;

        //Phone Number Validation
        PhoneNumberValidator validNumber = new PhoneNumberValidator();
        validNumber.setMessage("\t\t\t\t\t\t\t       enter valid number");
        phoneNumberField.getValidators().add(validNumber);
        if (!phoneNumberField.validate()) {
            validNumber.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
        } else validationFlag = true;

        return validationFlag;
    }
}
