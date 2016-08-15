package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimateView;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoicesView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 12:59 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainMenu extends CustomComponent {

    @Inject
    private UI ankaraUI;

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
        menuBar.addItem("Reports", selectedItem -> {

        });
    }
}
