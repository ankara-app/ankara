package io.ankara.ui.vaadin.util;

import com.vaadin.data.Container;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.dialogs.ConfirmDialog;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 4/10/16
 */
public abstract class RemoveItemButton extends Button implements Button.ClickListener {

    private Object itemID;
    private Container container;

    public RemoveItemButton(Container container, Object itemID) {
        super("Remove", FontAwesome.REMOVE);
        this.itemID = itemID;
        this.container = container;
        addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        addStyleName(ValoTheme.BUTTON_BORDERLESS);
        addStyleName(ValoTheme.BUTTON_TINY);
        addClickListener(this);
    }

    public Object getItemID() {
        return itemID;
    }

    public void setItemID(Object itemID) {
        this.itemID = itemID;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (itemID != null && container != null) {

            ConfirmDialog.show(getUI(),"Please confirm ...","Should this item be removed?","Yes","Cancel", new ConfirmDialog.Listener() {
                @Override
                public void onClose(ConfirmDialog confirmDialog) {
                    if(confirmDialog.isConfirmed())
                        removeItem(itemID);
                }
            });


        }
    }

    public  abstract  void removeItem(Object itemID);
}
