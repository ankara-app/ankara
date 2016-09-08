package io.ankara.domain;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Column(precision = 48, scale = 2,nullable = false)
    @NotNull
    private BigDecimal quantity;

    @Column(precision = 48, scale = 2,nullable = false)
    @NotNull
    private BigDecimal price;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<AppliedTax> taxes;

    public Item() {
        taxes = new LinkedList<>();
    }

    public Item(Cost cost, ItemType type) {
        this();
        this.cost = cost;
        this.type = type;

        quantity = BigDecimal.ONE;
        price = BigDecimal.ZERO;
    }

    public Item(Cost cost) {
       this(cost,null);
    }

    public BigDecimal getAmount(){
        return price.multiply(quantity);
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void loadTaxes(Set<Tax> taxes){
        if(cost == null)
            throw new IllegalStateException("Associated cost for the item is not specified");

        LinkedList appliedTaxes = new LinkedList();
        for(Tax tax:taxes){
            appliedTaxes.add(new AppliedTax(tax));
        }
        setTaxes(appliedTaxes);
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


    public List<AppliedTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<AppliedTax> taxes) {
        this.taxes = taxes;
    }

    public BigDecimal calculateTax() {
        BigDecimal taxAmount = new BigDecimal("0.0");

        for(AppliedTax appliedTax: taxes){
            taxAmount = taxAmount.add(getAmount().multiply(appliedTax.getPercentage()).divide(new BigDecimal("100")));
        }

        return taxAmount;
    }

    public BigDecimal calculateTax(Tax tax) {
        Optional<AppliedTax> appliedTax = getAppliedTax(tax);
        if(!appliedTax.isPresent()) return BigDecimal.ZERO;

        return getAmount().multiply(appliedTax.get().getPercentage()).divide(new BigDecimal("100"));
    }

    public Optional<AppliedTax> getAppliedTax(Tax tax) {
        return getTaxes().stream().filter(appliedTax -> appliedTax.getTax().equals(tax)).findFirst();
    }

    public void addTax(Tax tax) {
        if(taxes == null)
            taxes = new LinkedList<>();

        taxes.add(new AppliedTax(tax));
    }
}
