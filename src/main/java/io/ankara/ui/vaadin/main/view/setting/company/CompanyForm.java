package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import io.ankara.ui.vaadin.util.NotificationUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
//import org.vaadin.easyuploads.ImagePreviewField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 9:10 PM
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SpringComponent
public class CompanyForm extends FormLayout {

    @Inject
    private CompanyService companyService;

//    private ImagePreviewField picture;

    private TextField name;

    private TextField email;

    private TextField phone;

    private TextField fax;

    private TextArea address;

    private TextArea notes;

    private TextArea terms;

    private BeanValidationBinder<Company> companyBinder = new BeanValidationBinder<>(Company.class);

    private Window subWindow;
    private Company company;

    @PostConstruct
    private void build() {

        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        addStyleName(ValoTheme.LAYOUT_WELL);
        addStyleName(ValoTheme.LAYOUT_CARD);

//        picture = new ImagePreviewField();

        name = new TextField("Name");
        name.setWidth("100%");

        email = new TextField("Email");
        email.setWidth("100%");

        phone = new TextField("Phone");
        phone.setWidth("100%");

        fax = new TextField("Fax");
        fax.setWidth("100%");

        address = new TextArea("Address");
        address.setWidth("100%");
        address.setRows(4);

        notes = new TextArea("Notes");
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        notes.setDescription("Specify other notes ...");
        notes.setRows(6);
        notes.setWidth("100%");

        terms = new TextArea("Terms");
        terms.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        terms.setDescription("Specify terms ...");
        terms.setRows(6);
        terms.setWidth("100%");

        companyBinder.bindInstanceFields(this);

        Button save = new Button("Save");
        save.setIcon(FontAwesome.SAVE);
        save.setWidth("200px");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(event -> {
            try {
                companyBinder.writeBean(company);
                if (company.getId() == null ? companyService.create(company) : companyService.save(company)) {
                    NotificationUtils.showSuccess("Company information saved successfully", null);
                    if (subWindow != null)
                        subWindow.close();
                }
            } catch (ValidationException e) {
                Notification.show("Enter company information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });

        addComponents(name, email, phone, fax, address,notes, terms, save);

    }

    public void edit(Company company) {
        this.company = company;
        companyBinder.readBean(company);
    }

    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
