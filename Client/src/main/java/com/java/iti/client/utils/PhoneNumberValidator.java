package com.java.iti.client.utils;

import com.jfoenix.validation.NumberValidator;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

public class PhoneNumberValidator extends NumberValidator {

    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalTextInputField();
        }

    }


    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) this.srcControl.get();

        try {
            Integer.parseInt(textField.getText());
            checkEgyptianPhoneNumber(textField.getText());
            this.hasErrors.set(false);
        } catch (Exception var3) {
            this.hasErrors.set(true);
        }
    }

    private void checkEgyptianPhoneNumber(String number) throws Exception {
        if (Pattern.compile("^01[0125][0-9]{8}$").matcher(number).matches()) {
            return;
        } else {
            throw new Exception();
        }
    }

}
