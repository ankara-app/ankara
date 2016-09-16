package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Table;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimateView;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@UIScope
@SpringComponent
public class EstimatesTable extends Table{

    @Inject
    private EstimateService estimateService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @Inject
    private EventBus.UIEventBus eventBus;

    @PostConstruct
    private void build(){
        BeanItemContainer container = new BeanItemContainer<>(Estimate.class);
        container.addNestedContainerProperty("creator.fullName");
        setContainerDataSource(container);

        addItemClickListener(event -> {
            Estimate estimate = (Estimate) event.getItemId();
            mainUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
            eventBus.publish(EstimateView.TOPIC_SHOW,this,estimate);
        });


        setVisibleColumns("code", "timeCreated", "company", "customer","subject","subtotal","totalTax","discount","amountDue","creator.fullName");
        setColumnHeaders("Estimate ID","Time Created","Company","Customer","Subject","Subtotal","Total Tax","Discount","Amount Due","Created By");

    }

    public void reload() {
        BeanItemContainer container = (BeanItemContainer) getContainerDataSource();
        container.removeAllItems();
        container.addAll(estimateService.getEstimates(userService.getCurrentUser()));
    }
}
