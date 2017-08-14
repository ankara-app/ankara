package io.ankara.ui.vaadin.util;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/14/16 5:24 PM
 */
public abstract class BeanCRUDComponent<T> extends VerticalLayout {

    protected Grid<T> table;
    protected Window popUpWindow;
    protected Button createButton;
    protected HorizontalLayout header;

    public BeanCRUDComponent() {
        table = new Grid<>();
    }

    protected void build() {

        popUpWindow = new Window();
        popUpWindow.setModal(true);
        popUpWindow.center();
        popUpWindow.setDraggable(true);
        popUpWindow.setWidth("70%");
        popUpWindow.setHeight("90%");
        popUpWindow.addCloseListener(e -> reload());

        createButton = new Button("New", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        createButton.addClickListener(event -> {
            create();
        });
        header = new HorizontalLayout(createButton);

        addComponent(header);
        setComponentAlignment(header, Alignment.TOP_RIGHT);

        table.setSizeFull();

        table.addItemClickListener(event -> {
            Object bean = event.getItem();
            popUpWindow.setContent(getBeanComponent(bean));

            if (!popUpWindow.isAttached())
                UI.getCurrent().addWindow(popUpWindow);
        });

        addComponent(table);
        setExpandRatio(table, 1);

        table.addComponentColumn((ValueProvider<T, Component>) t -> {
            RemoveItemButton removeItemButton = new RemoveItemButton(t) {
                @Override
                public void removeItem(Object itemID) {
                    BeanCRUDComponent.this.removeItem(t);
                    reload();
                }
            };

            return removeItemButton;
        }).setCaption("");

        reload();
    }

    public void create() {
        popUpWindow.setContent(getCreateComponent());
        UI.getCurrent().addWindow(popUpWindow);
    }


    protected abstract void removeItem(Object itemID);

    public void reload() {
        table.getDataProvider().refreshAll();
    }

    protected abstract Component getCreateComponent();

    public Grid<T> getTable() {
        return table;
    }

    protected abstract Component getBeanComponent(Object bean);

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
