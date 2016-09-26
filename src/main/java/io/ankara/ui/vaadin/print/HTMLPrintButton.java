package io.ankara.ui.vaadin.print;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/5/16 7:05 PM
 */
public class HTMLPrintButton extends Button {

    public HTMLPrintButton() {
        super("Print", FontAwesome.PRINT);
        decorate();
    }

    private void decorate() {
        BrowserWindowOpener opener =
                new BrowserWindowOpener("/print");

        opener.extend(this);
    }
}
