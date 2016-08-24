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
    private SetupMenu setupMenu;

    @Inject
    private MainMenu mainMenu;

    @PostConstruct
    private void build(){
        setSizeFull();

        HorizontalLayout content = new HorizontalLayout();
        content.setSizeFull();
        setCompositionRoot(content);


        content.addComponent(mainMenu);
        content.setExpandRatio(mainMenu,1);

        setupMenu.setWidth(-1,Unit.PIXELS);
        content.addComponent(setupMenu);
        content.setComponentAlignment(setupMenu,Alignment.BOTTOM_RIGHT);
    }
}
