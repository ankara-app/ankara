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
public abstract class RemoveItemButton extends Button  {

    private Object itemID;

    public RemoveItemButton( Object itemID) {
        super("Remove", FontAwesome.REMOVE);
        this.itemID = itemID;
        addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        addStyleName(ValoTheme.BUTTON_BORDERLESS);
        addStyleName(ValoTheme.BUTTON_TINY);
        addClickListener((ClickListener) event -> {
            if (itemID != null) {

                ConfirmDialog.show(getUI(),"Please confirm ...","Should this item be removed?","Yes","Cancel", new ConfirmDialog.Listener() {
                    @Override
                    public void onClose(ConfirmDialog confirmDialog) {
                        if(confirmDialog.isConfirmed())
                            removeItem(itemID);
                    }
                });

            }
        });
    }

    public Object getItemID() {
        return itemID;
    }

    public void setItemID(Object itemID) {
        this.itemID = itemID;
    }

    public  abstract  void removeItem(Object itemID);
}
