package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import groovy.text.markup.MarkupTemplateEngine;
import io.ankara.domain.Cost;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/23/16 2:17 PM
 */
@SpringComponent
public abstract class CostView extends CustomComponent {

    private String template;

    private VerticalLayout content;
    private HorizontalLayout header;
    private Label templateLabel;

    @Inject
    private MarkupTemplateEngine templateEngine;

    @Inject
    protected EventBus.UIEventBus eventBus;

    public CostView(String template) {
        this.template = template;
    }

    @PostConstruct
    private void build() {
        Button edit = new Button("Edit", FontAwesome.PENCIL);
        edit.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        Button print = new Button("Print", FontAwesome.PRINT);
        print.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        header = new HorizontalLayout(edit, print);
        content = new VerticalLayout();
        content.setMargin(true);
        content.addStyleName(ValoTheme.LAYOUT_CARD);
        content.setWidth("100%");

        templateLabel = new Label("", ContentMode.HTML);
        content.addComponent(templateLabel);


        VerticalLayout root = new VerticalLayout(header, content);
        root.setWidth("100%");

        setCompositionRoot(root);
    }

    public void setCost(Cost cost) {
        Map bindings = new HashMap<>();
        bindings.put("cost", cost);

        Writer content = new StringWriter();

        try {
            templateEngine.createTemplateByPath("templates/"+template).make(bindings).writeTo(content);
            templateLabel.setValue(content.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Notification.show("Failed to render content", Notification.Type.ERROR_MESSAGE);
            templateLabel.setValue(null);
        }

    }

    @PostConstruct
    protected void init() {
        eventBus.subscribe(this);
    }

    @PreDestroy
    protected void destroy() {
        eventBus.unsubscribe(this);
    }

}
