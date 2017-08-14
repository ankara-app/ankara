package io.ankara.ui.vaadin;

import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import io.ankara.service.MailService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/20/16.
 */
public abstract class AnkaraUI extends UI{

    @Inject
    private MailService mailService;

    @PostConstruct
    private void setup(){
        VaadinSession.getCurrent().setErrorHandler((ErrorHandler) event -> {
            mailService.sendErrorsEmail(event.getThrowable());
        });
    }
}
