package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.AnkaraTemplateEngine;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.util.HTMLPrintButton;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.IOException;
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
    private AnkaraTemplateEngine templateEngine;

    @Inject
    protected EventBus.UIEventBus eventBus;

    protected Cost cost;

    public CostView(String template) {
        this.template = template;
    }

    @PostConstruct
    private void build() {
        Button edit = new Button("Edit", FontAwesome.PENCIL);
        edit.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        edit.addClickListener((Button.ClickListener) event -> edit(cost));

        Button delete = new Button("Delete", FontAwesome.REMOVE);
        delete.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        delete.addClickListener((Button.ClickListener) event -> delete(cost));

        Button print = new HTMLPrintButton("Print", FontAwesome.PRINT);
        print.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        header = new HorizontalLayout(edit, delete, print);
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

    protected abstract void delete(Cost cost);

    protected abstract void edit(Cost cost);

    public void setCost(Cost cost) {
        this.cost = cost;

        Map bindings = new HashMap<>();
        bindings.put("cost", cost);

        VaadinSession.getCurrent().setAttribute("bindings", bindings);
        VaadinSession.getCurrent().setAttribute("template", template);

        try {
            templateLabel.setValue(templateEngine.generate(template, bindings));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Notification.show("Failed to render content", "Template " + template + " failed to be loaded.", Notification.Type.ERROR_MESSAGE);
            templateLabel.setValue("Failed to render content");
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
