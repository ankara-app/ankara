package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by bonifacechacha on 8/31/16.
 */
@Entity
public class AppliedTax implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Tax tax;

    @Column(precision = 48, scale = 2,nullable = false)
    @NotNull
    private BigDecimal percentage;

    public AppliedTax() {
    }

    public AppliedTax(Tax tax, BigDecimal percentage) {
        this.tax = tax;
        this.percentage = percentage;
    }

    public AppliedTax(Tax tax) {
        this(tax,tax.getPercentage());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
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

        AppliedTax that = (AppliedTax) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(tax, that.tax)
                .append(percentage, that.percentage)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tax)
                .append(percentage)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append(tax.getName()).append(" (").append(percentage.stripTrailingZeros().toPlainString()).append("%)").toString();
    }

    @Override
    public AppliedTax clone(){
        return new AppliedTax(tax,new BigDecimal(percentage.toString()));
    }
}
