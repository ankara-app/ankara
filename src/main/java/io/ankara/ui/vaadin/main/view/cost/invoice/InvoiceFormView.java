package io.ankara.ui.vaadin.main.view.cost.invoice;


import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import io.ankara.domain.Invoice;
import io.ankara.ui.vaadin.main.view.cost.CostFormView;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 7:59 PM
 */
@UIScope
@SpringView(name = InvoiceFormView.VIEW_NAME)
public class InvoiceFormView extends CostFormView {
    public static final String VIEW_NAME = "InvoiceForm";
    public static final String TOPIC_CREATE = "Create Invoice";

    private TextField purchaseOrder;

    private DateField issueDate;

    private DateField dueDate;


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

        purchaseOrder = new TextField();
        purchaseOrder.setInputPrompt("Enter PO Number");
        purchaseOrder.setWidth("100%");
        purchaseOrder.setNullRepresentation("");

        issueDate = new DateField("Issue Date");
        issueDate.setDescription("Specify invoice issue date");
        issueDate.setWidth("100%");

        dueDate = new DateField("Due Date");
        dueDate.setDescription("Specify invoice due date");
        dueDate.setWidth("100%");

        HorizontalLayout dates = new HorizontalLayout(issueDate,dueDate);
        dates.setWidth("100%");
        dates.setSpacing(true);
        dates.setMargin(new MarginInfo(true,false,false,false));
        costDetailsLayout.addComponents(purchaseOrder,dates);
        return costDetailsLayout;
    }
}
