package io.ankara.ui.vaadin.main.view.setting.itemType;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;
import io.ankara.ui.vaadin.util.NotificationUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:17 AM
 */

@UIScope
@SpringComponent
public class ItemTypeForm extends FormLayout {

    @Inject
    private ItemTypeService itemTypeService;

    private TextField name;

    private TextArea description;

    private BeanValidationBinder<ItemType> itemTypeBinder = new BeanValidationBinder<>(ItemType.class);
    private ItemType itemType;

    private Window subWindow;

    @PostConstruct
    private void build() {

        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        addStyleName(ValoTheme.LAYOUT_WELL);
        addStyleName(ValoTheme.LAYOUT_CARD);

        name = new TextField("Name");
        name.setWidth("100%");

        description = new TextArea("Description");
        description.setWidth("100%");
        description.setRows(4);


        Button save = new Button("Save");
        save.setIcon(FontAwesome.SAVE);
        save.setWidth("200px");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(event -> {
            try {
                itemTypeBinder.writeBean(itemType);
                if (itemTypeService.save(itemType)) {
                    NotificationUtils.showSuccess("Item type information saved successfully", null);
                    if(subWindow != null)
                        subWindow.close();
                }
            } catch (ValidationException e) {
                Notification.show("Enter item type information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });
        addComponents(name,description, save);
        itemTypeBinder.bindInstanceFields(this);

    }

    public void edit(ItemType itemType) {
        this.itemType = itemType;
        itemTypeBinder.readBean(itemType);
    }


    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
