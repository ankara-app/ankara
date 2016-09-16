package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@NoRepositoryBean
public interface CostRepository<T> extends JpaRepository<T, Long> {
    List<T> findAllByCompanyInOrderByTimeCreatedDesc(List<Company> companies);

    T findFirstByCompanyOrderByIdDesc(Company company);
}
