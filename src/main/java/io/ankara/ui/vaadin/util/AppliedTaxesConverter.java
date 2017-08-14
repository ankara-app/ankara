package io.ankara.ui.vaadin.util;

import com.vaadin.data.util.converter.Converter;
import io.ankara.domain.AppliedTax;
import io.ankara.domain.Tax;

import java.util.*;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/1/16 4:05 PM
 */
public class AppliedTaxesConverter implements Converter {

    @Override
    public Object convertToModel(Object value, Class targetType, Locale locale) throws ConversionException {
        Collection<Tax> taxes = (Collection<Tax>) value;
        Collection<AppliedTax> appliedTaxes = new LinkedList<>();

        for(Tax tax:taxes){
            appliedTaxes.add(new AppliedTax(tax));
        }

        return appliedTaxes;
    }

    @Override
    public Object convertToPresentation(Object value, Class targetType, Locale locale) throws ConversionException {
        Collection<AppliedTax> appliedTaxes = (Collection<AppliedTax>) value;
        Collection<Tax> taxes = new HashSet<>();

        for(AppliedTax appliedTax:appliedTaxes){
            taxes.add(appliedTax.getTax());
        }

        return taxes;
    }

    @Override
    public Class getModelType() {
        return List.class;
    }

    @Override
    public Class getPresentationType() {
        return Set.class;
    }
}
