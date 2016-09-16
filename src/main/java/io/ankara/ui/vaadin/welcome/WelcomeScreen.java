package io.ankara.ui.vaadin.welcome;

import com.sun.org.apache.regexp.internal.RE;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
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
@UIScope
@SpringComponent
public class WelcomeScreen extends VerticalLayout implements ViewDisplay{

    @Inject
    private WelcomeUI welcomeUI;

    @Inject
    private SpringViewProvider springViewProvider;

    private Component currentComponent;

    @PostConstruct
    protected void build() {
        setSizeFull();

        Image image = new Image(null,new ThemeResource("img/logo.png"));
        addComponent(image);
        setComponentAlignment(image,Alignment.BOTTOM_CENTER);

        Navigator navigator = new Navigator(welcomeUI, (ViewDisplay)this);
        navigator.addProvider(springViewProvider);

        navigator.navigateTo(LoginForm.VIEW_NAME);
    }

    @Override
    public void showView(View view) {

        if (view instanceof Component) {
            Component component = (Component) view;

            if (currentComponent != null)
                removeComponent(currentComponent);
            addComponent(component);
//            setExpandRatio(component,1);
            setComponentAlignment(component, Alignment.TOP_CENTER);

            this.currentComponent = component;
        } else {
            throw new IllegalArgumentException("View is not a component: "+ view);
        }
    }
}
