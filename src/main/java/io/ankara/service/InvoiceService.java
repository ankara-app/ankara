package io.ankara.service;

import io.ankara.domain.Company;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;

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

    Invoice getInvoice(Long id);

    boolean delete(Invoice cost);
}
