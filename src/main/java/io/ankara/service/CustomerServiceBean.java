package io.ankara.service;

import io.ankara.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:11 AM
 */
@Service
public class CustomerServiceBean implements CustomerService{
    @Override
    public List<Customer> getCurrentUserCustomers() {
        return Collections.emptyList();
    }
}
