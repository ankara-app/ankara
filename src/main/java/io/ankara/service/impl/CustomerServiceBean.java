package io.ankara.service.impl;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.repository.CustomerRepository;
import io.ankara.service.CompanyService;
import io.ankara.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:11 AM
 */
@Service
public class CustomerServiceBean implements CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CompanyService companyService;

    @Override
    public List<Customer> getCurrentUserCustomers() {
        LinkedList<Customer> customers = new LinkedList<>();
        Collection<Company> userCompanies = companyService.getCurrentUserCompanies();

        for (Company company : userCompanies) {
            customers.addAll(getCustomers(company));
        }

        return customers;
    }

    @Override
    public Collection<Customer> getCustomers(Company company) {
        return customerRepository.findByCompany(company);
    }

    @Override
    public boolean save(Customer customer) {
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean delete(Customer customer) {
        customerRepository.delete(customer);
        return true;
    }

    @Override
    public Customer getCustomer(String name, Company company) {
        return customerRepository.findFirstByNameAndCompany(name,company);
    }

    @Override
    public Customer create(String name, Company company) {
        Customer customer = new Customer(name, company);
        save(customer);
        return customer;
    }
}
