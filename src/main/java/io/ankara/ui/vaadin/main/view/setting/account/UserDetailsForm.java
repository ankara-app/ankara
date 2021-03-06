package io.ankara.ui.vaadin.main.view.setting.account;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.User;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.util.NotificationUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 3:08 PM
 */
@UIScope
@SpringComponent
public class UserDetailsForm extends FormLayout {

    private TextField fullName;

    @Inject
    private UserService userService;

    @PostConstruct
    private void build() {
        setSpacing(true);
        setMargin(true);

        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        fullName = new TextField("Your name");
        fullName.setWidth("300px");
        fullName.setPlaceholder("What is your name?");


        User user = userService.getCurrentUser();

        BeanValidationBinder<User> userBinder = new BeanValidationBinder<>(User.class);
        userBinder.bindInstanceFields(this);

        userBinder.readBean(user);

        Button save = new Button("Save", FontAwesome.USER);
        save.setWidth("200px");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(event -> {
            try {
                userBinder.writeBean(user);
                if (userService.save(user)) {
                    NotificationUtils.showSuccess("Your details are saved successfully", null);
                    getUI().getPage().reload();
                }

            } catch (ValidationException e) {
                Notification.show("Please enter information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });

        addComponents(fullName, save);
        setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
    }
}
