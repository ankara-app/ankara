package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.domain.Cost;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/13/16 12:37 AM
 */
@UIScope
@SpringComponent
public class ItemsView extends VerticalLayout {

    @Inject
    private ItemsTable itemsTable;

    @Inject
    private ItemTypeService itemTypeService;

    private Cost cost;


    @PostConstruct
    private void build() {
        setWidth("100%");

        addComponent(itemsTable);

        Button addItem = new Button(FontAwesome.PLUS_CIRCLE);
        addItem.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addComponent(addItem);
        setComponentAlignment(addItem, Alignment.BOTTOM_RIGHT);

        addItem.addClickListener(event -> {
            Company company = cost.getCompany();

            if (company == null) {
                Notification.show("Select the company first", Notification.Type.WARNING_MESSAGE);
                return;
            }

            List<ItemType> types = itemTypeService.getItemTypes(company);

            if (types.isEmpty()) {
                Notification.show("Selected company cost item types have been not configured", Notification.Type.WARNING_MESSAGE);
                return;
            }

            ItemType defaultType = types.iterator().next();
            itemsTable.addItem(new Item(cost, defaultType));
        });

    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public ItemsTable getItemsTable() {
        return itemsTable;
    }
}
