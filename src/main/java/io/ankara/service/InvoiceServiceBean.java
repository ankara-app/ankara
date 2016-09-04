package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.repository.InvoiceRepository;
import io.ankara.ui.vaadin.util.FormattedID;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/18/16
 */
@Service
public class InvoiceServiceBean implements InvoiceService {

    @Inject
    private UserService userService;

    @Inject
    private CompanyService companyService;

    @Inject
    private InvoiceRepository invoiceRepository;


    @Override
    public Invoice newInvoice() {
        User creator = userService.getCurrentUser();
        Collection<Company> companies = companyService.getCompanies(creator);
        Company defaultCompany = companies.isEmpty() ? null : companies.iterator().next();

        //TODO CURRENCIES SHOULD BE IN SETTING AND CONFIGURABLE
        String currency = "TZS";
        String code = defaultCompany == null ? null : nextInvoiceNumber(defaultCompany);
        Invoice invoice =  new Invoice(creator, defaultCompany, currency, code);

        return invoice;
    }

    public String nextInvoiceNumber(Company company) {
        String prevCode;
        Invoice recentInvoice = invoiceRepository.findOneByCompanyOrderByIdDesc(company);

        if (recentInvoice == null)
            prevCode = "0000";
        else prevCode = recentInvoice.getCode();

        return FormattedID.generate(prevCode);
    }

    @Override
    public boolean save(Invoice invoice) {
        invoiceRepository.save(invoice);
        return true;
    }

    @Override
    public List<Invoice> getInvoices(User user) {
        List<Company> companies = companyService.getCompanies(user);

        return invoiceRepository.findAllByCompanyInOrderByTimeCreatedDesc(companies);
    }

    @Override
    public Invoice getInvoice(Long id) {
        return invoiceRepository.findOne(id);
    }

    @Override
    public boolean delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
        return true;
    }
}
