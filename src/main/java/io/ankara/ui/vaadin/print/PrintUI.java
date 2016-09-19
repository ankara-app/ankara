package io.ankara.ui.vaadin.print;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/5/16 7:35 PM
 */
@SpringUI(path = "/print")
@Theme("bootstrap")
public class PrintUI extends UI {

    @Inject
    private TemplateEngine templateEngine;

    @Override
    protected void init(VaadinRequest request) {
        // Have some content to print

        Context context = (Context) VaadinSession.getCurrent().getAttribute("context");
//        bindings.put("printing", true);
        String template = (String) VaadinSession.getCurrent().getAttribute("template");

        String content;

        try {
            content = templateEngine.process(template, context);
        } catch (Exception e) {
            content = "Failed to render content";
            e.printStackTrace();
        }

        setContent(new Label(content, ContentMode.HTML));

        //Print automatically when the window opens
        JavaScript.getCurrent().execute(loadPrintScript());
    }

    private String loadPrintScript() {

//        String printScroptPath = getClass().getClassLoader().getResource("js/print.js").getFile();
//
//        try {
//            return new String(Files.readAllBytes(new File(printScroptPath).toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
        return "setTimeout(function () {window.print();self.close();}, 0);";
    }
}