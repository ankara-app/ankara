package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:10 AM
 */
public interface CustomerService {

    List<Customer> getCurrentUserCustomers();

    Collection<Customer> getCustomers(Company company);

    boolean save(Customer customer);

    boolean delete(Customer customer);

    Customer getCustomer(String name, Company company);

    Customer create(String name, Company company);

    Page<Customer> getCustomers(Company company, Pageable pageable);

    Long countCustomers(Company company);
}
