package io.ankara.ui.vaadin.main.view.setting.company;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.service.CompanyService;
import org.vaadin.easyuploads.ImagePreviewField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 9:10 PM
 */
@UIScope
@SpringComponent
public class CompanyForm extends FormLayout {

    @Inject
    private CompanyService companyService;

    private ImagePreviewField picture;

    private TextField name;

    private TextField email;

    private TextField phone;

    private TextField fax;

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

        picture = new ImagePreviewField();

        name = new TextField("Name");
        name.setNullRepresentation("");
        name.setWidth("100%");

        email = new TextField("Email");
        email.setNullRepresentation("");
        email.setWidth("100%");

        phone = new TextField("Phone");
        phone.setNullRepresentation("");
        phone.setWidth("100%");

        fax = new TextField("Fax");
        fax.setNullRepresentation("");
        fax.setWidth("100%");

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
                        if (subWindow != null)
                            subWindow.close();
                    }
                } catch (FieldGroup.CommitException e) {
                    Notification.show("Enter company information correctly", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        addComponents(picture,name, email, phone, fax, address, description, notes, terms, save);

    }

    public void edit(Company company) {
        fieldGroup = BeanFieldGroup.bindFieldsBuffered(company, this);
    }

    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
