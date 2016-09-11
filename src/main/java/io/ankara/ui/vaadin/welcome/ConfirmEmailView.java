package io.ankara.ui.vaadin.welcome;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.fields.EmailField;

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

    private EmailField emailField;

    public ConfirmEmailView() {
        setSizeUndefined();
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        Label message = new Label("<h2>Enter your email below so that we can resent confirmation email. <br/>" +
                "Once received the email click the cofirmation link to activate your account</h2>", ContentMode.HTML);

        emailField = new EmailField("Email");
        emailField.setWidth("300px");
        emailField.setInputPrompt("Enter your email");

        Button resend = new Button("Resend confirmation email");
        resend.addStyleName(ValoTheme.BUTTON_PRIMARY);
        resend.addClickListener((Button.ClickListener) event -> resendConfirmation());

        addComponents(message, emailField, resend);
    }

    private void resendConfirmation() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String email = (String) VaadinSession.getCurrent().getAttribute("emailConfirm");
        emailField.setValue(email);
    }
}
