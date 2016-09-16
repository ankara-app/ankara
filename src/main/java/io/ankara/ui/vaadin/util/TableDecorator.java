package io.ankara.ui.vaadin.util;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/16/16.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableDecorator implements Table.ColumnGenerator {

    @Inject
    private TemplateEngine templateEngine;

    private Table table;
    private String template;
    private String column;

    public void decorate(Table table, String template, String column) {
        if (this.table != null && this.column!=null)
            this.table.removeGeneratedColumn(this.column);

        this.table = table;
        this.template = template;
        this.column = column;

        table.addGeneratedColumn(column, this);
        table.setVisibleColumns(column);
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        Context context = new Context();
        context.setVariable("cost", itemId);
        return new Label(templateEngine.process(template, context), ContentMode.HTML);
    }
}
