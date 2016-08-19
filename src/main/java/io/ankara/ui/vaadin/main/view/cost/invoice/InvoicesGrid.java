package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:32 AM
 */

@UIScope
@SpringComponent
public class InvoicesGrid extends Grid {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private UserService userService;

    @PostConstruct
    private void build(){
        setContainerDataSource(new BeanItemContainer<>(Invoice.class));
        setColumns("code", "timeCreated", "company", "customer","creator","subject");
    }

    public void reload() {
        BeanItemContainer container = (BeanItemContainer) getContainerDataSource();
        container.addAll(invoiceService.getInvoices(userService.getCurrentUser()));
    }
}
