package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Grid;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.CostsTable;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimatesTable;
import io.ankara.utils.DateUtils;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:32 AM
 */

@SpringComponent
@ViewScope
public class InvoicesTable extends CostsTable<Invoice> {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @Inject
    private EventBus.UIEventBus eventBus;


    @PostConstruct
    public void build() {
        super.build();

        addColumn(Invoice::getPurchaseOrder).setCaption("Purchase Order").setHidable(true).setHidden(true);
        addColumn(invoice-> DateUtils.formatDate(invoice.getDueDate())).setCaption("Due date").setHidable(true).setHidden(true);


        addItemClickListener(event -> {
            Invoice invoice = event.getItem();
            mainUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
            eventBus.publish(InvoiceView.TOPIC_SHOW, this, invoice);
        });


    }

    public void reload() {
        //TODO IMPLEMENT LAZY LOADING
        setItems(invoiceService.getInvoices(userService.getCurrentUser()));
    }

}
