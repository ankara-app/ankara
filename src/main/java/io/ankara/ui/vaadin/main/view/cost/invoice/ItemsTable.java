package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 3:02 PM
 */
@UIScope
@SpringComponent
public class ItemsTable extends Table {

    private static final int MAX_VISIBLE_ROWS = 4;
    @Inject
    private ItemTypeService itemTypeService;

    private Set<Item> items = new HashSet<>();

    private Map<Item,Label> itemAmountLabel = new HashMap<>();

    @PostConstruct
    private void build() {
        setWidth("100%");
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        setEditable(true);
        setBuffered(false);
        setImmediate(true);
        setTableFieldFactory(new ItemFieldFactory());

        addGeneratedColumn("amount", new ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Label amountLabel = new Label();
                Item item = (Item) itemId;
                amountLabel.setValue(item.getAmount().toString());
                itemAmountLabel.put(item,amountLabel);
                return amountLabel;
            }
        });
        reset();
    }

    public void addItem(Item item) {
        items.add(item);
        load();
    }

    private void load() {
        BeanItemContainer container = new BeanItemContainer<Item>(Item.class);
        container.addAll(items);
        setContainerDataSource(container);

    }

    public void setContainerDataSource(BeanItemContainer newDataSource) {
        super.setContainerDataSource(newDataSource);
        setVisibleColumns("type", "description", "quantity", "price", "amount", "taxable");
    }

    public void reset() {
        items.clear();
        load();
        setPageLength(0);
    }

    private class ItemFieldFactory extends DefaultFieldFactory {

        @Override
        public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
            String propertyName = (String) propertyId;
            Item item = (Item) itemId;

            if (propertyName.equals("type")) {
                ComboBox selector = new ComboBox();
                selector.setContainerDataSource(new BeanItemContainer<ItemType>(ItemType.class, itemTypeService.getItemTypes(item.getCost().getCompany())));
                selector.setWidth("100%");
                selector.setRequired(true);
                return selector;
            } else if (propertyName.equals("description")) {
                TextArea description = new TextArea();
                description.setRows(3);
                description.setNullRepresentation("");
                description.setWidth("100%");
                description.setRequired(true);
                return description;
            } else if (propertyName.equals("quantity")) {
                TextField quantity = new TextField();
                quantity.setWidth("100%");
                quantity.setRequired(true);

                quantity.addTextChangeListener(event -> {
                    int qty = Integer.valueOf(event.getText());
                    BigDecimal price = item.getPrice();
                    Label amountLabel = itemAmountLabel.get(itemId);
                   amountLabel.setValue(price.multiply(new BigDecimal(qty)).toString());
                });
                return quantity;
            } else if (propertyName.equals("price")) {
                TextField price = new TextField();
                price.setWidth("100%");
                price.addTextChangeListener(event -> {
                    int qty = item.getQuantity();
                    BigDecimal prc = new BigDecimal(event.getText());
                    Label amountLabel = itemAmountLabel.get(itemId);
                    amountLabel.setValue(prc.multiply(new BigDecimal(qty)).toString());
                });
                price.setRequired(true);
                return price;
            } else if (propertyName.equals("taxable")) {
                CheckBox taxable = new CheckBox();
                taxable.setRequired(true);
                return taxable;
            } else return null;
        }
    }
}
