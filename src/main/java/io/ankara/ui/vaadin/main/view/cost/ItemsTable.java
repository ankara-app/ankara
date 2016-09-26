package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.Topics;
import io.ankara.domain.Cost;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.util.AppliedTaxesConverter;
import io.ankara.utils.NumberUtils;
import io.ankara.ui.vaadin.util.RemoveItemButtonGenerator;
import org.springframework.util.CollectionUtils;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 3:02 PM
 */


@UIScope
@SpringComponent
public class ItemsTable extends Table {

    @Inject
    private ItemTypeService itemTypeService;

    @Inject
    private TaxService taxService;

    @Inject
    private EventBus.UIEventBus eventBus;

    private Cost cost;

    private Map<Integer, BeanFieldGroup<Item>> itemsFieldGroup = new HashMap();

    private Integer recentItemID = 0;

    private boolean ignoreSummaryCalculateRequest = false;

    @PostConstruct
    private void build() {
        setWidth("100%");
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setEditable(true);

        addContainerProperty("type", ComboBox.class, null);
        addContainerProperty("description", TextArea.class, null);
        addContainerProperty("quantity", TextField.class, null);
        addContainerProperty("price", TextField.class, null);
        addContainerProperty("amount", Label.class, null);
        addContainerProperty("taxes", OptionGroup.class, null);

        new RemoveItemButtonGenerator(this, "") {
            @Override
            protected void removeItem(Object itemID) {
                ItemsTable.this.removeItem(itemID);
                itemsFieldGroup.remove(itemID);
                requestSummaryCalculation(itemID);
            }
        };

        setColumnExpandRatio("description", 3);
        setColumnExpandRatio("amount", 1);
        setColumnExpandRatio("price", 1);
        setColumnWidth("quantity", 100);
        setColumnWidth("taxes", 100);
        setColumnWidth("type", 150);
        setColumnWidth("", 30);
    }

    private void requestSummaryCalculation(Object changedRowIndex) {
        if (!ignoreSummaryCalculateRequest)
            eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES, this, changedRowIndex);
    }

    public void addCostItem(Item item) {
        if (item.getCost().getCompany() == null) {
            Notification.show("Specify company for the cost first", Notification.Type.WARNING_MESSAGE);
            return;
        }
        addItem(getRow(recentItemID, item), recentItemID++);
    }


    private Object[] getRow(int rowIndex, Item item) {

        List<ItemType> types = itemTypeService.getItemTypes(cost.getCompany());
        if (types.isEmpty())
            Notification.show("Selected company cost item types have been not configured", Notification.Type.WARNING_MESSAGE);

        BeanFieldGroup<Item> fieldGroup = new BeanFieldGroup<>(Item.class);
        fieldGroup.setBuffered(false);
        fieldGroup.setFieldFactory(new ItemFieldFactory());
        fieldGroup.setItemDataSource(item);

        itemsFieldGroup.put(rowIndex, fieldGroup);

        ComboBox selector = (ComboBox) fieldGroup.buildAndBind("type");
        TextArea description = (TextArea) fieldGroup.buildAndBind("description");

        TextField quantity = (TextField) fieldGroup.buildAndBind("quantity");
        TextField price = (TextField) fieldGroup.buildAndBind("price");

        OptionGroup appliedTaxes = (OptionGroup) fieldGroup.buildAndBind("taxes");
        appliedTaxes.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
        appliedTaxes.addStyleName(AnkaraTheme.TEXT_SMALL);
        appliedTaxes.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                requestSummaryCalculation(rowIndex);
            }
        });

        Label amountLabel = new Label();
        amountLabel.setValue(NumberUtils.formatMoney(item.getAmount(),cost.getCurrency()));
        amountLabel.addStyleName("text-right");

        quantity.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                amountLabel.setValue(NumberUtils.formatMoney(item.getAmount(),cost.getCurrency()));
                requestSummaryCalculation(rowIndex);
            }
        });

        price.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                amountLabel.setValue(NumberUtils.formatMoney(item.getAmount(),cost.getCurrency()));
                requestSummaryCalculation(rowIndex);
            }
        });

        return new Object[]{selector, description, quantity, price, amountLabel, appliedTaxes};
    }

    /**
     * Reset the state of the table view and its item tracking.
     * Also it initialise empty items on the table ready for editing ONLY IF the cost instance has EMPTY items list
     */
    public void reset() {
        removeAllItems();
        itemsFieldGroup.clear();
        setPageLength(0);
        recentItemID = 0;

        initialiseCostItems();
    }

    /**
     * Initialise empty items on the table ready for editing ONLY IF the cost instance has EMPTY items list
     */
    public void initialiseCostItems() {
        //if the cost has no items initialise 4 items to simplify user editing
        if (cost != null && CollectionUtils.isEmpty(cost.getItems())) {
            //add 4 initial cost items
            addCostItem(new Item(cost));
            addCostItem(new Item(cost));
            addCostItem(new Item(cost));
            addCostItem(new Item(cost));
        }
    }

    public void setCost(Cost cost) {
        this.cost = cost;
        loadCostItems();
    }

    /**
     * Load the items in the cost instance ready to be edited on the table
     */
    private void loadCostItems() {
        reset();

        for (Item item : cost.getItems()) {
            addCostItem(item);
        }
    }

    public Cost getCost() {
        return cost;
    }

    private class ItemFieldFactory implements FieldGroupFieldFactory {

        @Override
        public Field createField(Class dataType, Class fieldType) {

            if (ItemType.class.isAssignableFrom(dataType)) {
                ComboBox selector = new ComboBox();
                selector.setNullSelectionAllowed(false);
                selector.setContainerDataSource(new BeanItemContainer<ItemType>(ItemType.class, itemTypeService.getItemTypes(cost.getCompany())));
                selector.setWidth("100%");
                selector.setRequired(true);
                return selector;
            } else if (String.class.isAssignableFrom(dataType)) {
                TextArea description = new TextArea();
                description.setRows(3);
                description.setNullRepresentation("");
                description.setWidth("100%");
                description.setRequired(true);
                return description;
            } else if (Integer.class.isAssignableFrom(dataType) || BigDecimal.class.isAssignableFrom(dataType)) {
                TextField quantity = new TextField();
                quantity.setWidth("100%");
                quantity.setRequired(true);
                return quantity;
            } else if (Boolean.class.isAssignableFrom(dataType)) {
                CheckBox taxable = new CheckBox();
                taxable.setImmediate(true);
                return taxable;
            } else if (Collection.class.isAssignableFrom(dataType)) {
                OptionGroup taxSelector = new OptionGroup();
                taxSelector.addItems(taxService.getTaxes(cost.getCompany()));
                taxSelector.setMultiSelect(true);
                taxSelector.setConverter(new AppliedTaxesConverter());

                return taxSelector;
            } else
                return createField(dataType, fieldType);
        }

    }

    public boolean ensureItemsValidity() {
        for (BeanFieldGroup fieldGroup : itemsFieldGroup.values()) {
            if (!fieldGroup.isValid()) {
                return false;
            }
        }

        return true;
    }

    public List<Item> getItems() {
        LinkedList<Item> items = new LinkedList<>();
        //iterating with the row index to determine the order they were added and reserve it when saving to the database
        List itemIDs = getItemIds().stream().sorted((Comparator<Object>) (itemID1, itemID2) -> ((Integer) itemID1).compareTo((Integer) itemID2)).collect(Collectors.toList());
        for (Object itemID : itemIDs) {
            items.add(itemsFieldGroup.get(itemID).getItemDataSource().getBean());
        }

        return items;

    }

    public Map<Integer, BeanFieldGroup<Item>> getItemsFieldGroup() {
        return itemsFieldGroup;
    }

    public void setIgnoreSummaryCalculateRequest(boolean ignoreSummaryCalculateRequest) {
        this.ignoreSummaryCalculateRequest = ignoreSummaryCalculateRequest;
    }
}
