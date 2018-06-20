package io.ankara.ui.vaadin.main.view.cost;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.util.TemplateView;
import io.ankara.utils.DateUtils;
import io.ankara.utils.NumberUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import javax.inject.Inject;
import java.util.Locale;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 7:35 PM
 */
public abstract class CostsTable<T extends Cost> extends Grid<T> {


    private String template;

    @Inject
    private TemplateEngine templateEngine;

    public CostsTable(String template) {
        this.template = template;
    }

    protected void build() {
        setSizeFull();
        setHeaderVisible(false);
        setBodyRowHeight(90);

        addColumn(cost->
                templateEngine.process(
                        template,
                        new Context(Locale.getDefault(), ImmutableMap.of("cost", cost))),
                new HtmlRenderer()
        );
        setDataProvider(getCostProvider());

        setSelectionMode(SelectionMode.NONE);
        addItemClickListener(event -> showCostView(event.getItem()));
    }

    protected abstract void showCostView(T cost);

    protected abstract CostsProvider<T> getCostProvider();

    public void reload(){
        getDataProvider().refreshAll();
    }

}
