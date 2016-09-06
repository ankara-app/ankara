package io.ankara.ui.vaadin.main;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import io.ankara.AnkaraTemplateEngine;

import javax.inject.Inject;
import java.io.IOException;
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
    private AnkaraTemplateEngine templateEngine;

    @Override
    protected void init(VaadinRequest request) {
        // Have some content to print

        Map bindings = new HashMap((Map) VaadinSession.getCurrent().getAttribute("bindings"));
        bindings.put("printing",true);

        String template = (String) VaadinSession.getCurrent().getAttribute("template");

        String content;

        try {
            content = templateEngine.generate(template,bindings);
        } catch (IOException| ClassNotFoundException e) {
            content = "Failed to render content";
            e.printStackTrace();
        }

        setContent(new Label(content,ContentMode.HTML));

//         Print automatically when the window opens
        //         Print automatically when the window opens
        JavaScript.getCurrent().execute(
                "setTimeout(function() {" +
                        "  var originalContents = document.body.innerHTML;\n" +
                        "        var printReport= document.getElementById('printSection').innerHTML;\n" +
                        "        document.body.innerHTML = printReport;\n" +
                        "        window.print();\n" +
                        "        document.body.innerHTML = originalContents" +
                        "; self.close();}, 0);");
    }
}