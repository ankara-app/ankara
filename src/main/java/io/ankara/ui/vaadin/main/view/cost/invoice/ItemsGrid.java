package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.domain.Item;
import io.ankara.domain.ItemType;
import io.ankara.service.ItemTypeService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/15/16 3:02 PM
 */
@UIScope
@SpringComponent
public class ItemsGrid extends Grid {

    @Inject
    private ItemTypeService itemTypeService;

    private BeanItemContainer<Item> container;

    @PostConstruct
    private void build() {
        container = new BeanItemContainer<Item>(Item.class);
        setContainerDataSource(container);

        setWidth("100%");
        setHeightMode(HeightMode.ROW);
        setHeightByRows(1);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);

        setColumns("type", "description", "quantity", "price");

    }


    public BeanItemContainer<Item> getContainer() {
        return container;
    }

    public void addItem(Item item) {
        setEditorEnabled(false);
        container.addBean(item);
        setContainerDataSource(container);
        setEditorEnabled(true);
        setEditorFieldGroup(new BeanFieldGroup<Item>(Item.class));
    }

    public void reset() {
        container.removeAllItems();
        setContainerDataSource(container);
    }

    public void setCompany(Company company){
        reset();

        List<ItemType> itemTypes = itemTypeService.getItemTypes(company);

        ComboBox selector = new ComboBox(null,new IndexedContainer(itemTypes));
        getColumn("type").setEditorField(selector);
       selector.setConverter(new ItemTypeConverter());
    }

    private class ItemTypeConverter implements Converter{

        @Override
        public ItemType convertToModel(Object value, Class targetType, Locale locale) throws ConversionException {
            return itemTypeService.getItemType((String) value);
        }

        @Override
        public String convertToPresentation(Object value, Class targetType, Locale locale)  throws ConversionException {
            return value.toString();
        }


        @Override
        public Class<ItemType> getModelType() {
            return ItemType.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }
    }
}
