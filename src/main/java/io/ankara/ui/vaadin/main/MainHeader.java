package io.ankara.ui.vaadin.main;

import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/16 10:52 PM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainHeader extends CustomComponent {

    @Inject
    private SetupMenu setupMenu;

    @Inject
    private MainMenu mainMenu;

    @PostConstruct
    private void build(){

        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");
        setCompositionRoot(content);

        Image logo = new Image(null,new ThemeResource("img/logo.png"));
        logo.setWidth("64px");
        logo.setHeight("64px");

        content.addComponent(logo);
        content.setComponentAlignment(logo, Alignment.BOTTOM_LEFT);

        content.addComponent(mainMenu);
        content.setComponentAlignment(mainMenu,Alignment.BOTTOM_LEFT);
        content.setExpandRatio(mainMenu,1);

        content.addComponent(setupMenu);
        content.setComponentAlignment(setupMenu,Alignment.BOTTOM_RIGHT);
    }
}
