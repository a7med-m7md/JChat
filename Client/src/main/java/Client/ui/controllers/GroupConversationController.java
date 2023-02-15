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
import model.Group;
import model.GroupMessageEntity;
import model.MessageEntity;
import model.user.UserStatus;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class GroupConversationController implements Initializable {

    @FXML
    private Label currConvContact;
    @FXML
    private Label currConvStatus;

    @FXML
    private Circle currConvAvatar;

    @FXML
    private ListView<GroupMessageEntity> messagesListView;

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
    private Group currentGroupChat;

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
                if (currentSession.currentGroupChatProperty().get() != null) {
                    //Setting up the message to send
                    GroupMessageEntity newMessage = new GroupMessageEntity(CurrentSession.getInstance().getCurrentGroupChat(), currentUserAccount.getMobile(), messageTextField.getText());
                    newMessage.setMessageFontFamily(fontFamilyComboBox.getValue());
                    newMessage.setMessageFontSize(Double.valueOf(fontSizeComboBox.getValue()));
                    newMessage.setMessageBubbleFill(colorPicker.getValue().toString());
                    newMessage.setUnderLineText(underLineToggle.isSelected());
                    newMessage.setBoldText(boldToggle.isSelected());
                    newMessage.setItalicText(italicToggle.isSelected());
                    // Adding this message to the contact's message list
                    if (currentSession.currentGroupChatProperty() != null)
                        currentSession.groupChatsMapProperty().get(currentSession.currentGroupChatProperty().get()).add(newMessage);
                    // Sending the message to the server
                    RMIClientServices.groupMessaging(newMessage);
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

        currentGroupChat = currentSession.currentGroupChatProperty().get();
        ObservableList<GroupMessageEntity> currentGroupMessageList = currentSession.groupChatsMapProperty().get(currentGroupChat);

        messagesListView.setItems(currentGroupMessageList);
        messagesListView.setCellFactory(listView -> new ListCell<GroupMessageEntity>() {
            @Override
            protected void updateItem(GroupMessageEntity message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setGraphic(null);
                } else {
                    StyledChatMessage styledChatMessage = new StyledChatMessage(currentSession.getContactByPhone(message.getSender()), message, ChatType.GROUP);
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
            Group currentGroup = currentSession.currentGroupChatProperty().get();
            return currentGroup == null ? "" : currentGroup.getName();
        }, currentSession.currentGroupChatProperty()));

        currConvStatus.textProperty().bind(Bindings.createStringBinding(() -> {
            Group currentGroup = currentSession.currentGroupChatProperty().get();
            return currentGroup == null ? "" : String.valueOf(currentGroup.getListMembers().size()) + " Members";
        }, currentSession.currentContactChatProperty()));

//        currConvAvatar.fillProperty().bind(Bindings.createObjectBinding(() -> {
//            Contact currentContact = currentSession.currentContactChatProperty().get();
//            return currentContact == null ? null : new ImagePattern(currentContact.getImage());
//        }, currentSession.currentContactChatProperty()));


//        //TODO SCROLL BUG
////        messagesListView.scrollTo(messagesListView.getItems().size() - 1);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                messagesListView.scrollTo(currentGroupMessageList.size() - 1);
//                //Scrolls Down Automatically when new messages added
//            }
//        });

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
