package io.ankara.ui.vaadin.util;


import com.vaadin.data.validator.StringLengthValidator;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 10:18 AM
 */
public class PasswordValidator extends StringLengthValidator {

    public PasswordValidator() {
        super("Password should have a minimum of five characters, make it strong", 5, -1);
    }
}
