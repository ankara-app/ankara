package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import io.ankara.domain.Cost;

import static  io.ankara.ui.vaadin.util.VaadinUtils.addFilteringTextField;

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


        addFilteringTextField(headerRow,"code","Filter by code", event -> getCostsTable().getCostProvider().setCodeFilter(event.getValue()));
        addFilteringTextField(headerRow,"customer","Filter by customer name", event -> getCostsTable().getCostProvider().setCustomerNameFilter(event.getValue()));
        addFilteringTextField(headerRow,"subject","Filter by subject", event -> getCostsTable().getCostProvider().setSubjectFilter(event.getValue()));

    }

    protected abstract CostsTable<T> getCostsTable();

    protected abstract Component getHeader();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getCostsTable().reload();
    }
}
