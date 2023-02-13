package Client.ui.components;

import Client.ui.models.Contact;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.MessageEntity;
import model.user.UserStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ConversationCard extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final Circle contactAvatar;
    protected final Label messageTimeStamp;
    protected final VBox vBox;
    protected final Label contactName;
    protected final Label latestMessage;
    private final String stylesheet_1 = "/styling/main.css";

    public ConversationCard(Contact contact, ObservableList<MessageEntity> messages) {

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        contactAvatar = new Circle();
        messageTimeStamp = new Label();
        vBox = new VBox();
        this.contactName = new Label();
        latestMessage = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(Double.MAX_VALUE);
        setPrefHeight(69.0);
        setStyle("-fx-background-color: transparent;");
        getStyleClass().add("chat-card");
        getStylesheets().add("/fxml/../styling/main.css");

        columnConstraints.setFillWidth(false);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints.setMinWidth(USE_PREF_SIZE);

        columnConstraints0.setHalignment(javafx.geometry.HPos.LEFT);
        columnConstraints0.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints0.setMaxWidth(Double.MAX_VALUE);

        columnConstraints1.setFillWidth(false);
        columnConstraints1.setHalignment(javafx.geometry.HPos.RIGHT);
        columnConstraints1.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints1.setMinWidth(USE_PREF_SIZE);

        rowConstraints.setMaxHeight(USE_PREF_SIZE);
        rowConstraints.setMinHeight(50.0);
        rowConstraints.setPrefHeight(50.0);
        rowConstraints.setValignment(javafx.geometry.VPos.CENTER);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);


        contactAvatar.setFill(new ImagePattern(contact.getImage()));
        contactAvatar.setRadius(23.0);
        contactAvatar.setStroke(javafx.scene.paint.Color.valueOf("#62ff32"));
        contactAvatar.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        contactAvatar.setStrokeWidth(2.0);
        GridPane.setMargin(contactAvatar, new Insets(3.0));

        GridPane.setColumnIndex(messageTimeStamp, 2);
        messageTimeStamp.setAlignment(javafx.geometry.Pos.CENTER);
        messageTimeStamp.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        //-------------------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        if (messages.size() > 0)
            messageTimeStamp.setText(messages.get(messages.size() - 1).getTime().format(formatter));
//            messageTimeStamp.setText(messages.get(messages.size() - 1).getTime().format());
        messageTimeStamp.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        messageTimeStamp.setTextFill(javafx.scene.paint.Color.valueOf("#697579"));
        messageTimeStamp.setWrapText(true);

        GridPane.setColumnIndex(vBox, 1);
        GridPane.setHalignment(vBox, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(vBox, javafx.geometry.VPos.CENTER);
        GridPane.setVgrow(vBox, javafx.scene.layout.Priority.NEVER);
        vBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setSpacing(3.0);

        VBox.setVgrow(contactName, javafx.scene.layout.Priority.NEVER);
        contactName.setAlignment(javafx.geometry.Pos.CENTER);
        contactName.setText(contact.getName());
        contactName.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);
        contactName.setTextFill(javafx.scene.paint.Color.valueOf("#5a6777"));
        contactName.setWrapText(true);
        contactName.setFont(new Font("Segoe UI", 15.0));

        VBox.setVgrow(latestMessage, javafx.scene.layout.Priority.NEVER);
        latestMessage.setAlignment(javafx.geometry.Pos.CENTER);
        latestMessage.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        //-------------------------------
        if (messages.size() > 0)
            latestMessage.setText(messages.get(messages.size() - 1).getMSGBody());
        else {
            latestMessage.setText("no messages yet!");
        }
        latestMessage.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        latestMessage.setTextFill(javafx.scene.paint.Color.valueOf("#9aa1aa"));
        vBox.setPadding(new Insets(4.0, 10.0, 4.0, 10.0));
        setPadding(new Insets(10.0));
        setCursor(Cursor.HAND);

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);
        getChildren().add(contactAvatar);
        getChildren().add(messageTimeStamp);
        vBox.getChildren().add(contactName);
        vBox.getChildren().add(latestMessage);
        getChildren().add(vBox);

        contactAvatar.strokeProperty().bind(Bindings.createObjectBinding(() -> {
            String selectedStatus = contact.getStatus().getStatusName();
            UserStatus userStatus = UserStatus.getStatus(selectedStatus);
            if (userStatus == null) {
                return Color.BLACK;
            }
            return userStatus.getColor();
        }, contact.statusProperty()));

    }
}
