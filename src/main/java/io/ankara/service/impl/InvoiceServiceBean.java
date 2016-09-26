package io.ankara.service.impl;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.repository.InvoiceRepository;
import io.ankara.service.CompanyService;
import io.ankara.service.InvoiceService;
import io.ankara.service.PDFService;
import io.ankara.utils.FormattedID;
import io.ankara.utils.GeneralUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
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

    @Inject
    private PDFService pdfService;


    public String nextInvoiceNumber(Company company) {
        String prevCode;
        Invoice recentInvoice = invoiceRepository.findFirstByCompanyOrderByIdDesc(company);

        if (recentInvoice == null)
            prevCode = "0000";
        else prevCode = recentInvoice.getCode();

        return FormattedID.generate(prevCode);
    }

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

    @Transactional
    public boolean delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
        return true;
    }

    @Override
    public File generatePDF(Invoice invoice) throws IOException, InterruptedException {
        String genPDFFilePath = System.getProperty("java.io.tmpdir") + File.separator + invoice.getClass().getCanonicalName() + invoice.getId() + ".pdf";
        String invoiceURL = GeneralUtils.getApplicationAddress()+"/invoice/"+invoice.getId();

        return pdfService.generatePDF(invoiceURL,genPDFFilePath);
    }
}
