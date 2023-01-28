package com.java.iti.client.utils;

import com.jfoenix.validation.NumberValidator;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

public class PasswordValidator extends NumberValidator {
    private String password;

    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalTextInputField();
        }

    }

    public PasswordValidator(String password) {
        super();
        this.password = password;
    }

    public PasswordValidator() {
        super();
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) this.srcControl.get();
        if (password == null) {
            try {
                checkPasswordSecurity(textField.getText());
                this.hasErrors.set(false);
            } catch (Exception var3) {
                this.hasErrors.set(true);
            }
        } else {
            try {
                checkConfirmPassword();
                this.hasErrors.set(false);
            } catch (Exception var3) {
                this.hasErrors.set(true);
            }
        }
    }

    private void checkPasswordSecurity(String number) throws Exception {
        if (Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$").matcher(number).matches()) {
            return;
        } else {
            throw new Exception();
        }
    }

    private void checkConfirmPassword() throws Exception {
        TextInputControl textField = (TextInputControl) this.srcControl.get();
        if (!textField.getText().equals(this.password))
        throw new Exception();

    }
}
