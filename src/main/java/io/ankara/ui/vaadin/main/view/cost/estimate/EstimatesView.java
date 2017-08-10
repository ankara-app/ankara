package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import io.ankara.ui.vaadin.main.view.ViewHeader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringView(name = EstimatesView.VIEW_NAME)
public class EstimatesView extends CustomComponent implements View {
    public static final String VIEW_NAME = "EstimatesView";

    @Inject
    private EstimatesHeader estimatesHeader;

    @Inject
    private EstimatesTable estimatesTable;

    @Inject
    private ViewHeader viewHeader;

    @PostConstruct
    private void build(){
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setSpacing(true);
        content.setMargin(false);
        setCompositionRoot(content);

        estimatesHeader.setWidth("50%");
        content.addComponent(estimatesHeader);

//        estimatesTable.setSizeFull();
        content.addComponent(estimatesTable);
        content.setExpandRatio(estimatesTable,1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        estimatesTable.reload();
        viewHeader.setValue("Estimates");
    }
}