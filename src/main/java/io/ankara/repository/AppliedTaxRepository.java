package io.ankara.repository;

import io.ankara.domain.AppliedTax;
import io.ankara.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 10/3/16.
 */
public interface AppliedTaxRepository extends JpaRepository<AppliedTax,Long>{
    List<AppliedTax> findAllByTax(Tax tax);
}
