package Client.ui.components;


import Client.ui.models.Contact;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ContactCard extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final Circle contactAvatar;
    protected final VBox vBox;
    protected final Label contactName;
    protected final Label bio;

    Contact contact;

    public ContactCard(Contact contact) {
        this.contact = contact;
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        contactAvatar = new Circle();
        vBox = new VBox();
        contactName = new Label();
        bio = new Label();

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

        contactAvatar.setFill(new ImagePattern(contact.getPicture()));
        contactAvatar.setRadius(17.0);
        contactAvatar.setStroke(javafx.scene.paint.Color.valueOf("#62ff32"));
        contactAvatar.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        contactAvatar.setStrokeWidth(2.0);
        GridPane.setMargin(contactAvatar, new Insets(3.0));

        GridPane.setColumnIndex(vBox, 1);
        GridPane.setHalignment(vBox, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(vBox, javafx.geometry.VPos.CENTER);
        GridPane.setVgrow(vBox, javafx.scene.layout.Priority.NEVER);
        vBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setSpacing(0.0);

        VBox.setVgrow(contactName, javafx.scene.layout.Priority.NEVER);
        contactName.setAlignment(javafx.geometry.Pos.CENTER);
        contactName.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        contactName.setText(contact.getName());
        contactName.setTextFill(javafx.scene.paint.Color.valueOf("#5a6777"));
        contactName.setWrapText(true);
        contactName.setFont(new Font("Segoe UI", 13.0));

        VBox.setVgrow(bio, javafx.scene.layout.Priority.NEVER);
        bio.setAlignment(javafx.geometry.Pos.CENTER);
        bio.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        bio.setText(contact.getBio());
        bio.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        bio.setTextFill(javafx.scene.paint.Color.valueOf("#9aa1aa"));
        vBox.setPadding(new Insets(4.0, 10.0, 4.0, 5.0));
        bio.setFont(new Font("Segoe UI", 10.0));
        setPadding(new Insets(5.0));
        setCursor(Cursor.HAND);

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);
        getChildren().add(contactAvatar);
        vBox.getChildren().add(contactName);
        vBox.getChildren().add(bio);
        getChildren().add(vBox);


    }
    @Override
    public String toString() {
        return contact.getMobile();
    }
}
