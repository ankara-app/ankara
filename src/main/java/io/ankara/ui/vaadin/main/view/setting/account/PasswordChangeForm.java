package io.ankara.ui.vaadin.main.view.setting.account;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.User;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.util.PasswordValidator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 2:59 PM
 */
@UIScope
@SpringComponent
public class PasswordChangeForm extends FormLayout {

    private PasswordField passwordField;
    private PasswordField checkPasswordField;
    private Button changePasswordButton;


    @Inject
    private UserService userService;

    @PostConstruct
    private void build() {
        setSpacing(true);
        setMargin(true);
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        passwordField = new PasswordField("Enter new password");
        passwordField.addValidator(new PasswordValidator());
        passwordField.setWidth("300px");

        checkPasswordField = new PasswordField("Confirm new passoword");
        checkPasswordField.addValidator(new PasswordValidator());
        passwordField.setWidth("300px");

        changePasswordButton = new Button("Change Password", FontAwesome.KEY);
        changePasswordButton.setWidth("200px");
        changePasswordButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        changePasswordButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                User user = userService.getCurrentUser();
                if (isPasswordCorrect() && userService.changePassword(user, passwordField.getValue())) {
                    Notification.show("Your password is changed successfully", Notification.Type.TRAY_NOTIFICATION);
                    getUI().getPage().reload();
                } else {
                    Notification.show("Please enter your password correct", Notification.Type.WARNING_MESSAGE);
                }
            }
        });
        addComponents(passwordField, checkPasswordField, changePasswordButton);
        setComponentAlignment(changePasswordButton, Alignment.BOTTOM_RIGHT);
    }

    private boolean isPasswordCorrect() {
        return passwordField.isValid()
                && checkPasswordField.isValid()
                && passwordField.getValue().equals(checkPasswordField.getValue());
    }
}
