package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.ui.vaadin.main.view.setting.customer.CustomerBeanCRUDComponent;
import io.ankara.ui.vaadin.main.view.setting.itemType.ItemTypeBeanCRUDComponent;
import io.ankara.ui.vaadin.main.view.setting.tax.TaxBeanCRUDComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 8:53 AM
 */
@UIScope
@SpringComponent
public class CompanyDetailsView extends VerticalLayout {

    @Inject
    private CompanyForm companyForm;

    @Inject
    private CustomerBeanCRUDComponent customerBeanCRUDComponent;

    @Inject
    private ItemTypeBeanCRUDComponent itemTypeBeanCRUDComponent;

    @Inject
    private TaxBeanCRUDComponent taxBeanCRUDComponent;

    @PostConstruct
    private void build() {
        setSizeFull();
        setMargin(true);

        TabSheet detailsMenu = new TabSheet();
        detailsMenu.setSizeFull();
        detailsMenu.addTab(companyForm, "Company",FontAwesome.BUILDING);
        detailsMenu.addTab(customerBeanCRUDComponent, "Customers", FontAwesome.USERS);
        detailsMenu.addTab(itemTypeBeanCRUDComponent, "Cost Items", FontAwesome.BOOKMARK);
        detailsMenu.addTab(taxBeanCRUDComponent, "Taxes", FontAwesome.CALCULATOR);

        addComponent(detailsMenu);
    }

    public void show(Company company) {
        companyForm.edit(company);
        customerBeanCRUDComponent.setCompany(company);
        itemTypeBeanCRUDComponent.setCompany(company);
        taxBeanCRUDComponent.setCompany(company);
    }
}
