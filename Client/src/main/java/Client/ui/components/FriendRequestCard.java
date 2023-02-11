package Client.ui.components;

import Client.network.RMIClientServices;
import Client.ui.models.CurrentUserAccount;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import model.FriendEntity;

import java.rmi.RemoteException;

public class FriendRequestCard extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final ColumnConstraints columnConstraints2;
    protected final ColumnConstraints columnConstraints3;
    protected final RowConstraints rowConstraints;
    protected final Circle contactAvatar;
    protected final VBox vBox;
    protected final Label contactName;
    protected final Label bio;
    protected final Button button;
    protected final SVGPath sVGPath;
    protected final Button button0;
    protected final SVGPath sVGPath0;

    FriendEntity currentFriendRequest;

    public FriendRequestCard(FriendEntity friend) {

        currentFriendRequest = new FriendEntity();
        currentFriendRequest = friend;

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        columnConstraints2 = new ColumnConstraints();
        columnConstraints3 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        contactAvatar = new Circle();
        vBox = new VBox();
        contactName = new Label();
        bio = new Label();
        button = new Button();
        sVGPath = new SVGPath();
        button0 = new Button();
        sVGPath0 = new SVGPath();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(Double.MAX_VALUE);
        setPrefHeight(69.0);
        setStyle("-fx-background-color: #f7fbff;");
        setStyle("-fx-background-radius: 7px;");
//        getStyleClass().add("chat-card");
//        getStylesheets().add("/styling/main.css");

        columnConstraints.setFillWidth(false);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints.setMinWidth(USE_PREF_SIZE);

        columnConstraints0.setHalignment(javafx.geometry.HPos.LEFT);
        columnConstraints0.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints0.setMaxWidth(Double.MAX_VALUE);

        columnConstraints1.setFillWidth(false);
        columnConstraints1.setHalignment(javafx.geometry.HPos.LEFT);
        columnConstraints1.setHgrow(javafx.scene.layout.Priority.NEVER);

        columnConstraints2.setFillWidth(false);
        columnConstraints2.setHalignment(javafx.geometry.HPos.LEFT);
        columnConstraints2.setHgrow(javafx.scene.layout.Priority.NEVER);

        columnConstraints3.setFillWidth(false);
        columnConstraints3.setHalignment(javafx.geometry.HPos.RIGHT);
        columnConstraints3.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints3.setMinWidth(USE_PREF_SIZE);

        rowConstraints.setMaxHeight(USE_PREF_SIZE);
        rowConstraints.setMinHeight(50.0);
        rowConstraints.setPrefHeight(50.0);
        rowConstraints.setValignment(javafx.geometry.VPos.CENTER);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);

        contactAvatar.setFill(javafx.scene.paint.Color.DODGERBLUE);
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

        VBox.setVgrow(contactName, javafx.scene.layout.Priority.NEVER);
        contactName.setAlignment(javafx.geometry.Pos.CENTER);
        contactName.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        contactName.setText(friend.getName());
        contactName.setTextFill(javafx.scene.paint.Color.valueOf("#5a6777"));
        contactName.setWrapText(true);
        contactName.setFont(new Font("Segoe UI", 13.0));

        VBox.setVgrow(bio, javafx.scene.layout.Priority.NEVER);
        bio.setAlignment(javafx.geometry.Pos.CENTER);
        bio.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        bio.setText(friend.getName());
        bio.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        bio.setTextFill(javafx.scene.paint.Color.valueOf("#9aa1aa"));
        bio.setFont(new Font(10.0));
        vBox.setPadding(new Insets(4.0));

        GridPane.setColumnIndex(button, 2);
        button.setMnemonicParsing(false);
        button.setOnMouseClicked(this::acceptRequest);
        button.setPrefHeight(32.0);
        button.setStyle("-fx-background-radius: 10px; -fx-background-color: #10FF70FF;");
        button.setText("Accept");
        button.setTextFill(javafx.scene.paint.Color.valueOf("#47555f"));

        sVGPath.setContent("M12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21ZM16.7682 9.64018C17.1218 9.21591 17.0645 8.58534 16.6402 8.23178C16.2159 7.87821 15.5853 7.93554 15.2318 8.35982L11.6338 12.6774C11.2871 13.0934 11.0922 13.3238 10.9366 13.4653L10.9306 13.4707L10.9242 13.4659C10.7564 13.339 10.5415 13.1272 10.1585 12.7443L8.70711 11.2929C8.31658 10.9024 7.68342 10.9024 7.29289 11.2929C6.90237 11.6834 6.90237 12.3166 7.29289 12.7071L8.74428 14.1585L8.78511 14.1993L8.78512 14.1993C9.11161 14.526 9.4257 14.8402 9.71794 15.0611C10.0453 15.3087 10.474 15.5415 11.0234 15.5165C11.5728 15.4916 11.9787 15.221 12.2823 14.9448C12.5534 14.6983 12.8377 14.3569 13.1333 14.0021L13.1333 14.0021L13.1703 13.9577L16.7682 9.64018Z");
        sVGPath.setFill(javafx.scene.paint.Color.valueOf("#47555f"));
        sVGPath.setFillRule(javafx.scene.shape.FillRule.EVEN_ODD);
        button.setGraphic(sVGPath);
        GridPane.setMargin(button, new Insets(0.0, 5.0, 0.0, 5.0));

        GridPane.setColumnIndex(button0, 3);
        button0.setMnemonicParsing(false);
        button0.setOnMouseClicked(this::rejectRequest);
        button0.setPrefHeight(32.0);
        button0.setStyle("-fx-background-radius: 10px; -fx-background-color: #FD4848FF;");
        button0.setText("Reject");
        button0.setTextFill(javafx.scene.paint.Color.WHITE);

        sVGPath0.setContent("M12,2A10,10,0,1,0,22,12,10,10,0,0,0,12,2Zm3.707,12.293a1,1,0,1,1-1.414,1.414L12,13.414,9.707,15.707a1,1,0,0,1-1.414-1.414L10.586,12,8.293,9.707A1,1,0,0,1,9.707,8.293L12,10.586l2.293-2.293a1,1,0,0,1,1.414,1.414L13.414,12Z");
        sVGPath0.setFill(javafx.scene.paint.Color.WHITE);
        button0.setGraphic(sVGPath0);
        setPadding(new Insets(5.0));
        setCursor(Cursor.HAND);

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getColumnConstraints().add(columnConstraints2);
        getColumnConstraints().add(columnConstraints3);
        getRowConstraints().add(rowConstraints);
        getChildren().add(contactAvatar);
        vBox.getChildren().add(contactName);
        vBox.getChildren().add(bio);
        getChildren().add(vBox);
        getChildren().add(button);
        getChildren().add(button0);

    }

    CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
    protected void acceptRequest(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            RMIClientServices.acceptFriendRequest(currentUserAccount.getPhoneNumber(),currentFriendRequest.getMobile());
            button.setText("Request Accepted");
            getChildren().remove(button0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    protected void rejectRequest(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            RMIClientServices.rejectFriendRequest(currentUserAccount.getPhoneNumber(),currentFriendRequest.getMobile());
            button0.setText("Request deleted");
            getChildren().remove(button);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

}
