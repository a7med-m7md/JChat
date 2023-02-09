package Client.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.SVGPath;

public class ErrorMessageUi extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints;
    protected final SVGPath sVGPath;
    protected final Label label;

    public ErrorMessageUi(String errorLabel) {

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        sVGPath = new SVGPath();
        label = new Label();

        setStyle("-fx-background-color: #ffacac; -fx-background-radius: 5px;");

        columnConstraints.setFillWidth(false);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints.setMaxWidth(30.399998092651362);
        columnConstraints.setMinWidth(23.19998779296875);
        columnConstraints.setPrefWidth(24.00003662109375);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints0.setMaxWidth(Double.MAX_VALUE);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        sVGPath.setContent("M7.91 3.23 3.23 7.913v-.01a.81.81 0 0 0-.23.57v7.054c0 .22.08.42.23.57L7.9 20.77c.15.15.36.23.57.23h7.06c.22 0 .42-.08.57-.23l4.67-4.673a.81.81 0 0 0 .23-.57V8.473c0-.22-.08-.42-.23-.57L16.1 3.23a.81.81 0 0 0-.57-.23H8.48c-.22 0-.42.08-.57.23ZM12 7a1 1 0 0 1 1 1v5a1 1 0 1 1-2 0V8a1 1 0 0 1 1-1Zm-1 9a1 1 0 0 1 1-1h.008a1 1 0 1 1 0 2H12a1 1 0 0 1-1-1Z");
        sVGPath.setFill(javafx.scene.paint.Color.valueOf("#ff6868"));

        GridPane.setColumnIndex(label, 1);
        label.setText(errorLabel);
        label.setTextFill(javafx.scene.paint.Color.valueOf("#a20000"));
        setPadding(new Insets(2.0, 7.0, 2.0, 7.0));

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getRowConstraints().add(rowConstraints);
        getChildren().add(sVGPath);
        getChildren().add(label);

    }
}
