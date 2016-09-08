package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.service.CompanyService;
import io.ankara.service.InvoiceService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.util.CompanySelectorWindow;
import io.ankara.ui.vaadin.util.SearchField;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:05 AM
 */
@UIScope
@SpringComponent
public class InvoicesHeader extends CustomComponent {

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private CompanyService companyService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @Inject
    private CompanySelectorWindow companySelectorWindow;

    @PostConstruct
    private void build() {
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");

        Button createButton = new Button("Create Invoice", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        createButton.addClickListener(event -> {
            showInvoiceCreateView();
        });
        content.addComponent(createButton);

        SearchField searchField = new SearchField();
        searchField.setWidth("100%");
        content.addComponent(searchField);
        content.setExpandRatio(searchField, 1);

        setCompositionRoot(content);
    }

    private void showInvoiceCreateView() {
        List<Company> companies = companyService.getCurrentUserCompanies();
        if (companies.isEmpty()) {
            //createCompany
        }else if(companies.size()==1){
            Company company = companies.stream().findFirst().get();
            navigateInvoiceCreateView(createInvoice(company));
        }else{
            companySelectorWindow.show(company -> {
                navigateInvoiceCreateView(createInvoice(company));
            });
        }

    }

    private Invoice createInvoice(Company company) {
        String currency = "TZS";
        String code = invoiceService.nextInvoiceNumber(company);
        User creator = userService.getCurrentUser();

        return new Invoice(creator, company, currency, code);
    }

    private void navigateInvoiceCreateView(Invoice invoice) {
        mainUI.getNavigator().navigateTo(InvoiceEditView.VIEW_NAME);
        eventBus.publish(InvoiceEditView.TOPIC_EDIT, this, invoice);
    }
}
