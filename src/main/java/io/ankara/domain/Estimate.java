package io.ankara.domain;

import javax.persistence.Entity;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */
@Entity
public class Estimate extends Cost implements Cloneable{

    public Estimate() {
        super();
    }

    public Estimate(User creator, Company company, String currency, String code) {
        super(creator, company, currency, code);
    }

    @Override
    public Estimate clone() {
        Estimate estimate = new Estimate();
        estimate.setCompany(getCompany());
        estimate.setCurrency(getCurrency());
        estimate.setCustomer(getCustomer());
        estimate.setDiscountPercentage(getDiscountPercentage());
        estimate.setIssueDate(new Date());
        estimate.setSubject(getSubject());
        estimate.setTerms(getTerms());
        estimate.setNotes(getNotes());
        estimate.setTemplate(getTemplate());
        estimate.setItems(getItems().stream().map(item -> item.clone(estimate)).collect(Collectors.toList()));

        return estimate;
    }
}