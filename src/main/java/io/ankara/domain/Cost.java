package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */

//TODO CUSTOMER AND COMPANY DETAILS AT THE TIME OF CREATION SHOULD BE SAVE ON THE COST
//TODO SO THAT IF THE CUSTOMER DETAILS OR COMPANY DETAILS CHANGES THE INVOICE REMAINS AS IT WAS CREATED
//TODO DURING CREATION THE FORM SHOULD ALLOW USER TO SELECT THE CUSTOMER AND COMPANY WHICH WILL INTURN FILL THEIR INFORMATION ON
//TODO THE COST. BUT AFTER A CERTAIN STATE SAY SUBMITTED/SENT TO CUSTOMER STATE OF COST/INVOICE THE FORM SHOULD NO LONGER ALLOW EDITING BY SELCTING CUSTOMER OR COMPANY
//TODO INSTEAD IT SHOULD ONLY ALLOW EDITING THE ACTUAL DETAILS OF THE COST AS THEY WERE OBTAINED FROM THE CUSTOMER AND COMPANY

@Entity
public class Cost implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String code;

    @Column(nullable = false)
    @NotBlank
    private String currency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date timeCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date lastTimeUpdated = new Date();

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Company company;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User creator;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String subject;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Customer customer;

    @Column(precision = 48, scale = 2)
    private BigDecimal tax;

    @Column(precision = 48, scale = 2)
    private BigDecimal discount;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String notes;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String terms;

    private String template;

    @ManyToOne
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    public Cost() {
    }

    public Cost(User creator, Company company, String currency, String code) {
        this.creator = creator;
        this.company = company;
        this.currency = currency;
        this.code = code;

        this.tax = new BigDecimal("0.0");
        this.discount = new BigDecimal("0.0");

        if(company!=null){
            terms = company.getTerms();
            notes = company.getNotes();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(Date lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Cost cost = (Cost) o;

        return new EqualsBuilder()
                .append(id, cost.id)
                .append(code, cost.code)
                .append(currency, cost.currency)
                .append(company, cost.company)
                .append(creator, cost.creator)
                .append(subject, cost.subject)
                .append(customer, cost.customer)
                .append(tax, cost.tax)
                .append(discount, cost.discount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(code)
                .append(currency)
                .append(company)
                .append(creator)
                .append(subject)
                .append(customer)
                .append(tax)
                .append(discount)
                .toHashCode();
    }

    @Override
    public String toString() {
        return getCompany() + " : " + getSubject();
    }
}
