package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.CostsHeader;
import org.vaadin.spring.events.EventBus;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:05 AM
 */
@UIScope
@SpringComponent
public class InvoicesHeader extends CostsHeader {

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

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
