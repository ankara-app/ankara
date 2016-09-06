package io.ankara.config;

import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;
import io.ankara.AnkaraTemplateEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/24/16 1:48 AM
 */
@Configuration
public class TemplatesConfiguration {

    @Bean
    public MarkupTemplateEngine markupTemplateEngine() {
        TemplateConfiguration config = new TemplateConfiguration();
        config.setAutoEscape(true);
        config.setAutoIndent(true);
        MarkupTemplateEngine templateEngine = new MarkupTemplateEngine(config);

        return templateEngine;
    }

    @Bean
    public AnkaraTemplateEngine ankaraTemplateEngine(){
        return new AnkaraTemplateEngine();
    }
}
