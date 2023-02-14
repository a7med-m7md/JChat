package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ContactCard;
import Client.ui.components.ConversationCard;
import Client.ui.components.StyledChatMessage;
import Client.ui.controllerutils.ChatType;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import Client.ui.models.Message;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.MessageEntity;
import model.user.UserStatus;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConversationController implements Initializable {

    @FXML
    private Label currConvContact;
    @FXML
    private Label currConvStatus;

    @FXML
    private Circle currConvAvatar;

    @FXML
    private ListView<MessageEntity> messagesListView;

    @FXML
    private TextField messageTextField;

    CurrentSession currentSession;
    Contact currentContactChat;

    @FXML
    void attachFile(MouseEvent event) {

    }


    @FXML
    void sendMessage(MouseEvent event) throws RemoteException {

        sendNewMessage();
    }

    private void sendNewMessage() {
        try {
            //TODO RMI Invocation Here
            CurrentSession currentSession = CurrentSession.getInstance();
            CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
            if (!messageTextField.getText().equals("")) {
                if (currentSession.currentContactChatProperty().get() != null) {
                    MessageEntity newMessage = new MessageEntity(currentSession.currentContactChatProperty().get().getMobile(), currentUserAccount.getMobile(), messageTextField.getText());
                    currentSession.chatsMapProperty().get(currentContactChat).add(newMessage);
                    RMIClientServices.chatMessaging(newMessage);
                    messageTextField.clear();
                }
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Loaded");
        currentSession = CurrentSession.getInstance();

        currentContactChat = currentSession.currentContactChatProperty().get();
        ObservableList<MessageEntity> currentContactMessageList = currentSession.chatsMapProperty().get(currentContactChat);

        messagesListView.setItems(currentContactMessageList);
        messagesListView.setCellFactory(listView -> new ListCell<MessageEntity>() {
            @Override
            protected void updateItem(MessageEntity message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setGraphic(null);
                } else {
                    StyledChatMessage styledChatMessage = new StyledChatMessage(currentSession.getContactByPhone(message.getSender()), message, ChatType.SINGLE);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setGraphic(styledChatMessage);
                        }
                    });
                }
            }
        });


        //binding the header contents
        //chat contact's name
        //chat contact's avatar
        currConvContact.textProperty().bind(Bindings.createStringBinding(() -> {
            Contact currentContact = currentSession.currentContactChatProperty().get();
            return currentContact == null ? "" : currentContact.getName();
        }, currentSession.currentContactChatProperty()));

        currConvStatus.textProperty().bind(Bindings.createStringBinding(() -> {
            Contact currentContact = currentSession.currentContactChatProperty().get();
            return currentContact == null ? "" : currentContact.getStatus().getStatusName();
        }, currentSession.currentContactChatProperty()));

        currConvAvatar.fillProperty().bind(Bindings.createObjectBinding(() -> {
            Contact currentContact = currentSession.currentContactChatProperty().get();
            return currentContact == null ? null : new ImagePattern(currentContact.getImage());
        }, currentSession.currentContactChatProperty()));

        currConvAvatar.strokeProperty().bind(Bindings.createObjectBinding(() -> {
            String selectedStatus = null;
            Contact currentContact = currentSession.currentContactChatProperty().get();
            if (currentContact != null)
                selectedStatus = currentContact.getStatus().getStatusName();
            UserStatus userStatus = UserStatus.getStatus(selectedStatus);
            if (userStatus == null) {
                return Color.WHITE;
            }
            return userStatus.getColor();
        }, currConvStatus.textProperty()));


        //TODO SCROLL BUG
        messagesListView.scrollTo(messagesListView.getItems().size() - 1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messagesListView.scrollTo(currentContactMessageList.size() - 1);
                //Scrolls Down Automatically when new messages added
            }
        });

        messageTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendNewMessage();
            }
//             else if (event.isShiftDown() && event.getCode() == KeyCode.ENTER)
//                int caretPosition = textArea.getCaretPosition();
//            textArea.setText(textArea.getText().substring(0, caretPosition) +
//                    "\n" +
//                    textArea.getText().substring(caretPosition));
//            textArea.positionCaret(caretPosition + 1);

        });

    }

}
