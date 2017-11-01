package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimatesView;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoicesView;
import io.ankara.ui.vaadin.main.view.setting.SettingView;

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
    private MainUI mainUI;

    @PostConstruct
    private void build() {
        MenuBar menuBar = new MenuBar();
        menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        menuBar.setWidth("100%");
        setCompositionRoot(menuBar);

        menuBar.addItem("Invoices", selectedItem -> {
            mainUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
        });

        menuBar.addItem("Estimates", selectedItem -> {
            mainUI.getNavigator().navigateTo(EstimatesView.VIEW_NAME);
        });

        menuBar.addItem("Settings", selectedItem -> {
            mainUI.getNavigator().navigateTo(SettingView.VIEW_NAME);
        });
    }
}
