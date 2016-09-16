package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import io.ankara.ui.vaadin.main.view.setting.company.CompaniesBeanCRUDComponent;
import io.ankara.ui.vaadin.util.CompanySelectorWindow;
import io.ankara.ui.vaadin.util.SearchField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringComponent
public abstract class CostsHeader extends CustomComponent{

    @Inject
    private CompanyService companyService;

    @Inject
    private CompanySelectorWindow companySelectorWindow;

    @Inject
    private CompaniesBeanCRUDComponent companiesBeanCRUDComponent;

    private Button createButton;

    @PostConstruct
    protected void build() {
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");

        createButton = new Button("Create ", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        createButton.addClickListener(event -> {
            initCostCreate();
        });
        content.addComponent(createButton);

        SearchField searchField = new SearchField();
        searchField.setWidth("100%");
        content.addComponent(searchField);
        content.setExpandRatio(searchField, 1);

        setCompositionRoot(content);
    }

    private void initCostCreate() {
        List<Company> companies = companyService.getCurrentUserCompanies();
        if (companies.isEmpty()) {
            companiesBeanCRUDComponent.create();
        }else if(companies.size()==1){
            Company company = companies.stream().findFirst().get();
            showCostCreateView(company);
        }else{
            companySelectorWindow.show(company -> {
                showCostCreateView(company);
            });
        }
    }

    protected abstract void showCostCreateView(Company company);

}
