package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 9:10 PM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyForm extends FormLayout {

    @Inject
    private CompanyService companyService;

    private TextField name;

    private TextField registration;

    private TextField VAT;

    private TextArea paymentInformation;

    private TextArea address;

    private TextArea description;

    private TextArea notes;

    private TextArea terms;

    private BeanFieldGroup fieldGroup;

    private Window subWindow;

    @PostConstruct
    private void build() {

        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        addStyleName(ValoTheme.LAYOUT_WELL);
        addStyleName(ValoTheme.LAYOUT_CARD);

        name = new TextField("Name");
        name.setNullRepresentation("");
        name.setWidth("100%");

        registration = new TextField("Registration Number");
        registration.setNullRepresentation("");
        registration.setWidth("100%");

        VAT = new TextField("VAT");
        VAT.setNullRepresentation("");
        VAT.setWidth("100%");

        paymentInformation = new TextArea("Payment Information");
        paymentInformation.setNullRepresentation("");
        paymentInformation.setWidth("100%");
        paymentInformation.setRows(4);

        address = new TextArea("Address");
        address.setNullRepresentation("");
        address.setWidth("100%");
        address.setRows(4);

        description = new TextArea("Description");
        description.setNullRepresentation("");
        description.setWidth("100%");
        description.setRows(4);

        notes = new TextArea("Notes");
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        notes.setInputPrompt("Specify other notes ...");
        notes.setRows(6);
        notes.setWidth("100%");
        notes.setNullRepresentation("");

        terms = new TextArea("Terms");
        terms.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        terms.setInputPrompt("Specify terms ...");
        terms.setRows(6);
        terms.setWidth("100%");
        terms.setNullRepresentation("");

        Button save = new Button("Save");
        save.setIcon(FontAwesome.SAVE);
        save.setWidth("200px");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    fieldGroup.commit();
                    Company company = (Company) fieldGroup.getItemDataSource().getBean();
                    if (company.getId() == null ? companyService.create(company) : companyService.save(company)) {
                        Notification.show("Company information saved successfully", Notification.Type.TRAY_NOTIFICATION);
                        if(subWindow != null)
                            subWindow.close();
                    }
                } catch (FieldGroup.CommitException e) {
                    Notification.show("Enter company information correctly", Notification.Type.WARNING_MESSAGE);
                }
            }
        });
        addComponents(name, registration, VAT, paymentInformation, address, description,notes,terms, save);

    }

    public void edit(Company company) {
        fieldGroup = BeanFieldGroup.bindFieldsBuffered(company, this);
    }

    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
