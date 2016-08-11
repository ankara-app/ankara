package io.ankara.domain;

import javax.persistence.*;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 5:39 PM
 */
@Entity
public class ItemType {
    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @Column(columnDefinition = "longtext not null")
    private String description;
}
