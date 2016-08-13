package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
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
    private InvoiceGrid invoiceGrid;

    @PostConstruct
    private void build(){
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setSpacing(true);
        setCompositionRoot(content);

        invoiceHeader.setWidth("50%");
        content.addComponent(invoiceHeader);

        invoiceGrid.setSizeFull();
        content.addComponent(invoiceGrid);
        content.setExpandRatio(invoiceGrid,1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
