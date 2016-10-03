package io.ankara.ui.vaadin.main.view.setting.customer;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.service.CustomerService;
import io.ankara.ui.vaadin.util.BeanCRUDComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 9:13 AM
 */
@UIScope
@SpringComponent
public class CustomerBeanCRUDComponent extends BeanCRUDComponent {

    @Inject
    private CustomerService customerService;

    @Inject
    private CustomerForm customerForm;

    private VerticalLayout holder;

    private Company company;

    @PostConstruct
    protected void build() {
        setSizeFull();
        customerForm.setWidth("60%");

        holder = new VerticalLayout(customerForm);
        holder.setSizeFull();
        holder.setMargin(true);
        holder.setComponentAlignment(customerForm, Alignment.MIDDLE_CENTER);

        super.build(Customer.class);

        removeItemButtonGenerator.setConfirmationMessage("Deleting a customer will also delete all records of INVOICES and ESTIMATES created for the customer");
        table.setVisibleColumns("name", "email", "address", "description","Remove");
    }

    @Override
    protected void removeItem(Object itemID) {
        Customer customer = (Customer) itemID;
        customerService.delete(customer);
    }

    @Override
    protected Collection loadBeans() {
        return customerService.getCustomers(company);
    }

    @Override
    protected Component getCreateComponent() {
        popUpWindow.setCaption("Create new customer");
        customerForm.edit(new Customer(company));
        customerForm.setSubWindow(popUpWindow);

        return holder;
    }

    @Override
    protected Component getBeanComponent(Object bean) {
        customerForm.edit((Customer) bean);
        customerForm.setSubWindow(popUpWindow);
        return holder;
    }

    public void setCompany(Company company) {
        this.company = company;
        reload();
    }
}
