package io.ankara.controller;

import io.ankara.utils.GeneralUtils;
import io.ankara.domain.Invoice;
import io.ankara.service.InvoiceService;
import io.ankara.service.PDFService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/25/16.
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Inject
    private InvoiceService invoiceService;

    @RequestMapping(value = "/{invoiceID}", method = RequestMethod.GET)
    public String show(@PathVariable Long invoiceID, Model model) {
        model.addAttribute("cost", invoiceService.getInvoice(invoiceID));
        return "invoice";
    }

    @RequestMapping(value = "print/{invoiceID}", method = RequestMethod.GET)
    public String print(@PathVariable Long invoiceID, Model model) {
        model.addAttribute("print", true);
        model.addAttribute("cost", invoiceService.getInvoice(invoiceID));
        return "invoicePrint";
    }

    @RequestMapping(value = "pdf/{invoiceID}", method = RequestMethod.GET)
    public void pdf(@PathVariable Long invoiceID, HttpServletResponse response) throws IOException, InterruptedException {
        Invoice invoice = invoiceService.getInvoice(invoiceID);
        if (invoice == null) return;

        response.setContentType("application/pdf");
        File file = invoiceService.generatePDF(invoice);
        OutputStream os = response.getOutputStream();
        Files.copy(file.toPath(), os);
        os.flush();
    }


}
