package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Table;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimateView;
import io.ankara.ui.vaadin.util.TableDecorator;
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
public class EstimatesTable extends Table {

    @Inject
    private EstimateService estimateService;

    @Inject
    private UserService userService;

    @Inject
    private MainUI mainUI;

    @Inject
    private EventBus.UIEventBus eventBus;

    @Inject
    private TableDecorator tableDecorator;

    @PostConstruct
    private void build() {
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);

        BeanItemContainer container = new BeanItemContainer<>(Estimate.class);
        setContainerDataSource(container);

        addItemClickListener(event -> {
            Estimate estimate = (Estimate) event.getItemId();
            mainUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
            eventBus.publish(EstimateView.TOPIC_SHOW, this, estimate);
        });

        tableDecorator.decorate(this, Templates.ESTIMATE_ENTRY, "Estimate");

    }

    public void reload() {
        BeanItemContainer container = (BeanItemContainer) getContainerDataSource();
        container.removeAllItems();
        container.addAll(estimateService.getEstimates(userService.getCurrentUser()));

        int size = container.size();
        setPageLength(size > 10 ? 10 : size < 5 ? 5 : 10);
    }
}
