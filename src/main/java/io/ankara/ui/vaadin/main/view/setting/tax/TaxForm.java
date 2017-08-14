package io.ankara.ui.vaadin.main.view.setting.tax;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Tax;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.util.NotificationUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/31/16 7:26 PM
 */
@UIScope
@SpringComponent
public class TaxForm extends FormLayout{

    @Inject
    private TaxService taxService;

    private TextField name;

    private TextField percentage;

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

        percentage = new TextField("Percentage");
        percentage.setNullRepresentation("");
        percentage.setWidth("100%");

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
                Tax tax = (Tax) fieldGroup.getItemDataSource().getBean();
                if (taxService.save(tax)) {
                    NotificationUtils.showSuccess("Tax information saved successfully",null);
                    if(subWindow != null)
                        subWindow.close();
                }
            } catch (FieldGroup.CommitException e) {
                Notification.show("Enter tax information correctly", Notification.Type.WARNING_MESSAGE);
            }
        });
        addComponents(name,percentage,description, save);

    }

    public void edit(Tax tax) {
        fieldGroup = BeanFieldGroup.bindFieldsBuffered(tax, this);
    }


    public void setSubWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
