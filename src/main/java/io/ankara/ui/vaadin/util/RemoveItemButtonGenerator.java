package io.ankara.ui.vaadin.util;

import com.vaadin.ui.Table;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 4/10/16
 */
public abstract class RemoveItemButtonGenerator implements Table.ColumnGenerator {

    public RemoveItemButtonGenerator(Table table,String columnName) {
        table.addGeneratedColumn(columnName, this);
        table.setColumnWidth(columnName,100);
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        return new RemoveItemButton(itemId){
            @Override
            public void removeItem(Object itemID) {
                RemoveItemButtonGenerator.this.removeItem(itemID);
            }
        };
    }

    protected abstract void removeItem(Object itemID);
}
