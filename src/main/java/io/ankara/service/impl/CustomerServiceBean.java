package io.ankara.service.impl;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.repository.CustomerRepository;
import io.ankara.service.CompanyService;
import io.ankara.service.CustomerService;
import io.ankara.service.EstimateService;
import io.ankara.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
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

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private EstimateService estimateService;

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
    @Transactional
    public boolean save(Customer customer) {
        customerRepository.save(customer);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Customer customer) {

        for (Invoice invoice : invoiceService.getCustomerInvoices(Arrays.asList(customer))) {
            invoiceService.delete(invoice);
        }

        for (Estimate estimate : estimateService.getCustomerEstimates(Arrays.asList(customer))) {
            estimateService.delete(estimate);
        }

        customerRepository.delete(customer);

        return true;
    }

    @Override
    public Customer getCustomer(String name, Company company) {
        return customerRepository.findFirstByNameAndCompany(name,company);
    }

    @Override
    @Transactional
    public Customer create(String name, Company company) {
        Customer customer = new Customer(name, company);
        save(customer);
        return customer;
    }
}
