package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Invoice;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostsTable;
import io.ankara.ui.vaadin.main.view.cost.CostsView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 2:33 AM
 */
@SpringView(name = InvoicesView.VIEW_NAME)
public class InvoicesView extends CostsView<Invoice>{
    public static final String VIEW_NAME = "InvoicesView";

    @Inject
    private InvoicesHeader invoicesHeader;

    @Inject
    private InvoicesTable invoicesTable;

    @Inject
    private ViewHeader viewHeader;

    @PostConstruct
    protected void build(){
        super.build();
    }

    @Override
    protected CostsTable<Invoice> getCostsTable() {
        return invoicesTable;
    }

    @Override
    protected Component getHeader() {
        return invoicesHeader;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        viewHeader.setValue("Invoices");
    }
}
