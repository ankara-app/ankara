package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
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
public class InvoicesTable extends Grid<Invoice> {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @Inject
    private EventBus.UIEventBus eventBus;


    @PostConstruct
    private void build(){
        setHeight("500px");
        setWidth("100%");


        addItemClickListener(event -> {
            Invoice invoice = event.getItem();
            mainUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
            eventBus.publish(InvoiceView.TOPIC_SHOW,this,invoice);
        });


    }

    public void reload() {
        //TODO IMPLEMENT LAZY LOADING
        setItems(invoiceService.getInvoices(userService.getCurrentUser()));
    }

}
