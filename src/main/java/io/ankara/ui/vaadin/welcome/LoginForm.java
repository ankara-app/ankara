package io.ankara.ui.vaadin.welcome;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.User;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoiceEditView;
import io.ankara.ui.vaadin.util.NotificationUtils;
import io.ankara.ui.vaadin.util.ViewLink;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static io.ankara.ui.vaadin.welcome.LoginForm.VIEW_NAME;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/10/16.
 */

@UIScope
@SpringView(name = VIEW_NAME)
@SpringComponent
public class LoginForm extends FormLayout implements View {
    public static final String VIEW_NAME = "LoginForm";

    @Inject
    private VaadinSharedSecurity vaadinSecurity;

    @Inject
    private UserService userService;

    @Inject
    private WelcomeUI welcomeUI;

    private TextField email;

    private PasswordField password;

    private CheckBox rememberMe;

    private Button login;

    @PostConstruct
    protected void build() {

        setSizeUndefined();
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        email = new TextField("Email");
        email.setWidth("300px");

        password = new PasswordField("Password");
        password.setWidth("300px");

        rememberMe = new CheckBox("Remember me");

        login = new Button("Login");
        login.addStyleName(ValoTheme.BUTTON_PRIMARY);
        login.setDisableOnClick(true);
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addClickListener((Button.ClickListener) event -> login());

        Label signUpLabel = new Label("Don't have an account?", ContentMode.HTML);
        signUpLabel.addStyleName(AnkaraTheme.SIGNUP_LOGIN_LABEL);


        Label forgetPasswordLabel = new Label("Forgot Password?", ContentMode.HTML);
        forgetPasswordLabel.addStyleName(AnkaraTheme.SIGNUP_LOGIN_LABEL);

        addComponents(
                email, password, rememberMe, login,
                new HorizontalLayout(signUpLabel, new ViewLink("Sign Up", RegistrationForm.VIEW_NAME)),
                new HorizontalLayout(forgetPasswordLabel, new ViewLink("Reset", ResetPasswordView.VIEW_NAME))
        );

    }

    private void login() {
        String email = this.email.getValue();
        String password = this.password.getValue();
        Boolean rememberMe = this.rememberMe.getValue();

        try {
            Authentication authentication = vaadinSecurity.login(email, password, rememberMe);
        } catch (DisabledException e) {
            VaadinSession.getCurrent().setAttribute("emailConfirm",email);
            welcomeUI.getNavigator().navigateTo(ConfirmEmailView.VIEW_NAME);
        } catch (LockedException e) {
            NotificationUtils.showWarning(
                    "Sorry, your account has been locked",
                    "Contact us for further assistance");
        } catch (BadCredentialsException e) {
            NotificationUtils.showWarning(
                    "Wrong username and/or password",
                    "Enter your credentials correctly, if you have forgot your password then click link below to reset");
        } catch (Exception e) {
            NotificationUtils.showWarning(
                    "Sorry, we could not sign you in",
                    "Something went wrong, please try again letter");
            LoggerFactory.getLogger(getClass()).error("Unexpected error while signup", e);
        } finally {
            this.email.focus();
            login.setEnabled(true);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
