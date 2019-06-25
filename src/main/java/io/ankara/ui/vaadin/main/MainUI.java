package io.ankara.ui.vaadin.main;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.AnkaraUI;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import java.util.Locale;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/16 9:43 PM
 */
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
@SpringUI(path = "/app")
@Theme(AnkaraTheme.THEME)
@Push(transport = Transport.WEBSOCKET)
@PushStateNavigation
public class MainUI extends AnkaraUI {

    @Inject
    private ApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("ankara - Simplifying billing and documentation");
        VaadinSession.getCurrent().setLocale( new Locale("en", "EN"));

        setContent(applicationContext.getBean(MainScreen.class));
//        setContent(new Label("Welcome"));
    }

}
