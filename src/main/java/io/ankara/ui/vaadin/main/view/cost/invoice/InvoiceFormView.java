package io.ankara.ui.vaadin.main.view.cost.invoice;


import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import io.ankara.ui.vaadin.main.view.cost.CostFormView;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 7:59 PM
 */
@SpringView(name = InvoiceFormView.VIEW_NAME)
public class InvoiceFormView extends CostFormView {
    public static final String VIEW_NAME = "InvoiceForm";

    private TextField purchaseOrder;

    private DateField issueDate;

    private DateField dueDate;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
