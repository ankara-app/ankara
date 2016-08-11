package io.ankara.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Cost implements Serializable{

    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(unique = true,nullable = false)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timeCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastTimeUpdated;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date issueDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User creator;

    @Column(columnDefinition = "longtext not null")
    private String subject;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    @Column(precision = 48, scale = 2)
    private BigDecimal tax;

    @Column(precision = 48, scale = 2)
    private BigDecimal discount;

    @Column(columnDefinition = "longtext not null")
    private String notes;

    @Column(columnDefinition = "longtext not null")
    private String terms;



}
