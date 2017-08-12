package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Estimate;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostsTable;
import io.ankara.ui.vaadin.main.view.cost.CostsView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringView(name = EstimatesView.VIEW_NAME)
public class EstimatesView extends CostsView<Estimate> {
    public static final String VIEW_NAME = "EstimatesView";

    @Inject
    private EstimatesHeader estimatesHeader;

    @Inject
    private EstimatesTable estimatesTable;

    @Inject
    private ViewHeader viewHeader;

    @PostConstruct
    protected void build(){
        super.build();
    }

    @Override
    protected CostsTable<Estimate> getCostsTable() {
        return estimatesTable;
    }

    @Override
    protected Component getHeader() {
        return estimatesHeader;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        viewHeader.setValue("Estimates");
    }
}