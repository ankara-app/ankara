package io.ankara.ui.vaadin.util;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
public class ViewLink extends Link {


    public ViewLink(String caption, String viewName) {
        super(caption,new ExternalResource("#!"+viewName));
    }
}
