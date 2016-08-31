package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/31/16 7:07 PM
 */
public interface TaxRepository extends JpaRepository<Tax,Long> {
    List<Tax> findAllByCompany(Company company);
}
