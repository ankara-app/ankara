package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.service.EstimateService;
import io.ankara.service.InvoiceService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostEditView;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoiceEditView;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoiceView;
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
@UIScope
@SpringView(name = EstimateEditView.VIEW_NAME)
@SpringComponent
public class EstimateEditView extends CostEditView {
    public static final String VIEW_NAME = "EstimateForm";
    public static final String TOPIC_EDIT = "Edit Estimate";

    @Inject
    private EstimateService estimateService;

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

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
