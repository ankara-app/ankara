package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.domain.Estimate;
import io.ankara.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@Service
public interface EstimateService {
    String nextEstimateNumber(Company company);

    boolean save(Estimate Estimate);

    List<Estimate> getEstimates(User user);

    Collection<Estimate> getCompanyEstimates(Collection<Company> companies);

    Estimate getEstimate(Long id);

    boolean delete(Estimate estimate);

    File generatePDF(Estimate estimate) throws IOException, InterruptedException;

    Collection<Estimate> getCustomerEstimates(Collection<Customer> customers);

    Long countEstimates(User user, String codeFilter, String customerNameFilter, String subjectFilter);

    Long countEstimates(Collection<Company> companies, String codeFilter, String customerNameFilter, String subjectFilter);

    Page<Estimate> getEstimates(User currentUser, String codeFilter, String customerNameFilter, String subjectFilter, Pageable pageable);

    Page<Estimate> getEstimates(Collection<Company> companies, String codeFilter, String customerNameFilter, String subjectFilter, Pageable pageable);

}
