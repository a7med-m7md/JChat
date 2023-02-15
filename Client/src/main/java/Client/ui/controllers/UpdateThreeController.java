package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.models.CurrentUserAccount;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.DuplicateUserException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.user.UserDto;
import model.user.UserStatus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class UpdateThreeController implements Initializable {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private JFXTextField userBio;

    @FXML
    private JFXTextField displayNameField;

    @FXML
    private Button confirmCreateAccountBtn;

    @FXML
    private Circle userAvatar;

    private Image userImage;

    @FXML
    void handleConfirmCreateAccount(MouseEvent event) throws IOException {
        CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
        if (displayNameField.validate()) {

            UserDto updatedAccountInfo = new UserDto();
            updatedAccountInfo.setMobile(CurrentUserAccount.getInstance().getMobile());
            updatedAccountInfo.setPassword(CurrentUserAccount.getInstance().getPassword());
            updatedAccountInfo.setEmail(CurrentUserAccount.getInstance().getEmail());
            updatedAccountInfo.setCountry(CurrentUserAccount.getInstance().getCountry());
            updatedAccountInfo.setGender(CurrentUserAccount.getInstance().getGender());
            updatedAccountInfo.setDateOfBirth(CurrentUserAccount.getInstance().getDateOfBirth());
            updatedAccountInfo.setName(CurrentUserAccount.getInstance().getName());
            updatedAccountInfo.setBio(CurrentUserAccount.getInstance().getBio());
            updatedAccountInfo.setPicture(CurrentUserAccount.getInstance().getPictureAsBytes());
            updatedAccountInfo.setStatus(CurrentUserAccount.getMyAccount().getStatus());


            RMIClientServices.updateInfo(updatedAccountInfo);


            //Switch to Main Screen
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

        }
    }

    @FXML
    void openFileChooser(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            userImage = new Image(file.toURI().toString());
            CurrentUserAccount.getInstance().setPicture(userImage);
            userAvatar.setFill(new ImagePattern(userImage));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //validating required displayname field
        RequiredFieldValidator requiredNameField = new RequiredFieldValidator();
        requiredNameField.setMessage("Please Enter a display Name");
        displayNameField.getValidators().add(requiredNameField);

        //initializing default bio and image
        userBio.setText("Hello, I'm using JChat!");
        userImage = new Image(getClass().getResourceAsStream("/images/image-placeholder.png"));
        userAvatar.setFill(new ImagePattern(userImage));

        userAvatar.setFill(new ImagePattern(CurrentUserAccount.getInstance().getImage()));
        displayNameField.setText(CurrentUserAccount.getInstance().getName());
        userBio.setText(CurrentUserAccount.getInstance().getBio());


    }
}
