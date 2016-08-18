package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.User;
import io.ankara.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
        addUser(company, userService.getCurrentUser());
        return true;
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
}
