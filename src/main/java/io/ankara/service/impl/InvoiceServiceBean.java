package io.ankara.service.impl;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.repository.InvoiceRepository;
import io.ankara.service.CompanyService;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.util.FormattedID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CompanyService companyService;

    @Inject
    private InvoiceRepository invoiceRepository;

    public String nextInvoiceNumber(Company company) {
        String prevCode;
        Invoice recentInvoice = invoiceRepository.findFirstByCompanyOrderByIdDesc(company);

        if (recentInvoice == null)
            prevCode = "0000";
        else prevCode = recentInvoice.getCode();

        return FormattedID.generate(prevCode);
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
        return true;
    }
}
