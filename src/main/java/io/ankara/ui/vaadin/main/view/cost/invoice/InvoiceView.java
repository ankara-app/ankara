package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import io.ankara.domain.Cost;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.AnkaraUI;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.view.cost.CostView;
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
public class InvoiceView extends CostView implements View{

    public static final String VIEW_NAME = "Invoice";
    public static final String TOPIC_SHOW = "Show Invoice";

    @Inject
    private AnkaraUI ankaraUI;

    @Inject
    private InvoiceService invoiceService;

    public InvoiceView() {
        super(Templates.INVOICE);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @EventBusListenerTopic(topic = InvoiceView.TOPIC_SHOW)
    @EventBusListenerMethod
    private void showInvoice(Invoice invoice){
        setCost(invoice);
    }

    @Override
    protected void delete(Cost cost) {
        invoiceService.delete((Invoice)cost);
        ankaraUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
    }

    @Override
    protected void edit(Cost cost) {
        ankaraUI.getNavigator().navigateTo(InvoiceEditView.VIEW_NAME);
        eventBus.publish(InvoiceEditView.TOPIC_EDIT,this,cost);
    }
}
