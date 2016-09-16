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

    public RemoveItemButtonGenerator(Table table,String columnName,boolean confirm) {
        this.confirm = confirm;

        table.addGeneratedColumn(columnName, this);
        table.setColumnWidth(columnName,100);
    }

    public RemoveItemButtonGenerator(Table table, String columnName) {
        this(table,columnName,false);
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        return new RemoveItemButton(itemId,confirm){
            @Override
            public void removeItem(Object itemID) {
                RemoveItemButtonGenerator.this.removeItem(itemID);
            }
        };
    }

    protected abstract void removeItem(Object itemID);
}
