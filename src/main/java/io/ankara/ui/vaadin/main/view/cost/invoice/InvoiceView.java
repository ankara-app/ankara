package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.spring.annotation.SpringView;
import io.ankara.domain.Invoice;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostView;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 7:09 PM
 */
@SpringView(name = InvoiceView.VIEW_NAME)
public class InvoiceView extends CostView<Invoice> {

    public static final String VIEW_NAME = "Invoice";
    public static final String TOPIC_SHOW = "Show Invoice";

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private User user;


    public InvoiceView() {
        super(Templates.INVOICE);
    }

    @EventBusListenerTopic(topic = InvoiceView.TOPIC_SHOW)
    @EventBusListenerMethod
    private void showInvoice(Invoice invoice){
        setCost(invoice);
        viewHeader.setValue(invoice.getCode()+" | "+invoice.getCustomer());
    }

    @Override
    protected void delete() {
        String confirmationMessage = "Are you sure that you want this invoice to be completely removed?";
        ConfirmDialog.show(getUI(), "Please confirm ...", confirmationMessage, "Proceed", "Cancel", (ConfirmDialog.Listener) confirmDialog -> {
            if (confirmDialog.isConfirmed()) {
                invoiceService.delete(getCost());
                mainUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
            }
        });
    }

    @Override
    protected void edit() {
        mainUI.getNavigator().navigateTo(InvoiceEditView.VIEW_NAME);
        eventBus.publish(InvoiceEditView.TOPIC_EDIT,this,invoiceService.getInvoice(getCost().getId()));
    }

    @Override
    protected void copy() {
        mainUI.getNavigator().navigateTo(InvoiceEditView.VIEW_NAME);
        eventBus.publish(InvoiceEditView.TOPIC_EDIT,this,getCopy());
    }

    private Invoice getCopy() {
        Invoice invoice = getCost().clone();
        invoice.setCode(invoiceService.nextInvoiceNumber(invoice.getCompany()));
        invoice.setCreator(user);
        return invoice;
    }

    @Override
    protected String getPrintURL(Invoice invoice) {
        return "/invoice/print/"+invoice.getId();
    }

    @Override
    protected String getPdfURL(Invoice invoice) {
        return "/invoice/pdf/"+invoice.getId();
    }
}
