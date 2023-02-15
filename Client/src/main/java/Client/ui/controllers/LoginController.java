package Client.ui.controllers;


import Client.Hashing.EncryptionUtil;
import Client.network.RMIClientServices;
import Client.ui.components.ErrorMessageUi;
import Client.ui.models.CurrentUserAccount;
import model.LoginEntity;
import model.user.UserEntity;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.user.UserStatus;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private VBox errorMessageContainer;

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
//        if (validateFields()) {
            try {
                populateLoggedInUserData();

                MainController mainController = MainController.getInstance();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/main.fxml"));
                loader.setController(mainController);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Stage homeStage = new Stage();
                homeStage.setScene(scene);

                homeStage.setResizable(true);
                homeStage.show();
                stage.close();

            } catch (UserNotFoundException e) {
                errorMessageContainer.getChildren().setAll(new ErrorMessageUi("Wrong phone number or password",true));
                System.out.println("user not found");
            } catch (RemoteException e) {
                errorMessageContainer.getChildren().setAll(new ErrorMessageUi("Server Down",true));
                System.out.println("server down");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private void populateLoggedInUserData() throws UserNotFoundException, RemoteException {
        // Here you get a user object that contains all data
        // of loggedin user
        UserEntity loggedInUser = RMIClientServices.logIn(phoneNumberField.getText(), passwordField.getText());
        CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
        currentUserAccount.populateCurrentUserData(loggedInUser);
        System.out.println("Connnected");
        RMIClientServices.registerInServer();
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
        LoginEntity loginEntity = deserialize();
        if (loginEntity != null) {
            System.out.println(loginEntity.getPassword());
            phoneNumberField.setText(loginEntity.getMobile());
            passwordField.setText(loginEntity.getPassword());
        } else cashPasswordAndUserName();
    }
//        private boolean validateFields () {
//            boolean validationFlag = true;
//
//            //Required Field Validation
//            RequiredFieldValidator requiredPassword = new RequiredFieldValidator();
//            requiredPassword.setMessage("Password can't be empty");
//            passwordField.getValidators().add(requiredPassword);
//
//            //Phone Number Validation
//            PhoneNumberValidator validNumber = new PhoneNumberValidator();
//            validNumber.setMessage(("Enter a valid phone number"));
//            phoneNumberField.getValidators().add(validNumber);
//
//            //Checking Fields
//            if (!phoneNumberField.validate()) {
//                validationFlag = false;
////            validNumber.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
//            }
//            if (!passwordField.validate()) {
////            requiredPassword.setIcon(new ImageView(new Image(getClass().getResourceAsStream("/images/error.png"))));
//                validationFlag = false;
//            }
//            return validationFlag;
//        }

    public LoginEntity cashPasswordAndUserName() {
        LoginEntity object1 = null;
        try (ObjectOutputStream objOStrm = new ObjectOutputStream(new
                FileOutputStream("cashedFile"))) {
            System.out.println("ddddddddddddd");
            String hashPassword = EncryptionUtil.encrypt(passwordField.getText().toString());
            object1 = new LoginEntity(phoneNumberField.getText(), hashPassword);
            System.out.println("object1: " + object1);
            objOStrm.writeObject(object1);
        } catch (IOException e) {
            System.out.println("Exception during serialization: " + e);
        }
        return object1;
    }

    public LoginEntity deserialize() {
        LoginEntity object2 = null;
        boolean exists = Files.exists(Path.of("cashedFile"));
        if (exists) {
            try (ObjectInputStream objIStrm = new ObjectInputStream(new
                    FileInputStream("cashedFile"))) {
                object2 = (LoginEntity) objIStrm.readObject();
                String decPassword = EncryptionUtil.decrypt(object2.getPassword());
                LoginEntity loginEntity = new LoginEntity(object2.getMobile(), decPassword);
                return loginEntity;
                // System.out.println("object2: " + object2.getMobile() + " " + object2.getPassword());
            } catch (Exception e) {
                System.out.println("Exception during deserialization: " + e);
            }
        }
        return object2;
    }

}