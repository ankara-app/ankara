package io.ankara.ui.vaadin.main.view.cost.invoice;


import com.vaadin.data.Property;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import io.ankara.domain.Company;
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
    public static final String TOPIC_CREATE = "Create Invoice";

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

    @EventBusListenerTopic(topic = TOPIC_CREATE)
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
            ankaraUI.getNavigator().navigateTo(InvoicesView.VIEW_NAME);
        }
    }

    @Override
    protected FormLayout createAssociatesForm() {
        FormLayout layout = super.createAssociatesForm();

        company.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Company company = (Company) event.getProperty().getValue();
                code.setValue(invoiceService.nextInvoiceNumber(company));
            }
        });

        return layout;
    }
}
