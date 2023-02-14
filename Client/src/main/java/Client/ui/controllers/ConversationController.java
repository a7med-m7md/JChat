package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.StyledChatMessage;
import Client.ui.controllerutils.ChatType;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.MessageEntity;
import model.user.UserStatus;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

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


    @FXML
    private ToggleButton italicToggle;

    @FXML
    private ToggleButton boldToggle;

    @FXML
    private ToggleButton underLineToggle;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox<String> fontSizeComboBox;

    @FXML
    private ComboBox<String> fontFamilyComboBox;


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
                    //Setting up the message to send
                    MessageEntity newMessage = new MessageEntity(currentSession.currentContactChatProperty().get().getMobile(), currentUserAccount.getMobile(), messageTextField.getText());
                    newMessage.setMessageFontFamily(fontFamilyComboBox.getValue());
                    newMessage.setMessageFontSize(Double.valueOf(fontSizeComboBox.getValue()));
                    newMessage.setMessageBubbleFill(colorPicker.getValue().toString());
                    newMessage.setUnderLineText(underLineToggle.isSelected());
                    newMessage.setBoldText(boldToggle.isSelected());
                    newMessage.setItalicText(italicToggle.isSelected());
                    // Adding this message to the contact's message list
                    currentSession.chatsMapProperty().get(currentContactChat).add(newMessage);
                    // Sending the message to the server
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

        currConvStatus.textProperty().bind(Bindings.createObjectBinding(() -> {
            String selectedStatus = currentContactChat.getStatus().getStatusName();
            if (selectedStatus == null) {
            return "";
            }
                return selectedStatus;
        }, currentContactChat.statusProperty()));

        currConvAvatar.fillProperty().bind(Bindings.createObjectBinding(() -> {
            Contact currentContact = currentSession.currentContactChatProperty().get();
            return currentContact == null ? null : new ImagePattern(currentContact.getImage());
        }, currentSession.currentContactChatProperty()));

        currConvAvatar.strokeProperty().bind(Bindings.createObjectBinding(() -> {
            String selectedStatus = currentContactChat.getStatus().getStatusName();
            UserStatus userStatus = UserStatus.getStatus(selectedStatus);
            if (userStatus == null) {
                return Color.BLACK;
            }
            return userStatus.getColor();
        }, currentContactChat.statusProperty()));



        //TODO SCROLL BUG
//        messagesListView.scrollTo(messagesListView.getItems().size() - 1);
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
            //TODO new Line in Message

//             else if (event.isShiftDown() && event.getCode() == KeyCode.ENTER)
//                int caretPosition = textArea.getCaretPosition();
//            textArea.setText(textArea.getText().substring(0, caretPosition) +
//                    "\n" +
//                    textArea.getText().substring(caretPosition));
//            textArea.positionCaret(caretPosition + 1);

        });

        //Populating The font-families combo-box
        ObservableList<String> fontList = FXCollections.observableList(Font.getFamilies());
        fontFamilyComboBox.setItems(fontList);
        fontFamilyComboBox.setValue("Segoe UI");

        //populating font size combo-box
        for (int i = 8; i <= 72; i++) {
            fontSizeComboBox.getItems().add(Integer.toString(i));
        }
        fontSizeComboBox.setValue("12");
        colorPicker.setValue(Color.rgb(221, 223, 232));
    }

}
