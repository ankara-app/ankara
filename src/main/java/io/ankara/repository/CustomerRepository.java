package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 9:45 AM
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByCompany(Company company);

    Customer findFirstByNameAndCompany(String name, Company company);

    Page<Customer> findAllByCompany(Company company, Pageable pageable);

    Long countByCompany(Company company);
}
