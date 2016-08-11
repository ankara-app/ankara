package io.ankara.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import java.util.Set;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 5:54 PM
 */
@Entity
public class Profile {
    @Id
    private Long id;

    @Version
    private Integer version;

    @ManyToMany
    private Set<Company> company;
}
