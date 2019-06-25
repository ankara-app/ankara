package io.ankara.domain;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */
@Entity
public class Invoice extends Cost implements Cloneable{

    private String purchaseOrder;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dueDate;

    public Invoice() {
    }

    public Invoice(User creator, Company company, String currency, String code) {
        super(creator, company, currency, code);
        dueDate = new Date();
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public Invoice clone(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseOrder(getPurchaseOrder());
        invoice.setCompany(getCompany());
        invoice.setCurrency(getCurrency());
        invoice.setCustomer(getCustomer());
        invoice.setDiscountPercentage(getDiscountPercentage());
        invoice.setIssueDate(new Date());
        invoice.setSubject(getSubject());
        invoice.setTerms(getTerms());
        invoice.setNotes(getNotes());
        invoice.setTemplate(getTemplate());
        invoice.setItems(getItems().stream().map(item -> item.clone(invoice)).collect(Collectors.toList()));

        return invoice;
    }
}
