package io.ankara.ui.vaadin.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/16 10:52 PM
 */

@UIScope
@SpringComponent
public class MainHeader extends CustomComponent {

    @Inject
    private MainMenu mainMenu;

    @Inject
    private ViewHeader viewHeader;

    @PostConstruct
    private void build(){
        setWidth("100%");
        setHeight("20px");

        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");
        setCompositionRoot(content);


        content.addComponent(viewHeader);
        content.setComponentAlignment(viewHeader,Alignment.TOP_LEFT);

        content.addComponent(mainMenu);
        content.setComponentAlignment(mainMenu,Alignment.BOTTOM_RIGHT);
    }
}
