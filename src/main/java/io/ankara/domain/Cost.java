package io.ankara.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cost implements Serializable{

    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(nullable = false)
    private String currency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timeCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastTimeUpdated;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User creator;

    @Column(columnDefinition = "longtext not null")
    private String subject;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Column(precision = 48, scale = 2)
    private BigDecimal tax;

    @Column(precision = 48, scale = 2)
    private BigDecimal discount;

    @Column(columnDefinition = "longtext not null")
    private String notes;

    @Column(columnDefinition = "longtext not null")
    private String terms;

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
}
