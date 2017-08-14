package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostView;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimatesView;
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
@UIScope
@SpringView(name = InvoiceView.VIEW_NAME)
@SpringComponent
public class InvoiceView extends CostView{

    public static final String VIEW_NAME = "Invoice";
    public static final String TOPIC_SHOW = "Show Invoice";

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private InvoiceService invoiceService;

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
    protected void delete(Cost cost) {
        String confirmationMessage = "Are you sure that you want this invoice to be completely removed?";
        ConfirmDialog.show(getUI(), "Please confirm ...", confirmationMessage, "Proceed", "Cancel", (ConfirmDialog.Listener) confirmDialog -> {
            if (confirmDialog.isConfirmed()) {
                invoiceService.delete((Invoice)cost);
                mainUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
            }
        });
    }

    @Override
    protected void edit(Cost cost) {
        mainUI.getNavigator().navigateTo(InvoiceEditView.VIEW_NAME);
        eventBus.publish(InvoiceEditView.TOPIC_EDIT,this,invoiceService.getInvoice(cost.getId()));
    }

    @Override
    protected String getPrintURL(Cost cost) {
        return "/invoice/print/"+cost.getId();
    }

    @Override
    protected String getPdfURL(Cost cost) {
        return "/invoice/pdf/"+cost.getId();
    }
}
