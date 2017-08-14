package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import io.ankara.domain.Company;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.CostsHeader;
import io.ankara.ui.vaadin.main.view.cost.invoice.InvoiceEditView;
import org.vaadin.spring.events.EventBus;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@UIScope
@SpringComponent
public class EstimatesHeader extends CostsHeader{
    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private EstimateService estimateService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

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
