package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.User;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraUI;
import io.ankara.ui.vaadin.main.view.setting.SettingView;
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

    @Inject
    private UserService userService;

    @Inject
    private AnkaraUI ankaraUI;

    private MenuBar.MenuItem settings;

    @PostConstruct
    private void build() {
        MenuBar menu = new MenuBar();
        menu.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

        User user = userService.getCurrentUser();

        settings = menu.addItem(user.getFullName(), null);

        settings.addItem("Settings", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                ankaraUI.getNavigator().navigateTo(SettingView.VIEW_NAME);
            }
        });
        settings.addSeparator();
        settings.addItem("Logout", selectedItem -> vaadinSecurity.logout());

        setCompositionRoot(menu);
        setWidth("100px");
    }

}
