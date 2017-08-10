package io.ankara.ui.vaadin.util;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import io.ankara.ui.vaadin.main.MainUI;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/26/16.
 */
public class OpenerButton extends Button{

    private BrowserWindowOpener opener;

    public OpenerButton(String caption, Resource resource) {
        opener = new BrowserWindowOpener(MainUI.class);
        opener.extend(this);
        setIcon(resource);
        setCaption(caption);
    }

    public BrowserWindowOpener getOpener() {
        return opener;
    }
}
