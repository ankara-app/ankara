package io.ankara.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */
@Entity
public class Invoice extends Cost {

    private String purchaseOrder;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dueDate;

    public Invoice() {
    }

    public Invoice(User creator, Company company, String currency, String code) {
        super(creator, company, currency, code);
        issueDate = new Date();
        dueDate = new Date();
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
