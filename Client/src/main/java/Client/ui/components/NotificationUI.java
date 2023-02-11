package Client.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;

public class NotificationUI extends HBox {
    protected final HBox hbox;

    protected final Label label;

    public NotificationUI(String count, boolean error) {

        hbox = new HBox();
        label = new Label();

        if (error)
            setStyle("-fx-background-color: #ffacac; -fx-background-radius: 10px;");
        else
            setStyle("-fx-background-color: #10ff70; -fx-background-radius: 10px;");

//        hbox.setHgrow(FA);
        setAlignment(Pos.CENTER);
        hbox.setMaxWidth(USE_COMPUTED_SIZE);
        hbox.setMinWidth(USE_COMPUTED_SIZE);
        hbox.setPrefWidth(USE_COMPUTED_SIZE);

        hbox.setMinHeight(USE_COMPUTED_SIZE);
        hbox.setPrefHeight(USE_COMPUTED_SIZE);

        label.setText(count + " new");
        if (error)
            label.setTextFill(javafx.scene.paint.Color.valueOf("#a20000"));
        else
            label.setTextFill(javafx.scene.paint.Color.valueOf("069A59FF"));
        setPadding(new Insets(2.0, 7.0, 2.0, 7.0));

        label.setFont(new Font("Segoe UI", 9.0));

        getChildren().add(label);

    }
}
