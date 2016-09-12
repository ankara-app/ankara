package io.ankara.ui.vaadin.util;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 5:24 PM
 */
public abstract class BeanCRUDComponent extends VerticalLayout {

    protected Table table;
    protected BeanItemContainer container;
    protected Window popUpWindow;
    protected Button createButton;
    protected HorizontalLayout header;


    protected void build(Class type) {

        popUpWindow = new Window();
        popUpWindow.setModal(true);
        popUpWindow.center();
        popUpWindow.setDraggable(true);
        popUpWindow.setWidth("70%");
        popUpWindow.setHeight("90%");
        popUpWindow.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent e) {
                reload();
            }
        });

        createButton = new Button("New", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        createButton.addClickListener(event -> {
            create();
        });
        header = new HorizontalLayout(createButton);

        addComponent(header);
        setComponentAlignment(header, Alignment.TOP_RIGHT);

        table = new Table();
        table.setSizeFull();;
        container = new BeanItemContainer(type,loadBeans());
        table.setContainerDataSource(container);
        table.addItemClickListener(event -> {
            Object bean = event.getItemId();
            popUpWindow.setContent(getBeanComponent(bean));
            UI.getCurrent().addWindow(popUpWindow);
        });

        addComponent(table);
        setExpandRatio(table,1);

        new RemoveItemButtonGenerator(table,"Remove",true){
            @Override
            protected void removeItem(Object itemID) {
                BeanCRUDComponent.this.removeItem(itemID);
                reload();
            }
        };

    }

    public void create() {
        popUpWindow.setContent(getCreateComponent());
        UI.getCurrent().addWindow(popUpWindow);
    }


    protected abstract void removeItem(Object itemID);

    public void reload() {
        container.removeAllItems();
        container.addAll(loadBeans());
    }


    protected abstract Collection loadBeans();

    protected abstract Component getCreateComponent();

    public Table getTable() {
        return table;
    }

    protected abstract Component getBeanComponent(Object bean);


    public BeanItemContainer getContainer() {
        return container;
    }

    public Window getPopUpWindow() {
        return popUpWindow;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public HorizontalLayout getHeader() {
        return header;
    }
}
