package io.ankara.ui.vaadin.welcome;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
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

import javax.inject.Inject;

import static io.ankara.ui.vaadin.welcome.ConfirmEmailView.VIEW_NAME;


/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@UIScope
@SpringView(name = VIEW_NAME)
@SpringComponent
public class ConfirmEmailView extends FormLayout implements View {
    public static final String VIEW_NAME = "ConfirmEmail";

    @Inject
    private UserService userService;
    private EmailField emailField;

    public ConfirmEmailView() {
        setSizeUndefined();
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        Label message = new Label("<h3>Enter your email below so that we can email you a confirmation link. <br/>" +
                "Once received click the cofirmation link to activate your account</h3>", ContentMode.HTML);

        emailField = new EmailField("Email");
        VaadinUtils.addValidator(emailField,new EmailValidator("Enter your email correctly"));
        emailField.setWidth("300px");
        emailField.setDescription("Enter your email");

        Button resend = new Button("Resend confirmation email");
        resend.addStyleName(ValoTheme.BUTTON_PRIMARY);
        resend.addClickListener((Button.ClickListener) event -> resendConfirmation());

        addComponents(message, emailField, resend);
    }

    private void resendConfirmation() {
        if(emailField.getErrorMessage() == null){
            String email = emailField.getValue();
            try{
              if(userService.requestVerification(email)){
                  NotificationUtils.showSuccess(
                          "We have email you a verification link",
                          "Open the email and  click the cofirmation link to activate your account");
              }
            }catch (IllegalArgumentException e){
                NotificationUtils.showWarning("Failed to send confirmation link",e.getMessage());
            }
        }else{
            NotificationUtils.showWarning(
                    "Enter a correct email",null);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String email = (String) VaadinSession.getCurrent().getAttribute("emailConfirm");
        emailField.setValue(email);
    }
}
