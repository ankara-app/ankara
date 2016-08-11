package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.security.VaadinSecurity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 1:54 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SetupMenu extends CustomComponent {

    @Inject
    private VaadinSecurity vaadinSecurity;

    @PostConstruct
    private void build(){
        MenuBar menu = new MenuBar();
        menu.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuBar.MenuItem settings = menu.addItem("User", null);

        settings.addItem("Settings", null);
        settings.addSeparator();
        settings.addItem("Logout", selectedItem -> vaadinSecurity.logout());

        setCompositionRoot(menu);
        setWidth("100px");
    }
}
