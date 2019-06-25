package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostsTable;
import io.ankara.ui.vaadin.main.view.cost.CostsView;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 2:33 AM
 */
@SpringView(name = InvoicesView.VIEW_NAME)
public class InvoicesView extends CostsView<Invoice>{
    public static final String VIEW_NAME = "InvoicesView";


    @Inject
    private InvoicesTable invoicesTable;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @PostConstruct
    protected void build(){
        super.build();
    }

    @Override
    protected CostsTable<Invoice> getCostsTable() {
        return invoicesTable;
    }


    @Override
    public void attach() {
        super.attach();
        createMenuItem.setText("Create Invoice");
        viewHeader.setValue("Invoices");
    }

    private Invoice createInvoice(Company company) {
        String currency = "TZS";
        String code = invoiceService.nextInvoiceNumber(company);
        User creator = userService.getCurrentUser();

        return new Invoice(creator, company, currency, code);
    }

    private void navigateInvoiceCreateView(Invoice invoice) {
        mainUI.getNavigator().navigateTo(InvoiceEditView.VIEW_NAME);
        eventBus.publish(InvoiceEditView.TOPIC_EDIT, this, invoice);
    }

    @Override
    protected void showCostCreateView(Company company) {
        navigateInvoiceCreateView(createInvoice(company));
    }
}
