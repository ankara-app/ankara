package io.ankara;

import groovy.text.markup.MarkupTemplateEngine;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/5/16 8:54 PM
 */
public class AnkaraTemplateEngine {

    @Inject
    private MarkupTemplateEngine templateEngine;


    public String generate(String template, Map bindings) throws IOException, ClassNotFoundException {

            Writer content = new StringWriter();
            templateEngine.createTemplateByPath("templates/" + template).make(bindings).writeTo(content);
            return content.toString();
    }
}
