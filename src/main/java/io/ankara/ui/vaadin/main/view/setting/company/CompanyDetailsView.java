package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.ui.vaadin.main.view.setting.customer.CustomerBeanCRUDComponent;
import io.ankara.ui.vaadin.main.view.setting.itemType.ItemTypeBeanCRUDComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 8:53 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyDetailsView extends VerticalLayout {

    @Inject
    private CompanyForm companyForm;

    @Inject
    private CustomerBeanCRUDComponent customerBeanCRUDComponent;

    @Inject
    private ItemTypeBeanCRUDComponent itemTypeBeanCRUDComponent;

    @PostConstruct
    private void build() {
        setSizeFull();
        setMargin(true);

        TabSheet detailsMenu = new TabSheet();
        detailsMenu.setSizeFull();
        detailsMenu.addTab(companyForm, "Company",FontAwesome.BUILDING);
        detailsMenu.addTab(customerBeanCRUDComponent, "Customers", FontAwesome.USERS);
        detailsMenu.addTab(itemTypeBeanCRUDComponent, "Cost Items", FontAwesome.BOOKMARK);
        detailsMenu.addTab(createStatusTab(), "Status", FontAwesome.STAR_HALF_O);

        addComponent(detailsMenu);
    }

    private Component createStatusTab() {
        return new VerticalLayout();
    }

    public void show(Company company) {
        companyForm.edit(company);
        customerBeanCRUDComponent.setCompany(company);
        itemTypeBeanCRUDComponent.setCompany(company);
    }
}
