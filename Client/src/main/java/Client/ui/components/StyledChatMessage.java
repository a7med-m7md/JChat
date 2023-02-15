package Client.ui.components;

import Client.ui.controllerutils.ChatType;
import Client.ui.controllerutils.MessageSource;
import Client.ui.models.*;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.MessageEntityInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StyledChatMessage extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final Label messageSenderName;
    protected final Circle senderAvatar;
    protected final HBox hBox;
    protected final GridPane messageBubble;
    protected final ColumnConstraints columnConstraints2;
    protected final RowConstraints rowConstraints1;
    protected final Label messageBody;
    protected final Label messageTimeStamp;
    private final String messageStyleSheet = "/styling/styling.css";

    public StyledChatMessage(UserModel contact, MessageEntityInterface message, ChatType type) {

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        messageSenderName = new Label();
        senderAvatar = new Circle();
        hBox = new HBox();
        messageBubble = new GridPane();
        columnConstraints2 = new ColumnConstraints();
        rowConstraints1 = new RowConstraints();
        messageBody = new Label();
        messageTimeStamp = new Label();
        MessageSource source = MessageSource.SENT;

        if (contact.getMobile() == CurrentSession.getInstance().getMyAccount(CurrentUserAccount.getInstance()).getMobile())
            source = MessageSource.SENT;
        else source = MessageSource.RECIEVED;

        if (source == MessageSource.SENT) {
            setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            messageBubble.setStyle("-fx-background-color:" + message.getMessageBubbleFill() + ";");
//            messageBody.setTextFill(Color.valueOf("#333333"));

        } else if (source == MessageSource.RECIEVED) {
            setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

                messageBubble.setStyle("-fx-background-color:" + message.getMessageBubbleFill() + ";");
//            messageBody.setTextFill(Color.valueOf("#FFFF"));
        }

        setStyle("-fx-background-color: transparent;");

        columnConstraints.setFillWidth(false);
        columnConstraints.setHgrow(Priority.NEVER);
        columnConstraints.setMinWidth(0.0);
        columnConstraints.setPercentWidth(0.0);

        columnConstraints0.setFillWidth(false);
        columnConstraints0.setHgrow(Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(440.0);
        columnConstraints0.setMinWidth(10.0);

        columnConstraints1.setHgrow(Priority.ALWAYS);
        columnConstraints1.setMaxWidth(Double.MAX_VALUE);
        columnConstraints1.setMinWidth(60.0);

        rowConstraints.setVgrow(Priority.ALWAYS);

        rowConstraints0.setMaxHeight(Double.MAX_VALUE);
        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setVgrow(Priority.ALWAYS);
        setPadding(new Insets(10.0, 10.0, 2.0, 10.0));

        GridPane.setColumnIndex(messageSenderName, 1);
        messageSenderName.setText(message.getSender());
        messageSenderName.setTextFill(Color.valueOf("#34434c"));
        messageSenderName.setFont(new Font("Segoe UI Light", 12.0));
        GridPane.setMargin(messageSenderName, new Insets(0.0, 10.0, 0.0, 10.0));

        GridPane.setRowIndex(senderAvatar, 1);
        GridPane.setValignment(senderAvatar, VPos.TOP);
        senderAvatar.setFill(new ImagePattern(contact.getImage()));
        senderAvatar.setRadius(14.0);
        senderAvatar.setStroke(Color.BLACK);
        senderAvatar.setStrokeType(StrokeType.INSIDE);
        senderAvatar.setStrokeWidth(0.0);

        GridPane.setColumnIndex(hBox, 1);
        GridPane.setRowIndex(hBox, 1);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10.0);

        messageBubble.getStyleClass().add("message-bubble");
        messageBubble.getStylesheets().add((this.getClass().getResource(messageStyleSheet).toExternalForm()));
        columnConstraints2.setFillWidth(false);
        columnConstraints2.setHgrow(Priority.ALWAYS);
        columnConstraints2.setMaxWidth(Double.MAX_VALUE);

        rowConstraints1.setMaxHeight(Double.MAX_VALUE);
        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setVgrow(Priority.ALWAYS);
        messageBubble.setPadding(new Insets(10.0, 15.0, 10.0, 15.0));

        messageBody.setAlignment(Pos.BOTTOM_LEFT);
        messageBody.setMaxHeight(Double.MAX_VALUE);
        messageBody.setMaxWidth(Double.MAX_VALUE);
        messageBody.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        messageBody.setText(message.getMessage());
        messageBody.setTextFill(Color.valueOf("#333333"));
        messageBody.setWrapText(true);
        messageBody.setFont(new Font(message.getMessageFontFamily(), message.getMessageFontSize()));
        messageBody.setUnderline(message.isUnderLineText());
        if (message.isItalicText()) {
            messageBody.setFont(Font.font(messageBody.getFont().getFamily(), FontPosture.ITALIC, messageBody.getFont().getSize()));
        }
        if (message.isBoldText()) {
            messageBody.setFont(Font.font(messageBody.getFont().getFamily(), FontWeight.BOLD, messageBody.getFont().getSize()));
        }

        /*
        Automatic textfill contrast setting
        with respect to the background fill
        */

        // Get the background color of the node
        Color bgColor = Color.valueOf(message.getMessageBubbleFill());

        // Calculate the brightness of the background color
        double brightness = (0.299 * bgColor.getRed() + 0.587 * bgColor.getGreen() + 0.114 * bgColor.getBlue()) * bgColor.getOpacity();

        // Set the text fill based on the brightness of the background color
        if (brightness > 0.5) {
            messageBody.setTextFill(Color.valueOf("#333333"));
        } else {
            messageBody.setTextFill(Color.WHITE);
        }

        messageTimeStamp.setAlignment(Pos.BOTTOM_LEFT);
        messageTimeStamp.setMinWidth(70.0);
        messageTimeStamp.setNodeOrientation(NodeOrientation.INHERIT);
        messageTimeStamp.setText(getMessageTime());
        messageTimeStamp.setTextFill(Color.valueOf("#697579"));
        hBox.setPadding(new Insets(0.0, 10.0, 0.0, 10.0));

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);
        getRowConstraints().add(rowConstraints0);
        messageBubble.getColumnConstraints().add(columnConstraints2);
        messageBubble.getRowConstraints().add(rowConstraints1);
        messageBubble.getChildren().add(messageBody);
        hBox.getChildren().add(messageBubble);
        hBox.getChildren().add(messageTimeStamp);
        getChildren().add(hBox);

        if (type == ChatType.SERVER) {
            setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            messageBubble.setStyle("-fx-background-color: transparent;");
            messageBody.setStyle("-fx-text-fill: #8D909AFF;");
            messageTimeStamp.setVisible(false);
        }
        if (type == ChatType.GROUP) {
            getChildren().add(messageSenderName);
            getChildren().add(senderAvatar);
        }

    }

    public String getMessageTime() {
        DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");
        String currentTime = timeFormat.format(new Date()).toString();
        return currentTime;
    }

    public String getMessageContent() {
        return messageBody.getText();
    }

    public String getMessageSender() {
        return messageSenderName.getText();
    }
}
