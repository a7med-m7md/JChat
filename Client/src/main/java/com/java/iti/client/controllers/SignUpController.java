package com.java.iti.client.controllers;

import com.java.iti.client.utils.PasswordValidator;
import com.java.iti.client.utils.PhoneNumberValidator;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    void handleNextBtn(MouseEvent event) {
        if (validateFields())
            System.out.println("valid");
        else System.out.println("invalid");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            Parent signUpPane2 = FXMLLoader.load(getClass().getResource("/FXML/sign-up-2.fxml"));
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
        PasswordValidator securePassword = new PasswordValidator();
        securePassword.setMessage("Weak Password!");
        signUpPasswordField.getValidators().add(securePassword);

        //Password Confirmation Validation
        PasswordValidator confirmPassword = new PasswordValidator(signUpPasswordField.getText());
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