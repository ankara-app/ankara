package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraUI;
import io.ankara.ui.vaadin.util.SearchField;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:05 AM
 */
@UIScope
@SpringComponent
public class InvoicesHeader extends CustomComponent {

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private AnkaraUI ankaraUI;

    @PostConstruct
    private void build() {
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");

        Button createButton = new Button("Create Invoice", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        createButton.addClickListener(event -> {
            ankaraUI.getNavigator().navigateTo(InvoiceFormView.VIEW_NAME);
            eventBus.publish(InvoiceFormView.TOPIC_CREATE,this,invoiceService.newInvoice());
        });
        content.addComponent(createButton);

        SearchField searchField = new SearchField();
        searchField.setWidth("100%");
        content.addComponent(searchField);
        content.setExpandRatio(searchField, 1);

        setCompositionRoot(content);
    }
}
