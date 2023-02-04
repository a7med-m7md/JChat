package Server.controllerutils;

import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.TextInputControl;

public class PasswordAndConfirmPasswordValidator extends RequiredFieldValidator {

    private TextInputControl passwordField;

    public PasswordAndConfirmPasswordValidator(TextInputControl passwordField) {
        this.passwordField = passwordField;
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl confirmPasswordField = (TextInputControl) srcControl.get();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password == null || password.isEmpty()) {
            hasErrors.set(true);
        } else if (password.length() < 8) {
            hasErrors.set(true);
        } else if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*")) {
            hasErrors.set(true);
        } else if (!password.equals(confirmPassword)) {
            hasErrors.set(true);
        } else {
            hasErrors.set(false);
        }
    }
}
