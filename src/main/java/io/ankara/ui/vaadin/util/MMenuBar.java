package io.ankara.ui.vaadin.util;

import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;

import java.util.Optional;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 10/1/17 7:43 PM
 */
public class MMenuBar extends MenuBar{

    public MenuItem addItemAsFirst(String caption, Resource icon, Command command) {
        Optional<MenuItem> firstMenuItem = getItems().stream().findFirst();
        if (firstMenuItem.isPresent())
            return addItemBefore(caption, icon, command, firstMenuItem.get());
        else return addItem(caption, icon, command);
    }
}
