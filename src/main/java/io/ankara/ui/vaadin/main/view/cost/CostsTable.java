package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.ui.Grid;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.utils.DateUtils;
import io.ankara.utils.NumberUtils;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 7:35 PM
 */
public abstract class CostsTable<T extends Cost> extends Grid<T> {


    protected void build() {
        setSizeFull();

        addColumn(T::getCode).setId("code").setCaption("Code");
        addColumn(cost-> DateUtils.formatDateTime(cost.getTimeCreated())).setId("timeCreated").setCaption("Created on").setHidable(true).setHidden(true);
        addColumn(T::getCustomer).setId("customer").setCaption("Customer");
        addColumn(T::getSubject).setId("subject").setCaption("Subject");
        addColumn(cost -> NumberUtils.formatMoney( cost.getAmountDue(),cost.getCurrency())).setId("amount").setCaption("Amount").setStyleGenerator(item -> AnkaraTheme.TEXT_RIGHT);
        addColumn(cost-> DateUtils.formatDate(cost.getIssueDate())).setId("issueDate").setCaption("Issued on").setHidable(true).setHidden(true);
        addColumn(T::getCompany).setCaption("Company").setId("company").setHidable(true).setHidden(true);
        addColumn(T::getCreator).setCaption("Creator").setId("creator").setHidable(true).setHidden(true);

        setDataProvider(getCostProvider());
    }

    protected abstract CostsProvider<T> getCostProvider();

    public void reload(){
        getDataProvider().refreshAll();
    }

}
