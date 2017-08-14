package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.AnkaraTheme;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 7:47 PM
 */
public abstract class CostsView<T extends Cost> extends CustomComponent implements View {

    protected void build(){
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setSpacing(true);
        content.setMargin(false);
        setCompositionRoot(content);

        Component header = getHeader();
        header.setWidth("50%");
        content.addComponent(header);


        CostsTable<T> costsTable = getCostsTable();
        content.addComponent(costsTable);
        content.setExpandRatio(costsTable,1);

        HeaderRow headerRow = getCostsTable().appendHeaderRow();
        createFilteringTextField(headerRow,"code","Filter by code",event -> getCostsTable().getCostProvider().setCodeFilter(event.getValue()));
        createFilteringTextField(headerRow,"customer","Filter by customer name",event -> getCostsTable().getCostProvider().setCustomerNameFilter(event.getValue()));
        createFilteringTextField(headerRow,"subject","Filter by subject",event -> getCostsTable().getCostProvider().setSubjectFilter(event.getValue()));

    }

    private TextField createFilteringTextField(HeaderRow headerRow,String propertyId,String placeHolder, HasValue.ValueChangeListener<String> listener) {
        TextField filterField = new TextField();
        filterField.setPlaceholder(placeHolder);
        filterField.setWidth("100%");
        filterField.addStyleNames(AnkaraTheme.TEXTFIELD_TINY,AnkaraTheme.TEXT_SMALL);
        filterField.addValueChangeListener(listener);
        headerRow.getCell(propertyId).setComponent(filterField);
        return filterField;
    }

    protected abstract CostsTable<T> getCostsTable();

    protected abstract Component getHeader();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getCostsTable().reload();
    }
}
