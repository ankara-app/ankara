package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Tax;
import io.ankara.repository.TaxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/31/16 7:06 PM
 */
@Service
public class TaxServiceBean implements TaxService {

    @Inject
    private TaxRepository taxRepository;

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
        taxRepository.delete(tax);
        return true;
    }
}
