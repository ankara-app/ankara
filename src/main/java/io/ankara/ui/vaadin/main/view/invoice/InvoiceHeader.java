package io.ankara.ui.vaadin.main.view.invoice;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import io.ankara.ui.vaadin.util.SearchField;

import javax.annotation.PostConstruct;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:05 AM
 */
@SpringComponent
public class InvoiceHeader extends CustomComponent{


    @PostConstruct
    private void build(){
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");
        SearchField searchField = new SearchField();
        searchField.setWidth("100%");
        content.addComponent(searchField);
        content.setExpandRatio(searchField,1);
        Button createButton = new Button("Create Invoice", FontAwesome.PLUS_CIRCLE);
        content.addComponent(createButton);

        setCompositionRoot(content);
    }
}
