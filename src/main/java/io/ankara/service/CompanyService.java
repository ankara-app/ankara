package io.ankara.service;

import io.ankara.domain.Company;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 1:00 AM
 */
public interface CompanyService {
    List<Company> getCurrentUserCompanies();
}
