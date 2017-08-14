package io.ankara.ui.vaadin.main.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import io.ankara.ui.vaadin.AnkaraTheme;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/8/16.
 */
@UIScope
@SpringComponent
public class ViewHeader extends Label {

    public ViewHeader() {
        addStyleName(AnkaraTheme.VIEW_HEADER);
    }
}
