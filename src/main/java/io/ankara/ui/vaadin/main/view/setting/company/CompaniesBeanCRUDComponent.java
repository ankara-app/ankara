package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import io.ankara.ui.vaadin.util.BeanCRUDComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 6:51 PM
 */
@UIScope
@SpringComponent
public class CompaniesBeanCRUDComponent extends BeanCRUDComponent<Company> {

    @Inject
    private CompanyService companyService;

    @Inject
    private CompanyForm companyForm;

    @Inject
    private CompanyDetailsView detailsView;



    @PostConstruct
    protected void build() {
        setSizeFull();

        table.addColumn(Company::getName).setCaption("Name");

        table.addColumn(Company::getEmail).setCaption("Email");

        table.addColumn(Company::getPhone).setCaption("Phone");

        table.addColumn(Company::getFax).setCaption("Fax");
        table.addColumn(Company::getAddress).setCaption("Address");

        super.build();

        setConfirmDelete(true);
        setDeleteConfirmationMessage("Deleting a company will also delete all records of CUSTOMERS , INVOICES and ESTIMATES created for the company");
    }

    @Override
    protected void removeItem(Object itemID) {
        Company company = (Company) itemID;
        companyService.delete(company);
    }

    @Override
    public void reload() {
        getTable().setItems(companyService.getCurrentUserCompanies());
    }

    @Override
    protected Component getCreateComponent() {
        popUpWindow.setCaption("Create new company");
        companyForm.edit(new Company());
        companyForm.setSubWindow(popUpWindow);
        companyForm.setWidth("60%");


        VerticalLayout holder = new VerticalLayout(companyForm);
        holder.setMargin(true);
        holder.setComponentAlignment(companyForm, Alignment.MIDDLE_CENTER);
        return holder;
    }

    @Override
    protected Component getBeanComponent(Object bean) {
        detailsView.show((Company) bean);
        return detailsView;
    }
}
