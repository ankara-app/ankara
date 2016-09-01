package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.Topics;
import io.ankara.domain.*;
import io.ankara.service.ItemTypeService;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.util.AppliedTaxesConverter;
import org.springframework.util.CollectionUtils;
import org.vaadin.spring.events.EventBus;

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

    @Inject
    private ItemTypeService itemTypeService;

    @Inject
    private TaxService taxService;

    @Inject
    private EventBus.UIEventBus eventBus;

    private Cost cost;

    private Map<Integer, BeanFieldGroup<Item>> itemsFieldGroup = new IdentityHashMap<>();

    @PostConstruct
    private void build() {
        setWidth("100%");
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setEditable(true);
        setFooterVisible(true);
        setColumnFooter("price","Subtotal");
        reset();

        addContainerProperty("type", ComboBox.class, null);
        addContainerProperty("description", TextArea.class, null);
        addContainerProperty("quantity", TextField.class, null);
        addContainerProperty("price", TextField.class, null);
        addContainerProperty("amount", Label.class, null);
        addContainerProperty("appliedTaxes", OptionGroup.class, null);

        addGeneratedColumn("", new ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Button removeButton =  new Button(FontAwesome.REMOVE);
                removeButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
                removeButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        ItemsTable.this.removeItem(itemId);
                    }
                });
                return removeButton;
            }
        });

    }

    @Override
    public boolean removeItem(Object itemId) {
        if(super.removeItem(itemId)){
            itemsFieldGroup.remove(itemId);
            eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES, this, itemId);
            return true;
        }else return false;
    }

    public void addCostItem() {
        Company company = cost.getCompany();
        if (company == null) {
            Notification.show("Select the company first", Notification.Type.WARNING_MESSAGE);
            return;
        }

        addItem(getRow(getRowIndex()), getRowIndex());
    }

    private int getRowIndex() {
        return itemsFieldGroup.size();
    }

    private Object[] getRow(int rowIndex) {

        Item item = new Item(cost);

        List<ItemType> types = itemTypeService.getItemTypes(cost.getCompany());
        if (!types.isEmpty()) {
            ItemType defaultType = types.iterator().next();
            item.setType(defaultType);
        }else
            Notification.show("Selected company cost item types have been not configured", Notification.Type.WARNING_MESSAGE);

        BeanFieldGroup<Item> fieldGroup = new BeanFieldGroup<>(Item.class);
        fieldGroup.setBuffered(false);
        fieldGroup.setFieldFactory(new ItemFieldFactory());
        fieldGroup.setItemDataSource(item);

        itemsFieldGroup.put(rowIndex,fieldGroup);

        ComboBox selector = (ComboBox) fieldGroup.buildAndBind("type");
        TextArea description = (TextArea) fieldGroup.buildAndBind("description");

        TextField quantity = (TextField) fieldGroup.buildAndBind("quantity");
        TextField price = (TextField) fieldGroup.buildAndBind("price");

        OptionGroup appliedTaxes = (OptionGroup) fieldGroup.buildAndBind("appliedTaxes");
        appliedTaxes.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES, this, rowIndex);
            }
        });

        Label amountLabel = new Label();

        quantity.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                calculateAmount(amountLabel, quantity, price);
                eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES,this,rowIndex);
            }
        });

        price.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                calculateAmount(amountLabel,quantity,price);
                eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES, this, rowIndex);
            }
        });

        return new Object[]{selector, description, quantity, price, amountLabel, appliedTaxes};
    }

    private void calculateAmount(Label amountLabel, TextField quantity, TextField price) {
        try{
            Integer qty = (Integer) quantity.getConvertedValue();
            BigDecimal prc = (BigDecimal) price.getConvertedValue();
            amountLabel.setValue(prc.multiply(BigDecimal.valueOf(qty)).toString());
        }catch (Converter.ConversionException | NumberFormatException e){
            Notification.show("Enter the item details correctly", Notification.Type.WARNING_MESSAGE);
        }

    }


    public void reset() {
        removeAllItems();
        itemsFieldGroup.clear();
        setPageLength(0);

        eventBus.publish(Topics.TOPIC_COST_CALCULATE_SUMMARIES, this, getRowIndex());
    }

    public void setCost(Cost cost) {
        this.cost = cost;
        reset();
        
        //if the cost has no items initialise 4 items to simplify user editing
        if(CollectionUtils.isEmpty(cost.getItems())){
            //add 4 initial cost items
            addCostItem();
            addCostItem();
            addCostItem();
            addCostItem();
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
            } else if(Collection.class.isAssignableFrom(dataType)){
                OptionGroup taxSelector = new OptionGroup();
                taxSelector.addItems(taxService.getTaxes(cost.getCompany()));
                taxSelector.setMultiSelect(true);
                taxSelector.setConverter(new AppliedTaxesConverter());

                return taxSelector;
            }else
                return createField(dataType, fieldType);
        }

    }

    public boolean ensureItemsValidity(){
        for(BeanFieldGroup fieldGroup:itemsFieldGroup.values()){
            if(!fieldGroup.isValid()){
                return false;
            }
        }

        return true;
    }

    public List<Item> getItems(){
        LinkedList items = new LinkedList();
        //iterating with the row index to determine the order they were added and reserve it when saving to the database

        for (int i = 0; i < itemsFieldGroup.size(); i++) {
            items.add(itemsFieldGroup.get(i).getItemDataSource().getBean());
        }

        return items;
    }
}
