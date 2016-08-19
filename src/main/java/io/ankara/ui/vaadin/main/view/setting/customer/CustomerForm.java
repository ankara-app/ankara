package io.ankara.ui.vaadin.main.view.setting.customer;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Customer;
import io.ankara.service.CustomerService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 9:18 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerForm extends FormLayout {

    @Inject
    private CustomerService customerService;

    private TextField name;

    private TextField email;

    private TextArea address;

    private TextArea description;

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

        email = new TextField("Email");
        email.setNullRepresentation("");
        email.setWidth("100%");

        address = new TextArea("Address");
        address.setNullRepresentation("");
        address.setWidth("100%");
        address.setRows(4);

        description = new TextArea("Description");
        description.setNullRepresentation("");
        description.setWidth("100%");
        description.setRows(4);

        Button save = new Button("Save");
        save.setIcon(FontAwesome.SAVE);
        save.setWidth("200px");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(event -> {
            try {
                fieldGroup.commit();
                Customer customer = (Customer) fieldGroup.getItemDataSource().getBean();
                if (customerService.save(customer)) {
                    Notification.show("Customer information saved successfully", Notification.Type.TRAY_NOTIFICATION);
                    if(subWindow != null)
                        subWindow.close();
                }
            } catch (FieldGroup.CommitException e) {
                Notification.show("Enter customer information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });
        addComponents(name, email, address, description, save);

    }

    public void edit(Customer customer) {
        fieldGroup = BeanFieldGroup.bindFieldsBuffered(customer, this);
    }


    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
