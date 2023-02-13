package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ContactCard;
import Client.ui.components.ConversationCard;
import Client.ui.components.StyledChatMessage;
import Client.ui.controllerutils.ChatType;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private ScrollPane conversationPane;

    @FXML
    private ListView<MessageEntity> conversationContainer;

    @FXML
    private TextField messageTextField;

    @FXML
    void sendMessage(MouseEvent event) {

//        try {
        //TODO RMI Invocation Here
        CurrentSession currentSession = CurrentSession.getInstance();
        CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
        if (currentSession.currentContactChatProperty().get() != null) {
            MessageEntity newMessage = new MessageEntity(currentSession.currentContactChatProperty().get().getMobile(), currentUserAccount.getMobile(), messageTextField.getText());
//                RMIClientServices.chatMessaging(newMessage);
            currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty()
                    .get()).add(newMessage);
        }
        System.out.println(conversationContainer.getItems());
//        StyledChatMessage newStyledMessage = new StyledChatMessage(currentUserAccount,newMessage,ChatType.SINGLE);
//        conversationContainer.getChildren().add(newStyledMessage);
//
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        CurrentSession currentSession = CurrentSession.getInstance();

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

        //Scrolls Down Automatically when new messages added
        conversationContainer.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                conversationPane.setVvalue((double) newValue);
            }
        });

        conversationContainer.setItems(currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty().get()));

        Binding<ObservableList<MessageEntity>> binding = Bindings.createObjectBinding(() -> currentSession.chatsMapProperty().get().get(currentSession.contactsListProperty().get()), currentSession.chatsMapProperty());
        conversationContainer.itemsProperty().bind(binding);

//        conversationContainer.itemsProperty().bind(Bindings.valueAt(currentSession.chatsMapProperty(), currentSession.currentContactChatProperty().get()));
        ;
        //binding the contents of contact's messages list to the messagesContainer listview
//        if (currentSession.currentContactChatProperty().get() != null) {
//            currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty().get()).addListener((ListChangeListener<MessageEntity>) change -> {
//                Contact currentContact = currentSession.currentContactChatProperty().get();
//                if (currentContact != null) {
//                    ObservableList<MessageEntity> messages = currentSession.chatsMapProperty().get(currentContact);
//                    if (messages != null) {
//                        conversationContainer.setItems(messages);
//                    }
//                }
//            });
//        }

        conversationContainer.setCellFactory(listView -> new ListCell<MessageEntity>() {
            @Override
            protected void updateItem(MessageEntity message, boolean empty) {
                super.updateItem(message, empty);

                if (empty || message == null) {
                    setGraphic(null);
                } else {
                    StyledChatMessage styledChatMessage = new StyledChatMessage(currentSession.getContactByPhone(message.getSender()), message, ChatType.SINGLE);
                    setGraphic(styledChatMessage);
                }
            }
        });;
;


//        conversationContainer.setCellFactory(listView -> new ListCell<MessageEntity>() {
//            @Override
//            protected void updateItem(MessageEntity message, boolean empty) {
//                super.updateItem(message, empty);
//
//                if (empty || message == null) {
//                    setGraphic(null);
//                } else {
//                    StyledChatMessage styledChatMessage = new StyledChatMessage(currentSession.getContactByPhone(message.getSender()), message, ChatType.SINGLE);
//                    setGraphic(styledChatMessage);
//                }
//            }
//        });


        //listner for selected contact
        //retrieves list of messages of the selected contact

//
//        Contact currentContact = currentSession.currentContactChatProperty().get();
//        ObservableList<MessageEntity> currentContactMessages = currentSession.chatsMapProperty().get(currentContact);
//
////        conversationContainer.itemsProperty().bind(Bindings.createObjectBinding(() -> currentContactMessages));
//
////        Bindings.bindContent(conversationContainer.getItems(), currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty().get()));
////        conversationContainer.getItems().bind(currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty().get()));
//
//        if (currentContact != null) {
////        conversationContainer.itemsProperty().bind(currentSession.chatsMapProperty().get(currentSession.currentContactChatProperty().get()));
//            conversationContainer.setItems(currentContactMessages);
//
//
//            conversationContainer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



//            conversationContainer.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(currentContactMessages)));
//
//            conversationContainer.setCellFactory(listView -> new ListCell<MessageEntity>() {
//                @Override
//                protected void updateItem(MessageEntity message, boolean empty) {
//                    super.updateItem(message, empty);
//
//                    if (empty || message == null) {
//                        setGraphic(null);
//                    } else {
//                        StyledChatMessage styledChatMessage = new StyledChatMessage(currentSession.getContactByPhone(message.getSender()), message, ChatType.SINGLE);
//                        setGraphic(styledChatMessage);
//                    }
//                }
//            });
    }

}



