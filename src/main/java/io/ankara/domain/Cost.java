package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String code;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String currency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date timeCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date lastTimeUpdated = new Date();

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    private Date issueDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Company company;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User creator;

    @Column(columnDefinition = "longtext not null")
    @NotBlank
    @NotNull
    private String subject;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Customer customer;

    @Min(value = 0)
    @Column(precision = 48, scale = 2, nullable = false)
    @NotNull
    private BigDecimal discountPercentage;

    @Column(columnDefinition = "longtext")
    private String notes;

    @Column(columnDefinition = "longtext")
    private String terms;

    private String template;

    /**
     * Is the cost still a draft or already submitted
     */
    private boolean submitted;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "cost")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    private List<Item> items;

    public Cost() {
        this.discountPercentage = new BigDecimal("0.0");
        issueDate = new Date();
        this.items = new LinkedList<>();
    }

    public Cost(User creator, Company company, String currency, String code) {
        this();
        this.creator = creator;
        this.company = company;
        this.currency = currency;
        this.code = code;

        if (company != null) {
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
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

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
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

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
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
                .append(discountPercentage, cost.discountPercentage)
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
                .append(discountPercentage)
                .toHashCode();
    }

    @Override
    public String toString() {
        return getCompany() + " : " + getSubject();
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal("0.0");

        for (Item item : getItems()) {
            subtotal = subtotal.add(item.getAmount());
        }

        return subtotal;
    }

    public BigDecimal getDiscount() {
        return getSubtotal().multiply(discountPercentage).divide(new BigDecimal("100"));
    }

    public BigDecimal calculateTax(Tax tax) {
        BigDecimal total = new BigDecimal("0.0");

        for (Item item : getItems()) {
            total = total.add(item.calculateTax(tax));
        }
        return total;
    }

    public BigDecimal getAmountDue() {
        return getTaxedTotal().subtract(getDiscount());
    }

    public BigDecimal getTotalTax() {
        BigDecimal total = new BigDecimal("0.0");
        for (Tax tax : getTaxes()) {
            total = total.add(calculateTax(tax));
        }
        return total;
    }

    public Set<Tax> getTaxes() {
        HashSet<Tax> taxes = new HashSet<>();

        for (Item item : items) {
            taxes.addAll(item.getTaxes().stream().map(appliedTax -> appliedTax.getTax()).collect(Collectors.toSet()));
        }

        return taxes;
    }

    public AppliedTax getAppliedTax(Tax tax) {
        for (Item item : items) {
            Optional<AppliedTax> appliedTax = item.getAppliedTax(tax);
            if (appliedTax.isPresent())
                return appliedTax.get();
        }
        return null;
    }

    public BigDecimal getTaxedTotal() {
        return getSubtotal().add(getTotalTax());
    }

    public boolean hasTax(Tax tax) {
        return getTaxes().contains(tax);
    }
}
