package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/18/16
 */
public interface InvoiceService {

    String nextInvoiceNumber(Company company);

    boolean save(Invoice invoice);

    List<Invoice> getInvoices(User currentUser);

    List<Invoice> getCompanyInvoices(Collection<Company> companies);

    Collection<Invoice> getCustomerInvoices(Collection<Customer> customers);

    List<Invoice> getCreatedInvoices(User user);

    Invoice getInvoice(Long id);

    boolean delete(Invoice cost);

    File generatePDF(Invoice invoice) throws IOException, InterruptedException;
}
