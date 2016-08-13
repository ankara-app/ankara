package io.ankara.ui.vaadin.main.view.cost.invoice;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.ui.vaadin.util.SearchField;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 3:05 AM
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InvoiceHeader extends CustomComponent {

    @Inject
    private UI ankaraUI;

    @PostConstruct
    private void build() {
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");

        Button createButton = new Button("Create Invoice", FontAwesome.PLUS_CIRCLE);
        createButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        createButton.addClickListener(event -> {
            ankaraUI.getNavigator().navigateTo(InvoiceFormView.VIEW_NAME);
        });
        content.addComponent(createButton);

        SearchField searchField = new SearchField();
        searchField.setWidth("100%");
        content.addComponent(searchField);
        content.setExpandRatio(searchField, 1);

        setCompositionRoot(content);
    }
}
