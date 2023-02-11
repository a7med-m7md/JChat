package Client.ui.controllers;

import Client.ui.components.StyledChatMessage;
import Client.ui.controllerutils.ChatType;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.Message;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConversationController implements Initializable {

    @FXML
    private Label currConvContact;

    @FXML
    private Circle currConvAvatar;

    @FXML
    private ScrollPane conversationPane;

    @FXML
    private VBox conversationContainer;

    @FXML
    private TextField messageTextField;

    @FXML
    void sendMessage(MouseEvent event) {
//        CurrentSession currentSession = CurrentSession.getInstance();
//        currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty().get())
//                .add(new Message(currentSession.getMyAccount(), messageTextField.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        CurrentSession currentSession = CurrentSession.getInstance();

        //binding the header contents
        //chat contact's name
        //chat contact's avatar
//        currConvContact.textProperty().bind(Bindings.createStringBinding(() -> {
            Contact currentContact = currentSession.currentContactChatProperty().get();
//            return currentContact == null ? "" : currentContact.getDisplayName();
//        }, currentSession.currentContactChatProperty()));

        currConvAvatar.fillProperty().bind(Bindings.createObjectBinding(() -> {
//            Contact currentContact = currentSession.currentContactChatProperty().get();
            return currentContact == null ? null : new ImagePattern(currentContact.getAvatar());
        }, currentSession.currentContactChatProperty()));

        //binding the contents of contact's messages list to the messagesContainer VBox
        currentSession.chatsMapProperty().addListener((observable, oldValue, newValue) -> {
//            Contact currentContact = currentSession.currentContactChatProperty().get();
            if (currentContact != null) {
                ObservableList<Message> messages = newValue.get(currentContact);
                if (messages != null) {
                    displayMessages(messages);
                }
            }
        });

        //listner for selected contact
        //retrieves list of messages of the selected contact
        //binds the messagesContainerVBox to the current contact messageslist
        currentSession.currentContactChatProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ObservableList<Message> messages = currentSession.chatsMapProperty().get().get(newValue);
                if (messages != null) {
                    displayMessages(messages);
                }
            }
        });

        //Scrolls Down Automatically when new messages added
        conversationContainer.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                conversationPane.setVvalue((double) newValue);
            }
        });

    }

    private void displayMessages(ObservableList<Message> messages) {
        conversationContainer.getChildren().setAll(messages.stream().map(message -> {
            StyledChatMessage styledMessage = new StyledChatMessage(message.getContact(), message, ChatType.GROUP);
            return styledMessage;
        }).collect(Collectors.toList()));
    }

}

