package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by bonifacechacha on 8/31/16.
 */
@Entity
public class Tax {
    public static final String VAT = "VAT";
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Company company;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column(precision = 48, scale = 2,nullable = false)
    @NotNull
    private BigDecimal percentage;

    public Tax() {
    }

    public Tax(String name, Company company, BigDecimal percentage, String description) {
        this.name = name;
        this.company = company;
        this.percentage = percentage;
        this.description = description;
    }

    public Tax(Company company) {
        this(null,company,null,null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Tax tax = (Tax) o;

        return new EqualsBuilder()
                .append(id, tax.id)
                .append(name, tax.name)
                .append(company, tax.company)
                .append(percentage, tax.percentage)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(company)
                .append(percentage)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getName()).append(" (").append(percentage.stripTrailingZeros().toPlainString()).append("%)").toString();
    }
}
