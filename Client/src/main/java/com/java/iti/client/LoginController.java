package com.java.iti.client;

import com.java.iti.client.utils.PhoneNumberValidator;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private JFXTextField phoneNumberField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private Button signInBtn;


    @FXML
    void handleSignIn(MouseEvent event) {
        if (validateFields()) {
            /*
            //Transition
            // To
            // User
            // Home Screen chats
            // Here
             */
            System.out.println("Valid");
        } else System.out.println("not valid");
    }


    @FXML
    void handleSignUp(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXML/sign-up.fxml"));
            Scene scene = signInBtn.getScene();
            root.translateXProperty().set(scene.getWidth());

            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(Duration.seconds(.7), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(anchorRoot);
            });
            timeline.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean validateFields() {
        boolean validationFlag = true;

        //Required Field Validation
        RequiredFieldValidator requiredPassword = new RequiredFieldValidator();
        requiredPassword.setMessage("\t\t\t\t\t\t\t\t\t can't be empty");
        passwordField.getValidators().add(requiredPassword);

        //Phone Number Validation
        PhoneNumberValidator validNumber = new PhoneNumberValidator();
        validNumber.setMessage("\t\t\t\t\t\t\t\t   enter valid number");
        phoneNumberField.getValidators().add(validNumber);

        //Checking Fields
        if (!phoneNumberField.validate()) {
            validationFlag = false;
        }
        if (!passwordField.validate()) {
//            requiredPassword.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
            validationFlag = false;
        }
        return validationFlag;
    }

}
