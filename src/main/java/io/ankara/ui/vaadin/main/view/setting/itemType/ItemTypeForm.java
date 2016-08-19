package io.ankara.ui.vaadin.main.view.setting.itemType;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:17 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemTypeForm extends FormLayout {

    @Inject
    private ItemTypeService itemTypeService;

    private TextField name;

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
                ItemType itemType = (ItemType) fieldGroup.getItemDataSource().getBean();
                if (itemTypeService.save(itemType)) {
                    Notification.show("Item type information saved successfully", Notification.Type.TRAY_NOTIFICATION);
                    if(subWindow != null)
                        subWindow.close();
                }
            } catch (FieldGroup.CommitException e) {
                Notification.show("Enter item type information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });
        addComponents(name,description, save);

    }

    public void edit(ItemType itemType) {
        fieldGroup = BeanFieldGroup.bindFieldsBuffered(itemType, this);
    }


    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
