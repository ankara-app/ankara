package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import io.ankara.domain.Company;
import io.ankara.domain.Estimate;
import io.ankara.domain.User;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostsTable;
import io.ankara.ui.vaadin.main.view.cost.CostsView;
import org.vaadin.spring.events.EventBus;

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
    public static final String VIEW_NAME = "Estimates";

    @Inject
    private EstimatesTable estimatesTable;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private EstimateService estimateService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @PostConstruct
    protected void build(){
        super.build();
    }

    @Override
    protected CostsTable<Estimate> getCostsTable() {
        return estimatesTable;
    }


    @Override
    public void attach() {
        super.attach();
        createMenuItem.setText("Create Estimate");
        viewHeader.setValue("Estimates");
    }

    private Estimate createEstimate(Company company) {
        String currency = "TZS";
        String code = estimateService.nextEstimateNumber(company);
        User creator = userService.getCurrentUser();

        return new Estimate(creator, company, currency, code);
    }

    private void navigateEstimateCreateView(Estimate estimate) {
        mainUI.getNavigator().navigateTo(EstimateEditView.VIEW_NAME);
        eventBus.publish(EstimateEditView.TOPIC_EDIT, this, estimate);
    }

    @Override
    protected void showCostCreateView(Company company) {
        navigateEstimateCreateView(createEstimate(company));
    }
}