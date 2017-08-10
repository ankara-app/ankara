package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.data.ValueProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Grid;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringComponent
@ViewScope
public class EstimatesTable extends Grid<Estimate>{

    @Inject
    private EstimateService estimateService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @Inject
    private EventBus.UIEventBus eventBus;


    @PostConstruct
    private void build() {
        setSizeFull();

        addItemClickListener(event -> {
            Estimate estimate = event.getItem();
            mainUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
            eventBus.publish(EstimateView.TOPIC_SHOW, this, estimate);
        });

        addColumn(Estimate::getCode).setCaption("Code");
        addColumn(Estimate::getTimeCreated).setCaption("Created on");
        addColumn(Estimate::getCustomer).setCaption("Customer");
        addColumn(Estimate::getSubject).setCaption("Subject");
        addColumn(estimate -> estimate.getAmountDue()+" "+estimate.getCurrency()).setCaption("Amount");
        addColumn(Estimate::getIssueDate).setCaption("Issued on");
        addColumn(Estimate::getCompany).setCaption("Company");
        addColumn(Estimate::getCreator).setCaption("Creator");
    }

    public void reload() {
        //TODO IMPLEMENT LAZY LOADING
        setItems(estimateService.getEstimates(userService.getCurrentUser()));

//        int size = container.size();
//        setPageLength(size > 10 ? 10 : size < 5 ? 5 : 10);
//        setPageLength(10);
    }

}
