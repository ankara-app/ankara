package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.repository.EstimateRepository;
import io.ankara.ui.vaadin.util.FormattedID;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@Service
public class EstimateServiceBean implements EstimateService{

    @Inject
    private CompanyService companyService;

    @Inject
    private EstimateRepository estimateRepository;

    @Override
    public String nextEstimateNumber(Company company) {
        String prevCode;
        Estimate recentEstimate = estimateRepository.findFirstByCompanyOrderByIdDesc(company);

        if (recentEstimate == null)
            prevCode = "0000";
        else prevCode = recentEstimate.getCode();

        return FormattedID.generate(prevCode);
    }

    @Override
    public boolean save(Estimate Estimate) {
        estimateRepository.save(Estimate);
        return true;
    }

    @Override
    public List<Estimate> getEstimates(User user) {
        List<Company> companies = companyService.getCompanies(user);

        return estimateRepository.findAllByCompanyInOrderByTimeCreatedDesc(companies);
    }

    @Override
    public Estimate getEstimate(Long id) {
        return estimateRepository.findOne(id);
    }

    @Override
    public boolean delete(Estimate estimate) {
        estimateRepository.delete(estimate);
        return true;
    }
}
