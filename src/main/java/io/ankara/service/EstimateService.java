package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Estimate;
import io.ankara.domain.User;
import org.springframework.stereotype.Service;

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

    Estimate getEstimate(Long id);

    boolean delete(Estimate estimate);
}
