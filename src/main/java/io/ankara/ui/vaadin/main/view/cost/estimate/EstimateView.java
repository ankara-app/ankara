package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostView;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoiceView;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:03 AM
 */
@UIScope
@SpringView(name =EstimateView.VIEW_NAME)
@SpringComponent
public class EstimateView extends CostView implements View{
    public static final String VIEW_NAME = "Estimate";
    public static final String TOPIC_SHOW = "Show Estimate";

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private EstimateService estimateService;

    public EstimateView() {
        super(Templates.ESTIMATE);
    }

    @EventBusListenerTopic(topic = EstimateView.TOPIC_SHOW)
    @EventBusListenerMethod
    private void showEstimate(Estimate estimate){
        setCost(estimate);
        viewHeader.setValue(estimate.getCode()+" | "+estimate.getCustomer());
    }

    @Override
    protected void delete(Cost cost) {
        estimateService.delete((Estimate)cost);
        mainUI.getNavigator().navigateTo(EstimatesView.VIEW_NAME);
    }

    @Override
    protected void edit(Cost cost) {
        mainUI.getNavigator().navigateTo(EstimateEditView.VIEW_NAME);
        eventBus.publish(EstimateEditView.TOPIC_EDIT,this,estimateService.getEstimate(cost.getId()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
}