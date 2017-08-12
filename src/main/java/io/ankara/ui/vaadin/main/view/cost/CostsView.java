package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimatesHeader;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimatesTable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
    }

    protected abstract CostsTable<T> getCostsTable();

    protected abstract Component getHeader();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getCostsTable().reload();
    }
}
