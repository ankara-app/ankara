package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/20/16
 */
public interface InvoiceRepository extends CostRepository<Invoice> {

}
