package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.*;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import io.ankara.Topics;
import io.ankara.domain.Cost;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.domain.Tax;
import io.ankara.service.ItemTypeService;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.util.AppliedTaxesConverter;
import io.ankara.ui.vaadin.util.RemoveItemButton;
import io.ankara.utils.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.vaadin.spring.events.EventBus;
import tm.kod.widgets.numberfield.NumberField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 3:02 PM
 */


@SpringComponent
@ViewScope
public class ItemsTable extends Grid<Integer> {

    private static final float ROW_HEIGHT = 80;
    private static final float HEADER_ROW_HEIGHT = 50;
    public static final int MAXIMUM_VISIBLE_ROWS = 10;

    @Inject
    private ItemTypeService itemTypeService;

    @Inject
    private TaxService taxService;

    @Inject
    private EventBus.UIEventBus eventBus;

    private Cost cost;

    private Map<Integer, BeanValidationBinder<Item>> itemBinders = new HashMap<>();
    private Map<Integer, Label> itemAmountLabels = new HashMap<>();

    private Integer recentItemID = 0;

    private boolean ignoreSummaryCalculateRequest = false;

    @PostConstruct
    private void build() {
//        setWidth("100%");
        setSizeFull();
        addStyleName(AnkaraTheme.ITEMS_GRID);
        addStyleName(AnkaraTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(AnkaraTheme.TABLE_SMALL);
        addStyleName(AnkaraTheme.TABLE_BORDERLESS);
        setSelectionMode(SelectionMode.NONE);

        addComponentColumn(key -> {
            ComboBox<ItemType> typeBox = new ComboBox<>();
            typeBox.setWidth("100%");
            typeBox.setItems(itemTypeService.getItemTypes(cost.getCompany()));

            itemBinders.get(key).forField(typeBox).bind("type");
            typeBox.addValueChangeListener(event -> calculateSummaries(key));

            return typeBox;
        }).setCaption("Type").setId("type").setWidth(200).setSortable(false);


        addComponentColumn(key -> {
            TextArea descriptionArea = new TextArea();
            descriptionArea.setRows(3);
            descriptionArea.setWidth("100%");
            itemBinders.get(key).forField(descriptionArea).bind("description");
            descriptionArea.addValueChangeListener(event -> calculateSummaries(key));

            return descriptionArea;

        }).setCaption("Description").setId("description").setMinimumWidth(200).setExpandRatio(1).setSortable(false);

        addComponentColumn(key -> {
            NumberField quantityField = new NumberField();
            quantityField.setDecimalSeparator(',');
            quantityField.setSigned(false);
            quantityField.setDecimalSeparator('.');
            quantityField.setWidth("100%");
            quantityField.addStyleName(AnkaraTheme.TEXTFIELD_TINY);

            itemBinders.get(key)
                    .forField(quantityField)
                    .withConverter(new StringToBigDecimalConverter("Quantity must be a number"))
                    .bind("quantity");
            quantityField.addValueChangeListener(event -> calculateSummaries(key));

            return quantityField;

        }).setCaption("Quantity").setId("quantity").setWidth(100).setSortable(false);

        addComponentColumn(key -> {
            NumberField priceField = new NumberField();
            priceField.setWidth("100%");

            itemBinders.get(key)
                    .forField(priceField)
                    .withConverter(new StringToBigDecimalConverter("Price must be a number"))
                    .bind("price");
            priceField.addValueChangeListener(event -> calculateSummaries(key));

            return priceField;

        }).setCaption("Price").setId("price").setWidth(200).setSortable(false);

        addComponentColumn(key -> {
            Item item = itemBinders.get(key).getBean();

            Label amountLabel = new Label();
            amountLabel.addStyleName(AnkaraTheme.TEXT_SMALL);
            //TODO CURRENTLY WE CAN NOT LISTEN FOR THE CHANGES ON THE CURRENCY SELECTOR THERE FOR WE SHOULD NOT SHOW CURRENCY HERE
            // TODO INSTEAD USER WILL REFER TO THE SELECTED CURRENCY UNTIL V2.0
            amountLabel.setValue(NumberUtils.formatMoney(item.getAmount(), ""));
            amountLabel.addStyleName("text-right");

            itemAmountLabels.put(key, amountLabel);
            return amountLabel;

        }).setCaption("Amount").setId("amount").setWidth(200).setSortable(false);

        addComponentColumn(key -> {

            CheckBoxGroup<Tax> taxSelector = new CheckBoxGroup();
            taxSelector.setItems(taxService.getTaxes(cost.getCompany()));
            taxSelector.addStyleName(AnkaraTheme.OPTIONGROUP_SMALL);
            taxSelector.addStyleName(AnkaraTheme.TEXT_SMALL);
//            taxSelector.setWidth("100px");

            itemBinders.get(key).forField(taxSelector).withConverter(new AppliedTaxesConverter()).bind("taxes");
            taxSelector.addValueChangeListener(event -> requestSummaryCalculation(key));
            return taxSelector;

        }).setCaption("Taxes").setId("taxes").setWidth(100).setSortable(false);


        addComponentColumn(key -> {
            RemoveItemButton removeItemButton = new RemoveItemButton(key) {
                @Override
                public void removeItem(Object itemID) {
                    removeCostItem((Integer) itemID);
                }
            };
            return removeItemButton;
        }).setCaption("").setId("remove").setWidth(70).setSortable(false);


        setDataProvider(
                (sortOrder, offset, limit) -> itemBinders.keySet().stream().skip(offset).limit(limit),
                () -> itemBinders.size()
        );

    }


    private void calculateSummaries(Integer key) {
        Item item = itemBinders.get(key).getBean();
        Label amountLabel = itemAmountLabels.get(key);
        amountLabel.setValue(NumberUtils.formatMoney(item.getAmount(), ""));
        requestSummaryCalculation(key);
    }

    private void requestSummaryCalculation(Object changedRowIndex) {
        if (!ignoreSummaryCalculateRequest)
            eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES, this, changedRowIndex);
    }


    public void removeCostItem(Integer key) {
        itemBinders.remove(key);

        //shrink the table according to the items it contains up to when it reaches 2
        if (itemBinders.size() >= 2)
            setHeight(ROW_HEIGHT * itemBinders.size() + HEADER_ROW_HEIGHT, Unit.PIXELS);

        requestSummaryCalculation(key);
        getDataProvider().refreshAll();
    }

    public void addCostItem(Item item) {
        //increment the recent item id
        recentItemID++;

        if (item.getCost().getCompany() == null) {
            Notification.show("Specify company for the cost first", Notification.Type.WARNING_MESSAGE);
            return;
        }

        BeanValidationBinder<Item> binder = new BeanValidationBinder<>(Item.class);
        binder.setRequiredConfigurator(null);

        //during validation if some fields have errrors then ignore them and do not show on the UI with red colors
        binder.setValidationStatusHandler(status -> {
        });
        binder.setBean(item);

        itemBinders.put(recentItemID, binder);

        setHeight(ROW_HEIGHT * itemBinders.size() + HEADER_ROW_HEIGHT, Unit.PIXELS);
        requestSummaryCalculation(recentItemID);
        getDataProvider().refreshAll();
    }


    /**
     * Reset the state of the table view and its item tracking.
     * Also it initialise empty items on the table ready for editing ONLY IF the cost instance has EMPTY items list
     */
    public void reset() {
        itemBinders.clear();
        recentItemID = 0;
//
//        initialiseCostItems();
    }

    /**
     * Initialise empty items on the table ready for editing ONLY IF the cost instance has EMPTY items list
     */
    public void initialiseCostItems(int limit) {
        for (int i = 0; i < limit; i++) {
            addCostItem(new Item(cost));
        }
    }

    public void setCost(Cost cost) {
        this.cost = cost;
        loadCostItems();

        if (cost != null && CollectionUtils.isEmpty(cost.getItems())) {
            initialiseCostItems(4);
        }
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


    public List<Item> getValidItems() {
        LinkedList<Item> items = new LinkedList<>();

        for (Object itemID : itemBinders.keySet().stream().sorted().collect(Collectors.toList())) {
            BeanValidationBinder<Item> binder = itemBinders.get(itemID);
            if (isValid(binder))
                items.add(binder.getBean());
        }

        return items;

    }

    private boolean isValid(BeanValidationBinder<Item> binder) {
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        return !errors.stream().findFirst().isPresent();
    }

    public Map<Integer, BeanValidationBinder<Item>> getItemBinders() {
        return itemBinders;
    }

    public void setIgnoreSummaryCalculateRequest(boolean ignoreSummaryCalculateRequest) {
        this.ignoreSummaryCalculateRequest = ignoreSummaryCalculateRequest;
    }
}
