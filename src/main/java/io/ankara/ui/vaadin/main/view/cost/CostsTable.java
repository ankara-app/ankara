package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.ui.Grid;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.util.TemplateView;
import io.ankara.utils.DateUtils;
import io.ankara.utils.NumberUtils;
import org.thymeleaf.TemplateEngine;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 7:35 PM
 */
public abstract class CostsTable<T extends Cost> extends Grid<T> {


    private String template;

    @Inject
    private TemplateEngine templateEngine;

    public CostsTable(String template) {
        this.template = template;
    }

    protected void build() {
        setSizeFull();
        setHeaderVisible(false);
        setBodyRowHeight(90);

        addComponentColumn(cost -> {
            return new TemplateView(templateEngine)
                    .setTemplatePath(template)
                    .putBinding("cost",cost)
                    .render().withFullWidth();
        });
//        addColumn(T::getCode).setId("code").setCaption("Code");
//        addColumn(cost-> DateUtils.formatDateTime(cost.getTimeCreated())).setId("timeCreated").setCaption("Created on").setHidable(true).setHidden(true);
//        addColumn(T::getCustomer).setId("customer").setCaption("Customer");
//        addColumn(T::getSubject).setId("subject").setCaption("Subject");
//        addColumn(cost -> NumberUtils.formatMoney( cost.getAmountDue(),cost.getCurrency())).setId("amount").setCaption("Amount").setStyleGenerator(item -> AnkaraTheme.TEXT_RIGHT);
//        addColumn(cost-> DateUtils.formatDate(cost.getIssueDate())).setId("issueDate").setCaption("Issued on").setHidable(true).setHidden(true);
//        addColumn(T::getCompany).setCaption("Company").setId("company").setHidable(true).setHidden(true);
//        addColumn(T::getCreator).setCaption("Creator").setId("creator").setHidable(true).setHidden(true);

        setDataProvider(getCostProvider());
    }

    protected abstract CostsProvider<T> getCostProvider();

    public void reload(){
        getDataProvider().refreshAll();
    }

}
