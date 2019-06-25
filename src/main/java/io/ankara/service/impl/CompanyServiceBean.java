package io.ankara.service.impl;

import io.ankara.domain.*;
import io.ankara.repository.CompanyRepository;
import io.ankara.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Collection;
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

    @Inject
    private CustomerService customerService;


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
        taxService.save(new Tax(Tax.VAT, company, new BigDecimal("18"), "Value added tax"));
    }

    @Transactional
    private void addDefaultItemTypes(Company company) {
        itemTypeService.save(new ItemType(ItemType.GOODS, company, null));
        itemTypeService.save(new ItemType(ItemType.SERVICE, company, null));
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
    @Transactional
    public boolean delete(Company company) {
        Collection<Customer> customers = customerService.getCustomers(company);

        for(Customer customer:customers)
            customerService.delete(customer);

        companyRepository.delete(company);

        return true;
    }

}
