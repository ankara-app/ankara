package io.ankara.ui.vaadin.main.view.cost.invoice;


import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import io.ankara.domain.Cost;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostEditView;
import io.ankara.ui.vaadin.util.NotificationUtils;
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

    private DateField dueDate;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    public InvoiceEditView() {
        super(Invoice.class);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @EventBusListenerTopic(topic = TOPIC_EDIT)
    @EventBusListenerMethod
    private void editInvoice(Invoice invoice){
        editCost(invoice);
        viewHeader.setValue(invoice.getCompany()+" Invoice");
    }

    @Override
    protected FormLayout createCostDetailsForm() {
        FormLayout costDetailsLayout =  super.createCostDetailsForm();

        purchaseOrder = new TextField("PO Number");
        purchaseOrder.setPlaceholder("Enter PO Number");
        purchaseOrder.setWidth("100%");

        dueDate = new DateField("Due Date");
        dueDate.setDescription("Specify invoice due date");
        dueDate.setWidth("100%");

        costDetailsLayout.addComponents(dueDate,purchaseOrder);
        return costDetailsLayout;
    }

    @Override
    protected void doSave(Cost cost) {
        Invoice invoice = (Invoice) cost;
        if(invoiceService.save(invoice)){
            NotificationUtils.showSuccess("Invoice saved successfully", null);
            mainUI.getNavigator().navigateTo(InvoiceView.VIEW_NAME);
            eventBus.publish(InvoiceView.TOPIC_SHOW,this,invoice);
        }
    }

}
