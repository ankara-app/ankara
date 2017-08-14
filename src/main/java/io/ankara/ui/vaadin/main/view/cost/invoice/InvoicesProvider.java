package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.view.cost.CostsProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.spring.annotation.PrototypeScope;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/12/17 2:13 PM
 */
@SpringComponent
@PrototypeScope
public class InvoicesProvider extends CostsProvider<Invoice>{
    private InvoiceService invoiceService;
    private UserService userService;

    public InvoicesProvider(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @Override
    protected Page<Invoice> getCosts(Query<Invoice, Void> query, String codeFilter, String customerNameFilter, String subjectFilter, Pageable pageable) {
        return invoiceService.getInvoices(userService.getCurrentUser(),codeFilter,customerNameFilter,subjectFilter,pageable);
    }

    @Override
    protected int countCosts(Query<Invoice, Void> query, String codeFilter, String customerNameFilter, String subjectFilter) {
        Long size = invoiceService.countInvoices(userService.getCurrentUser(),codeFilter,customerNameFilter,subjectFilter);
        return size.intValue();
    }
}
