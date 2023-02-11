package Client.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

public class ErrorMessageUi extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints;
    protected final SVGPath sVGPath;
    protected final Label label;

    public ErrorMessageUi(String errorLabel, boolean error) {

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        sVGPath = new SVGPath();
        label = new Label();

        if (error)
            setStyle("-fx-background-color: #ffacac; -fx-background-radius: 5px;");
        else
            setStyle("-fx-background-color: #10ff70; -fx-background-radius: 5px;");

        columnConstraints.setFillWidth(false);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints.setMaxWidth(30.399998092651362);
        columnConstraints.setMinWidth(23.19998779296875);
        columnConstraints.setPrefWidth(24.00003662109375);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints0.setMaxWidth(Double.MAX_VALUE);

        rowConstraints.setMinHeight(30.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        if (error) {
            sVGPath.setContent("M7.91 3.23 3.23 7.913v-.01a.81.81 0 0 0-.23.57v7.054c0 .22.08.42.23.57L7.9 20.77c.15.15.36.23.57.23h7.06c.22 0 .42-.08.57-.23l4.67-4.673a.81.81 0 0 0 .23-.57V8.473c0-.22-.08-.42-.23-.57L16.1 3.23a.81.81 0 0 0-.57-.23H8.48c-.22 0-.42.08-.57.23ZM12 7a1 1 0 0 1 1 1v5a1 1 0 1 1-2 0V8a1 1 0 0 1 1-1Zm-1 9a1 1 0 0 1 1-1h.008a1 1 0 1 1 0 2H12a1 1 0 0 1-1-1Z");
            sVGPath.setFill(javafx.scene.paint.Color.valueOf("#ff6868"));
        } else {
            sVGPath.setContent("M12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21ZM16.7682 9.64018C17.1218 9.21591 17.0645 8.58534 16.6402 8.23178C16.2159 7.87821 15.5853 7.93554 15.2318 8.35982L11.6338 12.6774C11.2871 13.0934 11.0922 13.3238 10.9366 13.4653L10.9306 13.4707L10.9242 13.4659C10.7564 13.339 10.5415 13.1272 10.1585 12.7443L8.70711 11.2929C8.31658 10.9024 7.68342 10.9024 7.29289 11.2929C6.90237 11.6834 6.90237 12.3166 7.29289 12.7071L8.74428 14.1585L8.78511 14.1993L8.78512 14.1993C9.11161 14.526 9.4257 14.8402 9.71794 15.0611C10.0453 15.3087 10.474 15.5415 11.0234 15.5165C11.5728 15.4916 11.9787 15.221 12.2823 14.9448C12.5534 14.6983 12.8377 14.3569 13.1333 14.0021L13.1333 14.0021L13.1703 13.9577L16.7682 9.64018Z");
            sVGPath.setFill(javafx.scene.paint.Color.valueOf("069A59FF"));
            sVGPath.setFillRule(FillRule.EVEN_ODD);

        }

        GridPane.setColumnIndex(label, 1);
        label.setText(errorLabel);
        if (error)
            label.setTextFill(javafx.scene.paint.Color.valueOf("#a20000"));
        else
            label.setTextFill(javafx.scene.paint.Color.valueOf("069A59FF"));
        setPadding(new Insets(2.0, 7.0, 2.0, 7.0));

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getRowConstraints().add(rowConstraints);
        getChildren().add(sVGPath);
        getChildren().add(label);

    }
}
