package io.ankara.service;

import io.ankara.domain.Company;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:00 AM
 */
@Service
public class CompanyServiceBean implements CompanyService {
    @Override
    public List<Company> getCurrentUserCompanies() {
        return Collections.emptyList();
    }
}
