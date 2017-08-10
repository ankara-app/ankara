package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostEditView;
import io.ankara.ui.vaadin.util.NotificationUtils;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringView(name = EstimateEditView.VIEW_NAME)
public class EstimateEditView extends CostEditView {
    public static final String VIEW_NAME = "EstimateForm";
    public static final String TOPIC_EDIT = "Edit Estimate";

    @Inject
    private EstimateService estimateService;

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    public EstimateEditView() {
        super(Estimate.class);
    }

    @EventBusListenerTopic(topic = TOPIC_EDIT)
    @EventBusListenerMethod
    private void editEstimate(Estimate estimate){
        editCost(estimate);
        viewHeader.setValue(estimate.getCompany()+" Estimate");
    }

    @Override
    protected void doSave(Cost cost) {
        Estimate estimate = (Estimate) cost;
        if(estimateService.save(estimate)){
            NotificationUtils.showSuccess("Estimate saved successfully",null);
            mainUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
            eventBus.publish(EstimateView.TOPIC_SHOW,this,estimate);
        }
    }

}
