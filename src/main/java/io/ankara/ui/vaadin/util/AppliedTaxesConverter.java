package io.ankara.ui.vaadin.util;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import io.ankara.domain.AppliedTax;
import io.ankara.domain.Tax;

import java.util.*;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/1/16 4:05 PM
 */
public class AppliedTaxesConverter implements Converter<Set<Tax>,List<AppliedTax>> {

    @Override
    public Result<List<AppliedTax>> convertToModel(Set<Tax> value, ValueContext context) {
        Collection<Tax> taxes =  value;
        List<AppliedTax> appliedTaxes = new LinkedList<>();

        for(Tax tax:taxes){
            appliedTaxes.add(new AppliedTax(tax));
        }

        return Result.ok(appliedTaxes);
    }

    @Override
    public Set<Tax> convertToPresentation(List<AppliedTax> value, ValueContext context) {
        Collection<AppliedTax> appliedTaxes = value;
        Set<Tax> taxes = new HashSet<>();

        for(AppliedTax appliedTax:appliedTaxes){
            taxes.add(appliedTax.getTax());
        }

        return taxes;
    }
}
