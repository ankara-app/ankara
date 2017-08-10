package io.ankara.service.impl;

import io.ankara.domain.Token;
import io.ankara.domain.User;
import io.ankara.service.MailService;
import io.ankara.utils.GeneralUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@Service
public class MailServiceBean implements MailService {

    @Inject
    private AnkaraMailSender mailSender;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Value("${ankara.mail.errors}")
    private String errorsEmail;

    public void sendConfirmationEmail(Token token) {

        String subject = "Ankara account confirmation";
        String to = token.getUser().getEmail();
        String template = "emailConfirmation";

        Context ctx = new Context();
        ctx.setVariable("user", token.getUser());
        ctx.setVariable("token", token.getToken());

        sendEmail(to, subject, template, ctx);

    }

    private void sendEmail(String to, String subject, String template, Context ctx) {
        ctx.setVariable("appAddress", GeneralUtils.getApplicationAddress());
        String htmlContent = this.templateEngine.process(template, ctx);

        mailSender.sendEmail(to, subject, htmlContent,true);
    }


    @Override
    public void sendPasswordResetEmail(User user, String password) {
        String subject = "Your ankara password have been reset";
        String to = user.getEmail();
        String template = "emailPasswordReset";

        Context ctx = new Context();
        ctx.setVariable("user", user);
        ctx.setVariable("password", password);

        sendEmail(to, subject, template, ctx);
    }

    @Override
    public void sendErrorsEmail(Throwable throwable) {
        throwable.printStackTrace();

        mailSender.sendEmail(
                errorsEmail,
                ExceptionUtils.getRootCauseMessage(throwable),
                ExceptionUtils.getStackTrace(throwable),
                false
        );
    }
}
