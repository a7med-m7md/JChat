package Client.ui.controllerutils;

import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.TextInputControl;

public class EmailValidator extends RequiredFieldValidator {

    private TextInputControl passwordField;


    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl emailTextField = (TextInputControl) srcControl.get();
        String emailAddress = emailTextField.getText();

        if (emailAddress == null || emailAddress.isEmpty()) {
            hasErrors.set(true);
        } else if (!emailAddress.matches("^[^@]+@[^@]+\\.[^@]+$"))
        {
            hasErrors.set(true);
        } else {
            hasErrors.set(false);
        }
    }
}
