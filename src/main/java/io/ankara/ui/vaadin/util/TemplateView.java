package io.ankara.ui.vaadin.util;

import com.vaadin.shared.ui.ContentMode;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.viritin.label.MLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/14/17 8:44 PM
 */
@Component
@PrototypeScope
public class TemplateView extends MLabel {

    private TemplateEngine templateEngine;
    private String templatePath;
    private Context context = new Context();

    public TemplateView(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        withContentMode(ContentMode.HTML);
    }

    public TemplateView setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    public TemplateView putBinding(String key, Object value) {
        context.setVariable(key, value);
        return this;
    }

    public TemplateView setContext(Context context) {
        this.context = context;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public TemplateView render() {
        setValue(templateEngine.process(templatePath, context));
        return this;
    }

}
