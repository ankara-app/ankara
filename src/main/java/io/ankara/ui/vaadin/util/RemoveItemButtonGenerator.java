package io.ankara.ui.vaadin.util;

import com.vaadin.ui.Table;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 4/10/16
 */
public abstract class RemoveItemButtonGenerator implements Table.ColumnGenerator {

    private boolean confirm;
    private String confirmationMessage;

    public RemoveItemButtonGenerator(Table table,String columnName,boolean confirm) {
        this.confirm = confirm;

        table.addGeneratedColumn(columnName, this);
        table.setColumnWidth(columnName,100);
    }

    public RemoveItemButtonGenerator(Table table, String columnName) {
        this(table,columnName,false);
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        RemoveItemButton removeItemButton =  new RemoveItemButton(itemId,confirm){
            @Override
            public void removeItem(Object itemID) {
                RemoveItemButtonGenerator.this.removeItem(itemID);
            }
        };

        if(confirm && confirmationMessage!=null)
            removeItemButton.setConfirmationMessage(confirmationMessage);

        return removeItemButton;
    }

    protected abstract void removeItem(Object itemID);
}
