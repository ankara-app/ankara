package io.ankara.repository;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.domain.Estimate;
import io.ankara.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@NoRepositoryBean
public interface CostRepository<T> extends JpaRepository<T, Long> {

    List<T> findAllByCompanyInOrderByTimeCreatedDesc(Collection<Company> companies);

    List<T> findAllByCustomerInOrderByTimeCreatedDesc(Collection<Customer> customers);

    T findFirstByCompanyOrderByIdDesc(Company company);

    List<T> findAllByCreatorOrderByTimeCreatedDesc(User user);

    Long countByCompanyInAndCodeContainingIgnoreCaseAndCustomerNameContainingIgnoreCaseAndSubjectContainingIgnoreCase(Collection<Company> companies, String codeFilter, String customerNameFilter, String subjectFilter);

    Page<T> findAllByCompanyInAndCodeContainingIgnoreCaseAndCustomerNameContainingIgnoreCaseAndSubjectContainingIgnoreCase(Collection<Company> currentUserCompanies, String codeFilter, String customerNameFilter, String subjectFilter,Pageable pageable);
}
