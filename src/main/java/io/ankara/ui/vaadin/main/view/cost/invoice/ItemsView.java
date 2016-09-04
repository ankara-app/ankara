package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Cost;
import io.ankara.domain.Item;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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

    private Cost cost;

    @PostConstruct
    private void build() {
        setWidth("100%");

        addComponent(itemsTable);

        Button addItem = new Button("Add Item", FontAwesome.PLUS_CIRCLE);
        addItem.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addComponent(addItem);
        setComponentAlignment(addItem, Alignment.BOTTOM_RIGHT);

        addItem.addClickListener(event -> {
            itemsTable.addCostItem(new Item(cost));
        });

    }

    public void setCost(Cost cost) {
        this.cost = cost;
        itemsTable.setCost(cost);
    }
}
