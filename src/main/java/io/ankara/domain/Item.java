package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 5:39 PM
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Cost cost;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private ItemType type;

    @Column(columnDefinition = "longtext not null")
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotNull
    private Integer quantity;

    @Column(precision = 48, scale = 2,nullable = false)
    @NotNull
    private BigDecimal price;

    private boolean taxable;

    public Item() {
    }

    public Item(Cost cost, ItemType type) {
        this.cost = cost;
        this.type = type;
        quantity = 1;
        price = BigDecimal.ZERO;
        taxable = true;
    }

    public BigDecimal getAmount(){
        return price.multiply(new BigDecimal(quantity));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new EqualsBuilder()
                .append(id, item.id)
                .append(cost, item.cost)
                .append(type, item.type)
                .append(description, item.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(cost)
                .append(type)
                .append(description)
                .toHashCode();
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
}
