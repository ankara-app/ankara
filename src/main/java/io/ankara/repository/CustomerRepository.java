package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 9:45 AM
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByCompany(Company company);
}
