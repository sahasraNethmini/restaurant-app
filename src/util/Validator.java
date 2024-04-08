package util;

import javafx.scene.control.TextField;

public class Validator {

    public static boolean validateTextField(TextField field, String pattern) {

        String text = field.getText().trim();

        if (text.isEmpty()) {
            field.requestFocus();
            return false;
        }

        if (!text.matches(pattern)) {
            field.requestFocus();
            return false;
        }
        return true;
    }

}
