package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Tax;

import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/31/16 6:53 PM
 */
public interface TaxService {
    boolean save(Tax tax);

    List<Tax> getTaxes(Company company);

    boolean delete(Tax tax);
}
