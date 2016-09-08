package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimateView;
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
    private MainUI ankaraUI;

    @PostConstruct
    private void build() {
        MenuBar menuBar = new MenuBar();
        menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        menuBar.setWidth("100%");
        setCompositionRoot(menuBar);

        menuBar.addItem("Invoices", selectedItem -> {
            ankaraUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
        });

        menuBar.addItem("Estimates", selectedItem -> {
            ankaraUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
        });

        menuBar.addItem("Settings", selectedItem -> {
            ankaraUI.getNavigator().navigateTo(SettingView.VIEW_NAME);
        });
    }
}
