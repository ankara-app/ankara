package io.ankara.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:51 AM
 */
@Entity
public class Company {

    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    private String name;

    private String registration;

    private String VAT;

    @Column(columnDefinition = "longtext")
    private String paymentInformation;

    @Column(columnDefinition = "longtext not null")
    private String address;

    @Column(columnDefinition = "longtext not null")
    private String description;

}
