package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.ui.Grid;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.main.MainUI;
import io.ankara.ui.vaadin.main.view.cost.estimate.EstimateView;
import io.ankara.utils.DateUtils;
import io.ankara.utils.NumberUtils;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 7:35 PM
 */
public abstract class CostsTable<T extends Cost> extends Grid<T> {


    protected void build() {
        setSizeFull();

        addColumn(T::getCode).setCaption("Code");
        addColumn(cost-> DateUtils.formatDateTime(cost.getTimeCreated())).setCaption("Created on").setHidable(true).setHidden(true);
        addColumn(T::getCustomer).setCaption("Customer");
        addColumn(T::getSubject).setCaption("Subject");
        addColumn(cost -> NumberUtils.formatMoney( cost.getAmountDue(),cost.getCurrency())).setCaption("Amount").setStyleGenerator(item -> AnkaraTheme.TEXT_RIGHT);
        addColumn(cost-> DateUtils.formatDate(cost.getIssueDate())).setCaption("Issued on").setHidable(true).setHidden(true);
        addColumn(T::getCompany).setCaption("Company").setHidable(true).setHidden(true);
        addColumn(T::getCreator).setCaption("Creator").setHidable(true).setHidden(true);
    }

    public abstract void reload();

}
