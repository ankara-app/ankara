package io.ankara.ui.vaadin.main.view.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 2:33 AM
 */
@SpringView(name =InvoiceView.VIEW_NAME)
public class InvoiceView extends CustomComponent implements View {
    public static final String VIEW_NAME = "";

    @Inject
    private InvoiceHeader invoiceHeader;
    @Inject
    private InvoiceTable invoiceTable;

    @PostConstruct
    private void build(){
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setSpacing(true);
        setCompositionRoot(content);

        invoiceHeader.setWidth("50%");
        content.addComponent(invoiceHeader);
        content.setComponentAlignment(invoiceHeader, Alignment.MIDDLE_RIGHT);

        invoiceTable.setSizeFull();
        content.addComponent(invoiceTable);
        content.setExpandRatio(invoiceTable,1);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
