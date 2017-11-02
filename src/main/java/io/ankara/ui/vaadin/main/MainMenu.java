package io.ankara.ui.vaadin.main;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.User;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimatesView;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoicesView;
import io.ankara.ui.vaadin.main.view.setting.SettingView;
import io.ankara.ui.vaadin.util.MMenuBar;
import org.vaadin.spring.security.VaadinSecurity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 12:59 AM
 */

@UIScope
@SpringComponent
public class MainMenu extends CustomComponent {


    @Inject
    private VaadinSecurity vaadinSecurity;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    private MenuBar.MenuItem settings;
    private MMenuBar menuBar;

    @PostConstruct
    private void build() {
        setWidthUndefined();
        menuBar = new MMenuBar();
        menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        setCompositionRoot(menuBar);

        menuBar.addItem("Invoices", VaadinIcons.BOOK_DOLLAR, selectedItem -> {
            mainUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
        });

        menuBar.addItem("Estimates",VaadinIcons.NOTEBOOK, selectedItem -> {
            mainUI.getNavigator().navigateTo(EstimatesView.VIEW_NAME);
        });

        User user = userService.getCurrentUser();
        settings = menuBar.addItem(user.getFullName(), VaadinIcons.USER,null);

        settings.addItem("Settings",VaadinIcons.COGS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                mainUI.getNavigator().navigateTo(SettingView.VIEW_NAME);
            }
        });
        settings.addSeparator();
        settings.addItem("Logout",VaadinIcons.SIGN_OUT, selectedItem -> vaadinSecurity.logout());
    }

    public MenuBar.MenuItem getSettings() {
        return settings;
    }

    public MMenuBar getMenuBar() {
        return menuBar;
    }
}
