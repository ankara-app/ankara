package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import io.ankara.ui.vaadin.util.BeanCRUDComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 6:51 PM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompaniesBeanCRUDComponent extends BeanCRUDComponent {

    @Inject
    private CompanyService companyService;

    @Inject
    private CompanyForm companyForm;

    @Inject
    private CompanyDetailsView detailsView;

    @PostConstruct
    protected void build() {
        setSizeFull();
        super.build(Company.class);
        grid.setColumns("registration", "name", "VAT", "paymentInformation", "address");
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(grid.getContainerDataSource().size()+1);
    }

    @Override
    protected Collection loadBeans() {
        return companyService.getCurrentUserCompanies();
    }

    @Override
    public void reload() {
        super.reload();
        grid.setHeightByRows(grid.getContainerDataSource().size()+1);
    }

    @Override
    protected Component getCreateComponent() {
        popUpWindow.setCaption("Create new company");
        companyForm.edit(new Company());
        companyForm.setSubWindow(popUpWindow);
        companyForm.setWidth("60%");


        VerticalLayout holder = new VerticalLayout(companyForm);
//        holder.setSizeFull();
        holder.setMargin(true);
        holder.setComponentAlignment(companyForm, Alignment.MIDDLE_CENTER);
        return holder;
    }

    @Override
    protected Component getBeanComponent(Object bean) {
        detailsView.show((Company) bean);
        return detailsView;
    }
}
