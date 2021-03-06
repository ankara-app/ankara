package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 5:39 PM
 */
@Entity
public class ItemType {
    public static final String GOODS = "Goods";
    public static final String SERVICE = "Service";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public ItemType() {
    }

    public ItemType(Company company) {
        this(null,company,null);
    }

    public ItemType(String name, Company company, String description) {
        this();
        this.name = name;
        this.company = company;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemType itemType = (ItemType) o;

        return new EqualsBuilder()
                .append(id, itemType.id)
                .append(name, itemType.name)
                .append(company, itemType.company)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(company)
                .toHashCode();
    }

    @Override
    public String toString() {
        return getName();
    }
}
