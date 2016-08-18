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

    protected Grid grid;
    protected BeanItemContainer container;
    protected Window popUpWindow;
    protected Button createButton;
    protected HorizontalLayout header;


    protected void build(Class type) {

        popUpWindow = new Window();
        popUpWindow.setModal(true);
        popUpWindow.center();
        popUpWindow.setDraggable(true);
        popUpWindow.setWidth("60%");
        popUpWindow.setHeight("60%");
        popUpWindow.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent e) {
                reload();
            }
        });

        createButton = new Button("New", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        createButton.addClickListener(event -> {
            popUpWindow.setContent(getCreateComponent());
            UI.getCurrent().addWindow(popUpWindow);
        });
        header = new HorizontalLayout(createButton);

        addComponent(header);
        setComponentAlignment(header, Alignment.TOP_RIGHT);

        grid = new Grid();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        container = new BeanItemContainer(type,loadBeans());
        grid.setContainerDataSource(container);
        grid.addItemClickListener(event -> {
            Object bean = event.getItemId();
            popUpWindow.setContent(getBeanComponent(bean));
            UI.getCurrent().addWindow(popUpWindow);
        });

        addComponent(grid);
        setExpandRatio(grid,1);

    }

    public void reload() {
        container.removeAllItems();
        container.addAll(loadBeans());
    }


    protected abstract Collection loadBeans();

    protected abstract Component getCreateComponent();

    protected abstract Component getBeanComponent(Object bean);

    public Grid getGrid() {
        return grid;
    }

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