package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Table;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.util.TableDecorator;
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
    private MainUI mainUI;

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private TableDecorator tableDecorator;

    @PostConstruct
    private void build(){
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setHeight("500px");
        setWidth("100%");

        BeanItemContainer container = new BeanItemContainer<>(Invoice.class);
        setContainerDataSource(container);

        addItemClickListener(event -> {
            Invoice invoice = (Invoice) event.getItemId();
            mainUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
            eventBus.publish(InvoiceView.TOPIC_SHOW,this,invoice);
        });

        tableDecorator.decorate(this, Templates.INVOICE_ENTRY,"Invoice");

    }

    public void reload() {
        BeanItemContainer container = getContainerDataSource();
        container.removeAllItems();
        container.addAll(invoiceService.getInvoices(userService.getCurrentUser()));

//        int size = container.size();
//        setPageLength(size > 10 ? 10 : size < 5 ? 5 : 10);
//        setPageLength(10);
    }

    @Override
    public BeanItemContainer getContainerDataSource() {
        return (BeanItemContainer) super.getContainerDataSource();
    }
}
