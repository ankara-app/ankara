package io.ankara.ui.vaadin.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/8/16.
 */
@PrototypeScope
@SpringComponent
public class LoginScreen extends CustomComponent {

    @Inject
    private VaadinSharedSecurity vaadinSecurity;

    private TextField userName;

    private PasswordField passwordField;

    private CheckBox rememberMe;

    private Button login;

    private Label loginFailedLabel;

    private Label loggedOutLabel;

    @PostConstruct
    protected void build() {
        FormLayout loginForm = new FormLayout();
        loginForm.setSizeUndefined();
        loginForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        userName = new TextField("Username");
        userName.setWidth("300px");
        passwordField = new PasswordField("Password");
        passwordField.setWidth("300px");
        rememberMe = new CheckBox("Remember me");
        login = new Button("Login");
        loginForm.addComponent(userName);
        loginForm.addComponent(passwordField);
        loginForm.addComponent(rememberMe);
        loginForm.addComponent(login);
        login.addStyleName(ValoTheme.BUTTON_PRIMARY);
        login.setDisableOnClick(true);
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                login();
            }
        });

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSizeUndefined();

        loginFailedLabel = new Label();
        loginLayout.addComponent(loginFailedLabel);
        loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
        loginFailedLabel.setSizeUndefined();
        loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        loginFailedLabel.setVisible(false);

        loggedOutLabel = new Label("Good bye!");
        loginLayout.addComponent(loggedOutLabel);
        loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);
        loggedOutLabel.setSizeUndefined();
        loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
        loggedOutLabel.setVisible(false);

        loginLayout.addComponent(loginForm);
        loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);

        VerticalLayout rootLayout = new VerticalLayout(loginLayout);
        rootLayout.setSizeFull();
        rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }

    private void login() {
        try {
            Authentication authentication = vaadinSecurity.login(userName.getValue(), passwordField.getValue(), rememberMe.getValue());
        } catch (AuthenticationException ex) {
            userName.focus();
            userName.selectAll();
            loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
            loginFailedLabel.setVisible(true);
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
        } finally {
            passwordField.setValue("");
            login.setEnabled(true);
        }
    }

}
