package io.ankara.ui.vaadin.main.view.setting.itemType;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;
import io.ankara.ui.vaadin.util.BeanCRUDComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 11:16 AM
 */
@UIScope
@SpringComponent
public class ItemTypeBeanCRUDComponent extends BeanCRUDComponent<ItemType>{

    @Inject
    private ItemTypeService itemTypeService;

    @Inject
    private ItemTypeForm itemTypeForm;

    private VerticalLayout holder;

    private Company company;

    @PostConstruct
    protected void build() {
        setSizeFull();
        itemTypeForm.setWidth("60%");

        holder = new VerticalLayout(itemTypeForm);
        holder.setSizeFull();
        holder.setMargin(true);
        holder.setComponentAlignment(itemTypeForm, Alignment.MIDDLE_CENTER);



        table.addColumn(ItemType::getName).setCaption("Name");
        table.addColumn(ItemType::getDescription).setCaption("Description");
        ;
        super.build();
    }

    @Override
    protected void removeItem(Object itemID) {
        ItemType itemType = (ItemType) itemID;
        itemTypeService.delete(itemType);
    }

    @Override
    protected Collection loadBeans() {
        return itemTypeService.getItemTypes(company);
    }

    @Override
    protected Component getCreateComponent() {
        popUpWindow.setCaption("Create new item type");
        itemTypeForm.edit(new ItemType(company));
        itemTypeForm.setSubWindow(popUpWindow);

        return holder;
    }

    @Override
    protected Component getBeanComponent(Object bean) {
        itemTypeForm.edit((ItemType) bean);
        itemTypeForm.setSubWindow(popUpWindow);
        return holder;
    }

    public void setCompany(Company company) {
        this.company = company;
        reload();
    }
}
