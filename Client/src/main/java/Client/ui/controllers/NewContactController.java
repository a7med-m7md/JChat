package Client.ui.controllers;

import Client.ui.components.ErrorMessageUi;
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
            //If PhoneNumber doesn't exist (Reverse Logic) :
            errorContainer.getChildren().setAll(new ErrorMessageUi("No such user!"));
        } catch (Exception e) {
            //if phone number exists

            if (errorContainer.getChildren() != null)
                errorContainer.getChildren().removeAll();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
