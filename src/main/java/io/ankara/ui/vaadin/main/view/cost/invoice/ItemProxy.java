package io.ankara.ui.vaadin.main.view.cost.invoice;

import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/17/16
 */
public class ItemProxy {

    private Item item;

    private Date createdTime = new Date();

    public ItemProxy(Item item) {
        this.item = item;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemType getType() {
        return item.getType();
    }

    public void setType(ItemType type) {
        this.item.setType(type);
    }

    public String getDescription() {
        return item.getDescription();
    }

    public void setDescription(String description) {
        this.item.setDescription(description);
    }

    public Integer getQuantity() {
        return item.getQuantity();
    }

    public void setQuantity(Integer quantity) {
        this.item.setQuantity(quantity);
    }

    public BigDecimal getPrice() {
        return item.getPrice();
    }

    public void setPrice(BigDecimal price) {
        this.item.setPrice(price);
    }
    public BigDecimal getAmount(){
        return item.getAmount();
    }

    public void setTaxable(boolean taxable) {
        this.item.setTaxable(taxable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemProxy itemProxy = (ItemProxy) o;

        return new EqualsBuilder()
                .append(item, itemProxy.item)
                .append(createdTime, itemProxy.createdTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(item)
                .append(createdTime)
                .toHashCode();
    }

}
