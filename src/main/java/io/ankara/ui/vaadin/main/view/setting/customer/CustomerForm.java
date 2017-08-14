package io.ankara.ui.vaadin.main.view.setting.customer;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Customer;
import io.ankara.service.CustomerService;
import io.ankara.ui.vaadin.util.NotificationUtils;
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
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SpringComponent
public class CustomerForm extends FormLayout {

    @Inject
    private CustomerService customerService;

    private TextField name;

    private TextField email;

    private TextArea address;

    private TextArea description;

    private Binder<Customer> customerBinder = new Binder<Customer>(Customer.class);
    private Customer customer;
    private Window subWindow;

    @PostConstruct
    private void build() {

        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        addStyleName(ValoTheme.LAYOUT_WELL);
        addStyleName(ValoTheme.LAYOUT_CARD);

        name = new TextField("Name");
        name.setWidth("100%");

        email = new TextField("Email");
        email.setWidth("100%");

        address = new TextArea("Address");
        address.setWidth("100%");
        address.setRows(4);

        description = new TextArea("Description");
        description.setWidth("100%");
        description.setRows(4);

        Button save = new Button("Save");
        save.setIcon(FontAwesome.SAVE);
        save.setWidth("200px");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(event -> {
            try {
                customerBinder.writeBean(customer);
                if (customerService.save(customer)) {
                    NotificationUtils.showSuccess("Customer information saved successfully", null);
                    if(subWindow != null)
                        subWindow.close();
                }
            } catch (ValidationException e) {
                Notification.show("Enter customer information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });
        addComponents(name, email, address, description, save);
        customerBinder.bindInstanceFields(this);

    }

    public void edit(Customer customer) {
        this.customer = customer;
        customerBinder.readBean(customer);
    }


    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
