package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.CostsProvider;
import io.ankara.ui.vaadin.main.view.cost.CostsTable;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.stream.Stream;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringComponent
@ViewScope
public class EstimatesTable extends CostsTable<Estimate> {

    private MainUI mainUI;
    private EventBus.UIEventBus eventBus;

    private EstimatesProvider estimatesProvider;

    public EstimatesTable(MainUI mainUI, EventBus.UIEventBus eventBus, EstimatesProvider estimatesProvider) {
        super(Templates.ESTIMATE_ENTRY);
        this.mainUI = mainUI;
        this.eventBus = eventBus;
        this.estimatesProvider = estimatesProvider;
    }

    @PostConstruct
    protected void build() {
        super.build();

        addItemClickListener(event -> {
            Estimate estimate = event.getItem();
            mainUI.getNavigator().navigateTo(EstimateView.VIEW_NAME);
            eventBus.publish(EstimateView.TOPIC_SHOW, this, estimate);
        });
    }

    @Override
    protected CostsProvider<Estimate> getCostProvider() {
        return estimatesProvider;
    }


}
