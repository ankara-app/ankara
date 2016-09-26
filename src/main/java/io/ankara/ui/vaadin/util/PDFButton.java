package io.ankara.ui.vaadin.util;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import io.ankara.ui.vaadin.main.MainUI;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/26/16.
 */
public class PDFButton extends Button{

    private BrowserWindowOpener opener;

    public PDFButton() {
        opener = new BrowserWindowOpener(MainUI.class);
        opener.extend(this);
        setIcon(FontAwesome.FILE_PDF_O);
        setCaption("PDF");
    }

    public BrowserWindowOpener getOpener() {
        return opener;
    }
}
