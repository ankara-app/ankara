package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import io.ankara.ui.vaadin.main.view.ViewHeader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 2:33 AM
 */
@UIScope
@SpringView(name = InvoicesView.VIEW_NAME)
public class InvoicesView extends CustomComponent implements View {
    public static final String VIEW_NAME = "InvoicesView";

    @Inject
    private InvoicesHeader invoicesHeader;

    @Inject
    private InvoicesTable invoicesTable;

    @Inject
    private ViewHeader viewHeader;

    @PostConstruct
    private void build(){
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setSpacing(true);
        setCompositionRoot(content);

        invoicesHeader.setWidth("50%");
        content.addComponent(invoicesHeader);

        invoicesTable.setSizeFull();
        content.addComponent(invoicesTable);
        content.setExpandRatio(invoicesTable,1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        invoicesTable.reload();
        viewHeader.setValue("Invoices");
    }
}
