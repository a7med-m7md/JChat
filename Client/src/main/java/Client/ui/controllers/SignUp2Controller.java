package Client.ui.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class SignUp2Controller {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private JFXTextField signUpPhoneNumberField;

    @FXML
    private JFXComboBox<?> countryComboBox;

    @FXML
    private JFXComboBox<?> genderComboBox;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Button signUpNextBtn2;

    @FXML
    void handleNextBtn(MouseEvent event) throws IOException {
        Parent signUpPane3 = FXMLLoader.load(getClass().getResource("/FXML/sign-up-3.fxml"));
        Scene currentScene = signUpNextBtn2.getScene();
        signUpPane3.translateXProperty().set(currentScene.getWidth());

        signInPane.getChildren().add(signUpPane3);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(signUpPane3.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(.7), kv);
        timeline.getKeyFrames().add(kf);
//            timeline.setOnFinished(t -> {
//                LoginController.s.getChildren().remove(signUpPane2);
//            });
        timeline.play();

    }

}
