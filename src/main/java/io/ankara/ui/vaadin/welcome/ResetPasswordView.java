package io.ankara.ui.vaadin.welcome;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.util.NotificationUtils;
import io.ankara.ui.vaadin.util.VaadinUtils;
import org.vaadin.viritin.fields.EmailField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static io.ankara.ui.vaadin.welcome.ResetPasswordView.VIEW_NAME;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@UIScope
@SpringView(name = VIEW_NAME)
@SpringComponent
public class ResetPasswordView extends FormLayout implements View {
    public static final String VIEW_NAME = "ResetPassword";
    @Inject
    private UserService userService;
    private EmailField emailField;

    @PostConstruct
    public void build() {
        setSizeUndefined();
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        Label message = new Label("<h3>We will email you a password.<br/> You will be required to change to your own once you login</h3>", ContentMode.HTML);

        emailField = new EmailField("Email");
        emailField.setWidth("300px");
        VaadinUtils.addValidator(emailField, new EmailValidator("Enter a correct email"));
        emailField.setDescription("Enter your email");

        Button resend = new Button("Reset password");
        resend.addStyleName(ValoTheme.BUTTON_PRIMARY);
        resend.addClickListener((Button.ClickListener) event -> resetPassword());

        addComponents(message, emailField, resend);
    }

    private void resetPassword() {
        if (emailField.getErrorMessage() == null) {
            String email = emailField.getValue();

            try {
                if (userService.resetPassword(email)) {
                    NotificationUtils.showSuccess(
                            "Your password have been reset",
                            "We have emailed you a password.<br/> You will be required to change to your own once you login");
                }
            } catch (IllegalArgumentException e) {
                NotificationUtils.showWarning("Failed to reset your password", e.getMessage());
            }
        } else {
            NotificationUtils.showWarning("Enter correct email", null);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
