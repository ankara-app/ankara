package io.ankara.ui.vaadin.main.view.setting.tax;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.domain.Tax;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.util.BeanCRUDComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/31/16 7:18 PM
 */
@UIScope
@SpringComponent
public class TaxBeanCRUDComponent extends BeanCRUDComponent{

    @Inject
    private TaxService taxService;

    @Inject
    private TaxForm taxForm;

    private VerticalLayout holder;

    private Company company;

    @PostConstruct
    protected void build() {
        setSizeFull();
        taxForm.setWidth("60%");

        holder = new VerticalLayout(taxForm);
        holder.setSizeFull();
        holder.setMargin(true);
        holder.setComponentAlignment(taxForm, Alignment.MIDDLE_CENTER);

        super.build(Tax.class);
        table.setVisibleColumns("name", "percentage", "description","Remove");
    }

    @Override
    protected void removeItem(Object itemID) {
        Tax tax = (Tax) itemID;
        taxService.delete(tax);
    }

    @Override
    protected Collection loadBeans() {
        return taxService.getTaxes(company);
    }

    @Override
    protected Component getCreateComponent() {
        popUpWindow.setCaption("Create new tax");
        taxForm.edit(new Tax(company));
        taxForm.setSubWindow(popUpWindow);

        return holder;
    }

    @Override
    protected Component getBeanComponent(Object bean) {
        taxForm.edit((Tax) bean);
        taxForm.setSubWindow(popUpWindow);
        return holder;
    }

    public void setCompany(Company company) {
        this.company = company;
        reload();
    }
}
