package io.ankara.ui.vaadin.util;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.main.MainUI;

import javax.inject.Inject;
import java.util.function.Consumer;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/8/16.
 */
@UIScope
@SpringComponent
public class CompanySelectorWindow extends Window {

    @Inject
    private MainUI mainUI;

    @Inject
    private CompanyService companyService;

    private ComboBox<Company> companySelector;

    private Consumer<Company> companyConsumer;

    public CompanySelectorWindow() {
        super("Select company");
        setModal(true);
        center();
        setResizable(false);
        setWidth("30%");
        setHeight("20%");
        setIcon(FontAwesome.BUILDING);

        companySelector = new ComboBox<>("Company");
        companySelector.setWidth("100%");
        companySelector.addValueChangeListener( event -> {
            Company company = event.getValue();
            if (company != null) {
                companyConsumer.accept(company);
                companyConsumer = null;
                close();
            }
        });

        FormLayout layout = new FormLayout(companySelector);
        layout.setMargin(true);
        layout.addStyleName(AnkaraTheme.FORMLAYOUT_LIGHT);
        layout.setComponentAlignment(companySelector, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    public void show(Consumer<Company> companyConsumer) {
        this.companyConsumer = companyConsumer;

        companySelector.setItems(companyService.getCurrentUserCompanies());

        mainUI.addWindow(this);
    }
}
