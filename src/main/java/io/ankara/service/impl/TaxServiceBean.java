package io.ankara.service.impl;

import io.ankara.domain.*;
import io.ankara.repository.AppliedTaxRepository;
import io.ankara.repository.TaxRepository;
import io.ankara.service.EstimateService;
import io.ankara.service.InvoiceService;
import io.ankara.service.TaxService;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/31/16 7:06 PM
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaxServiceBean implements TaxService {

    @Inject
    private TaxRepository taxRepository;

    @Inject
    private AppliedTaxRepository appliedTaxRepository;

    @Transactional
    public boolean save(Tax tax) {
        taxRepository.save(tax);
        return true;
    }

    @Override
    public List<Tax> getTaxes(Company company) {
        return taxRepository.findAllByCompany(company);
    }

    @Override
    @Transactional
    public boolean delete(Tax tax) {
        Collection<AppliedTax> appliedTaxes = appliedTaxRepository.findAllByTax(tax);
        if(!appliedTaxes.isEmpty())
            throw new IllegalStateException("There are invoices/estimates applied with this tax : "+tax);

        taxRepository.delete(tax);

        return true;
    }
}
