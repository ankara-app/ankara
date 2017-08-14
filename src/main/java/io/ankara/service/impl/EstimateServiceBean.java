package io.ankara.service.impl;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.domain.Estimate;
import io.ankara.domain.User;
import io.ankara.repository.EstimateRepository;
import io.ankara.service.CompanyService;
import io.ankara.service.EstimateService;
import io.ankara.service.PDFService;
import io.ankara.utils.FormattedID;
import io.ankara.utils.GeneralUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
public class EstimateServiceBean implements EstimateService {

    @Inject
    private CompanyService companyService;

    @Inject
    private EstimateRepository estimateRepository;

    @Inject
    private PDFService pdfService;

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
    @Transactional
    public boolean save(Estimate Estimate) {
        estimateRepository.save(Estimate);
        return true;
    }

    @Override
    public List<Estimate> getEstimates(User user) {
        List<Company> companies = companyService.getCompanies(user);
        return getCompanyEstimates(companies);
    }

    @Override
    public List<Estimate> getCompanyEstimates(Collection<Company> companies) {
        return estimateRepository.findAllByCompanyInOrderByTimeCreatedDesc(companies);
    }

    @Override
    public Collection<Estimate> getCustomerEstimates(Collection<Customer> customers) {
        return estimateRepository.findAllByCustomerInOrderByTimeCreatedDesc(customers);
    }

    @Override
    public Estimate getEstimate(Long id) {
        return estimateRepository.findOne(id);
    }

    @Override
    @Transactional
    public boolean delete(Estimate estimate) {
        estimateRepository.delete(estimate);
        return true;
    }

    @Override
    public File generatePDF(Estimate estimate) throws IOException, InterruptedException {
        String genPDFFilePath = System.getProperty("java.io.tmpdir") + File.separator + estimate.getClass().getCanonicalName() + estimate.getId() + ".pdf";
        String estimateURL = GeneralUtils.getApplicationAddress()+"/estimate/"+estimate.getId();

        return pdfService.generatePDF(estimateURL,genPDFFilePath);
    }

}
