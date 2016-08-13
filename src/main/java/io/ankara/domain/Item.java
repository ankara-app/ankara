package io.ankara.domain;

import javax.persistence.*;
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
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cost cost;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ItemType type;

    @Column(columnDefinition = "longtext not null")
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 48, scale = 2,nullable = false)
    private BigDecimal price;

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
}
