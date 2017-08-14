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
public class CustomerBeanCRUDComponent extends BeanCRUDComponent<Customer> {

    private CustomerService customerService;
    private CustomerForm customerForm;
    private CustomersProvider customersProvider;

    private VerticalLayout holder;

    private Company company;

    public CustomerBeanCRUDComponent(CustomerService customerService,  CustomersProvider customersProvider,CustomerForm customerForm) {
        this.customerService = customerService;
        this.customerForm = customerForm;
        this.customersProvider = customersProvider;
    }

    @PostConstruct
    protected void build() {
        setSizeFull();
        customerForm.setWidth("60%");

        holder = new VerticalLayout(customerForm);
        holder.setSizeFull();
        holder.setMargin(true);
        holder.setComponentAlignment(customerForm, Alignment.MIDDLE_CENTER);


        table.addColumn(Customer::getName).setCaption("Name");
        table.addColumn(Customer::getEmail).setCaption("Email");
        table.addColumn(Customer::getAddress).setCaption("Address");
        table.addColumn(Customer::getDescription).setCaption("Description");
        //TODO NOTIFY ABOUT THE EFFECT OF DELETING CUSTOMER
//        removeItemButtonGenerator.setConfirmationMessage("Deleting a customer will also delete all records of INVOICES and ESTIMATES created for the customer");

        table.setDataProvider(customersProvider);
        super.build();
    }

    @Override
    protected void removeItem(Object itemID) {
        Customer customer = (Customer) itemID;
        customerService.delete(customer);
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
        customersProvider.setCompany(company);
        reload();
    }
}
