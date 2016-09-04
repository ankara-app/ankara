package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Table;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraUI;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:32 AM
 */

@UIScope
@SpringComponent
public class InvoicesTable extends Table {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private UserService userService;

    @Inject
    private AnkaraUI ankaraUI;

    @Inject
    private EventBus.UIEventBus eventBus;

    @PostConstruct
    private void build(){
        setContainerDataSource(new BeanItemContainer<>(Invoice.class));
        setVisibleColumns("code", "timeCreated", "company", "customer","creator","subject");

        addItemClickListener(event -> {
            Invoice invoice = (Invoice) event.getItemId();
            ankaraUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
            eventBus.publish(InvoiceView.TOPIC_SHOW,this,invoice);
        });
    }

    public void reload() {
        BeanItemContainer container = (BeanItemContainer) getContainerDataSource();
        container.addAll(invoiceService.getInvoices(userService.getCurrentUser()));
    }
}
