package io.ankara.ui.vaadin.util;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import io.ankara.ui.vaadin.AnkaraTheme;
import org.vaadin.addons.searchbox.SearchBox;
import org.vaadin.viritin.fields.MTextField;

import java.util.function.Consumer;

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

    public static SearchBox addFilteringTextField(HeaderRow headerRow, String propertyId, String placeHolder, Consumer<String> listener) {
        SearchBox filterField =  createFilteringTextField(placeHolder, listener);
        filterField.setWidth("100%");
        headerRow.getCell(propertyId).setComponent(filterField);
        return filterField;
    }

    public static SearchBox createFilteringTextField(String placeHolder, Consumer<String>  listener) {
        SearchBox filterField = new SearchBox(VaadinIcons.SEARCH, SearchBox.ButtonPosition.LEFT);
        filterField.setButtonJoined(true);
       filterField.setSearchMode(SearchBox.SearchMode.DEBOUNCE);
        filterField.setPlaceholder(placeHolder);
        filterField.addSearchListener(searchEvent -> listener.accept(searchEvent.getSearchTerm()));
        return filterField;
    }
}
