package io.ankara.domain;

import javax.persistence.Entity;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */
@Entity
public class Estimate extends Cost {

    public Estimate() {
    }

    public Estimate(User creator, Company company, String currency, String code) {
        super(creator, company, currency, code);
    }
}