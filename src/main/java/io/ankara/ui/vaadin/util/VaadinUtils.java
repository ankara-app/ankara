package io.ankara.ui.vaadin.util;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import io.ankara.ui.vaadin.AnkaraTheme;
import org.vaadin.viritin.fields.MTextField;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/7/17 10:38 AM
 */
public class VaadinUtils {
    public static void addValidator(AbstractField field, Validator validator) {
        field.addValueChangeListener(event -> {
            ValidationResult result = validator.apply(event.getValue(), new ValueContext(field));

            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                field.setComponentError(error);
            } else {
                field.setComponentError(null);
            }
        });
    }

    public static MTextField addFilteringTextField(HeaderRow headerRow, String propertyId, String placeHolder, HasValue.ValueChangeListener<String> listener) {
        MTextField filterField =  createFilteringTextField(placeHolder, listener);
        headerRow.getCell(propertyId).setComponent(filterField);
        return filterField;
    }

    private static MTextField createFilteringTextField(String placeHolder, HasValue.ValueChangeListener<String> listener) {
        MTextField filterField = new MTextField();
        filterField.setPlaceholder(placeHolder);
        filterField.setWidth("100%");
        filterField.addStyleNames(AnkaraTheme.TEXTFIELD_TINY,AnkaraTheme.TEXT_SMALL);
        filterField.addValueChangeListener(listener);
        return filterField;
    }
}
