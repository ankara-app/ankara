package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import io.ankara.ui.vaadin.main.view.estimate.EstimateView;
import io.ankara.ui.vaadin.main.view.invoice.InvoiceView;
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
        menuBar.setWidth("100%");
        setCompositionRoot(menuBar);

        menuBar.addItem("Invoices", selectedItem -> {
            ankaraUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
        });
        menuBar.addItem("Estimates", selectedItem -> {
            ankaraUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
        });
        menuBar.addItem("Reports", selectedItem -> {

        });
    }
}
