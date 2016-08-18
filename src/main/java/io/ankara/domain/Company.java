package io.ankara.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:51 AM
 */
@Entity
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false,unique = true)
    @NotBlank
    private String name;

    private String registration;

    private String VAT;

    @Lob
    @Column(nullable = false)
    private String paymentInformation;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String address;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String description;

    @Lob
    private byte[] picture;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String notes;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String terms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getVAT() {
        return VAT;
    }

    public void setVAT(String VAT) {
        this.VAT = VAT;
    }

    public String getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(String paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return new EqualsBuilder()
                .append(id, company.id)
                .append(name, company.name)
                .append(registration, company.registration)
                .append(VAT, company.VAT)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(registration)
                .append(VAT)
                .toHashCode();
    }

    public void addUser(User user) {
        if (users == null) users = new HashSet<>();
        users.add(user);
    }

    @Override
    public String toString() {
        return getName();
    }
}
