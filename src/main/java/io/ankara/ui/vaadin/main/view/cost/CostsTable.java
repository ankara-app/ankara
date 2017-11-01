package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.ui.Grid;
import io.ankara.domain.Cost;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.Templates;
import io.ankara.ui.vaadin.util.TemplateView;
import io.ankara.utils.DateUtils;
import io.ankara.utils.NumberUtils;
import org.thymeleaf.TemplateEngine;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import javax.inject.Inject;

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

        addComponentColumn(cost -> {
            MHorizontalLayout costLayout = new MHorizontalLayout(
            new TemplateView(templateEngine)
                    .setTemplatePath(template)
                    .putBinding("cost",cost)
                    .render().withFullWidth()
            ).withFullWidth();
            costLayout.addLayoutClickListener(event -> showCostView(cost));

            return costLayout;
        });

        setDataProvider(getCostProvider());
    }

    protected abstract void showCostView(T cost);

    protected abstract CostsProvider<T> getCostProvider();

    public void reload(){
        getDataProvider().refreshAll();
    }

}
