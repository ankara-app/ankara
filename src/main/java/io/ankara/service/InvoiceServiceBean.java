package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/18/16
 */
@Service
public class InvoiceServiceBean implements InvoiceService{

    @Inject
    private UserService userService;

    @Inject
    private CompanyService companyService;


    @Override
    public Invoice newInvoice() {
        User creator = userService.getCurrentUser();
        Collection<Company> companies = companyService.getCompanies(creator);
        Company defaultCompany = companies.isEmpty()?null:companies.iterator().next();

        //TODO CURRENCIES SHOULD BE IN SETTING AND CONFIGURABLE
        String currency = "TZS";
        return new Invoice(creator,defaultCompany,currency,nextInvoiceNumber());
    }

    private String nextInvoiceNumber() {
        //TODO THE NUMBER SHOULD BE A SEQUENCY CONFIGURABLE
        return "";
    }
}
