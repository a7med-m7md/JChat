package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ErrorMessageUi;
import exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NewContactController implements Initializable {


    @FXML
    private TextField newContactPhoneField;
    @FXML
    private VBox errorContainer = new VBox();


    @FXML
    void addContact(MouseEvent event) {

    }

    @FXML
    void addNumber(MouseEvent event) {
        try {
            //TODO Check phoneNumber in DB Here
            //If PhoneNumber exists
            RMIClientServices.checkUserExists(newContactPhoneField.getText());
//            if (errorContainer.getChildren() != null)
                errorContainer.getChildren().clear();
        } catch (UserNotFoundException e) {
            //if phone number doesn't exist
            errorContainer.getChildren().setAll(new ErrorMessageUi("No such user!"));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
