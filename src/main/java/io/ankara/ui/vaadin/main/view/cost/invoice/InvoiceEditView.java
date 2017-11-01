package io.ankara.ui.vaadin.main.view.cost.invoice;


import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import io.ankara.domain.Cost;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.ViewHeader;
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

@SpringView(name = InvoiceEditView.VIEW_NAME)
public class InvoiceEditView extends CostEditView {
    public static final String VIEW_NAME = "InvoiceForm";
    public static final String TOPIC_EDIT = "Create Invoice";

    private TextField purchaseOrder;

    private DateField dueDateField;

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

        dueDateField = new DateField("Due Date");
        dueDateField.setPlaceholder("Specify invoice due date");
        dueDateField.setWidth("100%");
        getCostBinder()
                .forField(dueDateField)
                .withConverter(new LocalDateToDateConverter())
                .bind("dueDate");

        costDetailsLayout.addComponents(dueDateField,purchaseOrder);
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
