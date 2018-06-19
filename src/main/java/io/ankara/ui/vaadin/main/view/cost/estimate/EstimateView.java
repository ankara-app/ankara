package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.spring.annotation.SpringView;
import io.ankara.domain.Estimate;
import io.ankara.domain.Estimate;
import io.ankara.domain.Invoice;
import io.ankara.domain.User;
import io.ankara.service.EstimateService;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.ViewHeader;
import io.ankara.ui.vaadin.main.view.cost.CostView;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:03 AM
 */
@SpringView(name = EstimateView.VIEW_NAME)
public class EstimateView extends CostView<Estimate> {
    public static final String VIEW_NAME = "Estimate";
    public static final String TOPIC_SHOW = "Show Estimate";

    @Inject
    private MainUI mainUI;

    @Inject
    private ViewHeader viewHeader;

    @Inject
    private EstimateService estimateService;

    @Inject
    private User user;

    public EstimateView() {
        super(Templates.ESTIMATE);
    }

    @EventBusListenerTopic(topic = EstimateView.TOPIC_SHOW)
    @EventBusListenerMethod
    private void showEstimate(Estimate estimate) {
        setCost(estimate);
        viewHeader.setValue(estimate.getCode() + " | " + estimate.getCustomer());
    }

    @Override
    protected void delete() {

        String confirmationMessage = "Are you sure that you want this estimate to be completely removed?";
        ConfirmDialog.show(getUI(), "Please confirm ...", confirmationMessage, "Proceed", "Cancel", (ConfirmDialog.Listener) confirmDialog -> {
            if (confirmDialog.isConfirmed()) {
                estimateService.delete(getCost());
                mainUI.getNavigator().navigateTo(EstimatesView.VIEW_NAME);
            }
        });

    }

    @Override
    protected void edit() {
        mainUI.getNavigator().navigateTo(EstimateEditView.VIEW_NAME);
        eventBus.publish(EstimateEditView.TOPIC_EDIT, this, estimateService.getEstimate(getCost().getId()));
    }

    @Override
    protected void copy() {
        mainUI.getNavigator().navigateTo(EstimateEditView.VIEW_NAME);
        eventBus.publish(EstimateEditView.TOPIC_EDIT, this, getCopy());
    }

    private Estimate getCopy() {
        Estimate estimate = getCost().clone();
        estimate.setCreator(user);
        estimate.setCode(estimateService.nextEstimateNumber(estimate.getCompany()));
        return estimate;
    }

    @Override
    protected String getPrintURL(Estimate estimate) {
        return "/estimate/print/" + estimate.getId();
    }

    @Override
    protected String getPdfURL(Estimate estimate) {
        return "/estimate/pdf/" + estimate.getId();
    }

}