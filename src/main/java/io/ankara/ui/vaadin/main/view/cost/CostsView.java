package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import io.ankara.domain.Company;
import io.ankara.domain.Cost;
import io.ankara.service.CompanyService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.main.MainMenu;
import io.ankara.ui.vaadin.main.view.setting.company.CompaniesBeanCRUDComponent;
import io.ankara.ui.vaadin.util.CompanySelectorWindow;
import org.vaadin.addons.searchbox.SearchBox;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import javax.inject.Inject;
import java.util.List;

import static io.ankara.ui.vaadin.util.VaadinUtils.createFilteringTextField;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 7:47 PM
 */
public abstract class CostsView<T extends Cost> extends CustomComponent implements View {

    @Inject
    private MainMenu mainMenu;
    @Inject
    private CompanySelectorWindow companySelectorWindow;

    @Inject
    private CompaniesBeanCRUDComponent companiesBeanCRUDComponent;

    protected MenuBar.MenuItem createMenuItem;

    @Inject
    private CompanyService companyService;

    protected void build() {
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(false);
        setCompositionRoot(content);


        SearchBox codeFilter = createFilteringTextField("Filter by code", search -> getCostsTable().getCostProvider().setCodeFilter(search));
        SearchBox nameFilter = createFilteringTextField("Filter by customer", search -> getCostsTable().getCostProvider().setCustomerNameFilter(search));
        SearchBox subjectFilter = createFilteringTextField("Filter by subject", search -> getCostsTable().getCostProvider().setSubjectFilter(search));
        content.addComponent(
                new MHorizontalLayout(codeFilter, nameFilter, subjectFilter)
                        .withStyleName(AnkaraTheme.HORIZONTAL_LAYOUT_SEARCH_HOLDER)
                        .withSpacing(true)
                        .withMargin(false)
        );

        CostsTable<T> costsTable = getCostsTable();
        content.addComponent(costsTable);
        content.setExpandRatio(costsTable, 1);
    }

    protected abstract CostsTable<T> getCostsTable();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getCostsTable().reload();
    }

    @Override
    public void attach() {
        super.attach();
        createMenuItem = mainMenu
                .getMenuBar()
                .addItemAsFirst("Create", VaadinIcons.PLUS, selectedItem -> initCostCreate());
    }

    @Override
    public void detach() {
        super.detach();
        mainMenu.getMenuBar().removeItem(createMenuItem);
    }

    private void initCostCreate() {
        List<Company> companies = companyService.getCurrentUserCompanies();
        if (companies.isEmpty()) {
            companiesBeanCRUDComponent.create();
        } else if (companies.size() == 1) {
            Company company = companies.stream().findFirst().get();
            showCostCreateView(company);
        } else {
            companySelectorWindow.show(company -> {
                showCostCreateView(company);
            });
        }
    }

    protected abstract void showCostCreateView(Company company);

    public MenuBar.MenuItem getCreateMenuItem() {
        return createMenuItem;
    }
}
