package io.ankara.ui.vaadin.main.view.cost;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import elemental.json.JsonArray;
import groovy.text.markup.MarkupTemplateEngine;
import io.ankara.domain.Cost;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.*;
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

        Button print = new Button("Print", FontAwesome.PRINT);
        print.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        print.addClickListener((Button.ClickListener) event -> {
            try {
                print(cost);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        });

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


    private void print(Cost cost) throws IOException, DocumentException {
        JavaScript.getCurrent().execute(
                "window.print()"
        );
    }

    protected abstract void delete(Cost cost);

    protected abstract void edit(Cost cost);

    public void setCost(Cost cost) {
        this.cost = cost;

        templateLabel.setValue(generateCostHTML());
    }

    private String generateCostHTML() {

        Map bindings = new HashMap<>();
        bindings.put("cost", cost);
        try {
            Writer content = new StringWriter();
            templateEngine.createTemplateByPath("templates/" + template).make(bindings).writeTo(content);

            return content.toString();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Notification.show("Failed to render content", "Template " + template + " failed to be loaded.", Notification.Type.ERROR_MESSAGE);
            return null;
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
