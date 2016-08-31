package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.ItemType;
import io.ankara.domain.Tax;
import io.ankara.domain.User;
import io.ankara.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:00 AM
 */
@Service
public class CompanyServiceBean implements CompanyService {

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private UserService userService;

    @Inject
    private ItemTypeService itemTypeService;

    @Inject
    private TaxService taxService;

    @Override
    public List<Company> getCurrentUserCompanies() {
        return getCompanies(userService.getCurrentUser());
    }

    @Override
    public List<Company> getCompanies(User user) {
        return companyRepository.findByUsers(user);
    }

    @Override
    @Transactional
    public boolean create(Company company) {
        save(company);

        addDefaultItemTypes(company);
        addDefaultTaxes(company);

        addUser(company, userService.getCurrentUser());
        return true;
    }

    @Transactional
    private void addDefaultTaxes(Company company) {
        taxService.save(new Tax("VAT", company, new BigDecimal("18"), "Value added tax"));
    }

    @Transactional
    private void addDefaultItemTypes(Company company) {
        itemTypeService.save(new ItemType("Goods", company, null));
        itemTypeService.save(new ItemType("Service", company, null));
    }

    @Override
    @Transactional
    public boolean addUser(Company company, User user) {
        company.addUser(user);
        return save(company);
    }

    @Override
    @Transactional
    public boolean save(Company company) {
        companyRepository.save(company);
        return true;
    }

    @Override
    public boolean delete(Company company) {
        companyRepository.delete(company);
        return true;
    }
}
