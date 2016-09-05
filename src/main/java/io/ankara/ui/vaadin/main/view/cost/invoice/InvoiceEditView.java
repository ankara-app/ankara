package io.ankara.ui.vaadin.main.view.cost.invoice;


import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import io.ankara.domain.Cost;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.AnkaraUI;
import io.ankara.ui.vaadin.main.view.cost.CostEditView;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 7:59 PM
 */
@UIScope
@SpringView(name = InvoiceEditView.VIEW_NAME)
@SpringComponent
public class InvoiceEditView extends CostEditView {
    public static final String VIEW_NAME = "InvoiceForm";
    public static final String TOPIC_EDIT = "Create Invoice";

    private TextField purchaseOrder;

    private DateField issueDate;

    private DateField dueDate;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private AnkaraUI ankaraUI;


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @EventBusListenerTopic(topic = TOPIC_EDIT)
    @EventBusListenerMethod
    private void editInvoice(Invoice invoice){
        editCost(invoice);
    }

    @Override
    protected FormLayout createCostDetailsForm() {
        FormLayout costDetailsLayout =  super.createCostDetailsForm();

        purchaseOrder = new TextField("PO Number");
        purchaseOrder.setInputPrompt("Enter PO Number");
        purchaseOrder.setWidth("100%");
        purchaseOrder.setNullRepresentation("");

        issueDate = new DateField("Issue Date");
        issueDate.setDescription("Specify invoice issue date");
        issueDate.setWidth("100%");

        dueDate = new DateField("Due Date");
        dueDate.setDescription("Specify invoice due date");
        dueDate.setWidth("100%");

        costDetailsLayout.addComponents(purchaseOrder,issueDate,dueDate);
        return costDetailsLayout;
    }

    @Override
    protected void doSave(Cost cost) {
        Invoice invoice = (Invoice) cost;
        if(invoiceService.save(invoice)){
            Notification.show("Invoice saved successfully", Notification.Type.TRAY_NOTIFICATION);
            ankaraUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
            eventBus.publish(InvoiceView.TOPIC_SHOW,this,invoice);
        }
    }

}
