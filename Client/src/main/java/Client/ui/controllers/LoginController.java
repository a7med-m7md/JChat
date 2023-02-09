package Client.ui.controllers;


import Client.network.RMIConnection;
import Client.ui.controllerutils.PhoneNumberValidator;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.UserNotFoundException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane parentContainer;

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
        if (validateFields()) {
            try {
                RMIConnection.logIn(phoneNumberField.getText(), passwordField.getText());
                //todo populate current user model with phone number
                Scene home = new Scene(FXMLLoader.load(getClass().getResource("/FXML/main.fxml")));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Stage homeStage = new Stage();
                homeStage.setScene(home);
                homeStage.setResizable(true);
                homeStage.show();
                stage.close();

            } catch (UserNotFoundException e) {
                System.out.println("wrong credintials");
                //todo implement usernotfound gui error
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            /*
            //Transition
            // To
            // User
            // Home Screen chats
            // Here
             */

            System.out.println("Valid");

        } else System.out.println("not valid fields");
    }


    @FXML
    void handleSignUp(MouseEvent event) {
        try {
            Parent signUpPane = FXMLLoader.load(getClass().getResource("/FXML/sign-up-1.fxml"));
            Scene currentScene = signInBtn.getScene();
            signUpPane.translateXProperty().set(currentScene.getWidth());

            parentContainer.getChildren().add(signUpPane);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(signUpPane.translateXProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(Duration.seconds(.7), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(signInPane);
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
        requiredPassword.setMessage("Password can't be empty");
        passwordField.getValidators().add(requiredPassword);

        //Phone Number Validation
        PhoneNumberValidator validNumber = new PhoneNumberValidator();
        validNumber.setMessage("Enter a valid phone number");
        phoneNumberField.getValidators().add(validNumber);

        //Checking Fields
        if (!phoneNumberField.validate()) {
            validationFlag = false;
//            validNumber.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
        }
        if (!passwordField.validate()) {
//            requiredPassword.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
            validationFlag = false;
        }
        return validationFlag;
    }

}