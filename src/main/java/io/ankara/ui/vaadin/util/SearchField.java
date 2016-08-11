package io.ankara.ui.vaadin.util;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:07 AM
 */
public class SearchField extends TextField {
    public SearchField() {
        setInputPrompt("Search ...");
        addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        setIcon(FontAwesome.SEARCH);
    }
}
