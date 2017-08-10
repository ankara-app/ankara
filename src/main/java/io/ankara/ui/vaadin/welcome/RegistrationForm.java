package io.ankara.ui.vaadin.welcome;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.User;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.util.NotificationUtils;
import io.ankara.ui.vaadin.util.ViewLink;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.viritin.fields.EmailField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static io.ankara.ui.vaadin.welcome.RegistrationForm.VIEW_NAME;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@UIScope
@SpringView(name = VIEW_NAME)
@SpringComponent
public class RegistrationForm extends FormLayout implements View {
    public static final String VIEW_NAME = "RegistrationForm";

    private TextField fullName;

    private EmailField email;

    private PasswordField password;

    private Binder<User> userBinder = new Binder<>();

    private Button register;

    @Inject
    private UserService userService;

    @Inject
    private VaadinSecurity vaadinSecurity;

    @Inject
    private WelcomeUI welcomeUI;

    @PostConstruct
    protected void build() {

        setSizeUndefined();
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        fullName = new TextField("Full Name");
        fullName.setWidth("300px");

        email = new EmailField("Email");
        email.setWidth("300px");

        password = new PasswordField("Password");
        password.setWidth("300px");

        register = new Button("Register");
        register.addStyleName(ValoTheme.BUTTON_PRIMARY);
        register.setDisableOnClick(true);
        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        register.addClickListener((Button.ClickListener) event -> register());


        userBinder.bindInstanceFields(this);

        Label loginLabel = new Label("Already have an account?", ContentMode.HTML);
        loginLabel.addStyleName(AnkaraTheme.SIGNUP_LOGIN_LABEL);

        ViewLink login = new ViewLink("Login", LoginForm.VIEW_NAME);
        addComponents(fullName, email, password, register, new HorizontalLayout(loginLabel, login));
    }

    public void load() {
        userBinder.setBean(new User());
    }

    private void register() {
        User user = userBinder.getBean();
        try {
            if (userService.create(user)) {
                welcomeUI.getNavigator().navigateTo(LoginForm.VIEW_NAME);

                NotificationUtils.showSuccess(
                        "Your account is almost ready",
                        "We have sent you an email on you account, confirm your email by clicking the verification link");
            }
        } catch (IllegalArgumentException e) {
            NotificationUtils.showWarning("Failed to sign you up", e.getMessage());
        } catch (Exception e) {
            NotificationUtils.showWarning(
                    "Sorry, " + user.getFullName() + " we could not register you",
                    "Something went wrong during registration, please try again letter");
            LoggerFactory.getLogger(getClass()).error("Unexpected error while signup", e);
        } finally {
            register.setEnabled(true);
        }

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        load();
    }
}

