package io.ankara.ui.vaadin.main.view.setting.customer;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/12/17 3:47 PM
 */
@SpringComponent
@PrototypeScope
public class CustomersProvider extends PageableDataProvider<Customer,Void>{

    private CustomerService customerService;
    private Company company;

    public CustomersProvider(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    protected Page<Customer> fetchFromBackEnd(Query<Customer, Void> query, Pageable pageable) {
        return customerService.getCustomers(company,pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        List<QuerySortOrder> sortOrders = new ArrayList<>();
        sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
//        sortOrders.add(new QuerySortOrder("id", SortDirection.DESCENDING));
        return sortOrders;
    }

    @Override
    protected int sizeInBackEnd(Query<Customer, Void> query) {
        Long count = customerService.countCustomers(company);
        return count.intValue();
    }
}
