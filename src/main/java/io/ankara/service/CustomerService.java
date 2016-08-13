package io.ankara.service;

import io.ankara.domain.Customer;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:10 AM
 */
public interface CustomerService {
    List<Customer> getCurrentUserCustomers();
}
