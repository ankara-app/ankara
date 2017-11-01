package io.ankara.ui.vaadin.util;

import com.vaadin.ui.VerticalLayout;
import io.ankara.ui.vaadin.AnkaraTheme;
import org.springframework.stereotype.Component;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.viritin.layouts.MPanel;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 5/4/17 8:32 PM
 */
@Component
@PrototypeScope
public class TemplatePanel extends MPanel implements Cloneable {

    private TemplateView templateView;

    public TemplatePanel(TemplateView templateView) {
        this.templateView = templateView;
        templateView.setSizeFull();
        setContent(new MVerticalLayout(templateView).withMargin(true));
        addStyleName(AnkaraTheme.PANEL_BORDERLESS);
    }

    public TemplateView getTemplateView() {
        return templateView;
    }
}