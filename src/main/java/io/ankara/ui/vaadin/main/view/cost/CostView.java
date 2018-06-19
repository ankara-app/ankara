package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.util.OpenerButton;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.vaadin.spring.events.EventBus;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/23/16 2:17 PM
 */
public abstract class CostView<T extends Cost> extends CustomComponent implements View {

    private String template;

    private Panel contentPanel;
    private HorizontalLayout header;
    private Label templateLabel;
    private Button edit,delete,copy;
    private OpenerButton pdf,print;

    @Inject
    private TemplateEngine templateEngine;

    @Inject
    protected EventBus.UIEventBus eventBus;

    protected T cost;

    public CostView(String template) {
        this.template = template;
    }

    @PostConstruct
    private void build() {
        setSizeFull();

        edit = new Button("Edit", FontAwesome.PENCIL);
        edit.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);
        edit.addClickListener((Button.ClickListener) event -> edit());

        copy = new Button("Copy", FontAwesome.COPY);
        copy.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);
        copy.addClickListener((Button.ClickListener) event -> copy());

        delete = new Button("Delete", FontAwesome.REMOVE);
        delete.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);
        delete.addClickListener((Button.ClickListener) event -> delete());

        print = new OpenerButton("Print",FontAwesome.PRINT);
        print.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);

        pdf = new OpenerButton("PDF",FontAwesome.FILE_PDF_O);
        pdf.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);

        header = new HorizontalLayout(edit,copy, delete, print,pdf);

        VerticalLayout content = new VerticalLayout();
        content.addStyleName(AnkaraTheme.LAYOUT_CARD);
        templateLabel = new Label("", ContentMode.HTML);
        templateLabel.setWidth("100%");
        content.addComponent(templateLabel);
        content.setWidth("100%");

        contentPanel = new Panel(content);
        contentPanel.addStyleName(AnkaraTheme.PANEL_BORDERLESS);
        contentPanel.setSizeFull();
        VerticalLayout root = new VerticalLayout(header, contentPanel);
        root.setExpandRatio(contentPanel,1);
        root.setSizeFull();
        root.setMargin(false);

        setCompositionRoot(root);
    }

    protected abstract void delete();

    protected abstract void edit();

    protected abstract void copy();

    public T getCost() {
        return cost;
    }

    public void setCost(T cost) {
        this.cost = cost;

        pdf.getOpener().setUrl(getPdfURL(cost));
        print.getOpener().setUrl(getPrintURL(cost));

        Context context = new Context();

        context.setVariable("cost", cost);

        try {
            templateLabel.setValue(templateEngine.process(template, context));
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Failed to render content", "Template " + template + " failed to be loaded.", Notification.Type.ERROR_MESSAGE);
            templateLabel.setValue("Failed to render content");
        }
    }

    protected abstract String getPrintURL(T cost);

    protected abstract String getPdfURL(T cost);

    @PostConstruct
    protected void init() {
        eventBus.subscribe(this);
    }

    @PreDestroy
    protected void destroy() {
        eventBus.unsubscribe(this);
    }

}
