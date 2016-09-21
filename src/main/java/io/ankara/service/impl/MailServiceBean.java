package io.ankara.service.impl;

import io.ankara.domain.Token;
import io.ankara.domain.User;
import io.ankara.service.MailService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@Service
public class MailServiceBean implements MailService {

    @Inject
    private JavaMailSender mailSender;

    @Inject
    private SpringTemplateEngine templateEngine;


    @Value("${ankara.mail.from}")
    private String from;

    @Value("${ankara.mail.errors}")
    private String errorsEmail;

    @Value("${ankara.app.address}")
    private String appAddress;

    @Async
    public void sendConfirmationEmail(Token token) {

        String subject = "Ankara account confirmation";
        String to = token.getUser().getEmail();
        String template = "emailConfirmation";

        Context ctx = new Context();
        ctx.setVariable("user", token.getUser());
        ctx.setVariable("token", token.getToken());
        ctx.setVariable("appAddress", appAddress);

        sendEmail(to, subject, template, ctx);

    }

    private void sendEmail(String to, String subject, String template, Context ctx) {
        ctx.setVariable("appAddress", appAddress);
        String htmlContent = this.templateEngine.process(template, ctx);

        sendEmail(to, subject, htmlContent,true);
    }

    private void sendEmail(String to, String subject, String content, boolean includeLogo) {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper message = null;

        try {
            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(subject);
            message.setTo(to);
            message.setFrom(from);
            message.setText(content, true);
            if (includeLogo)
                message.addInline("logo.png", new ClassPathResource("images/logo.png"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }

    @Async
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

    @Async
    @Override
    public void sendErrorsEmail(Throwable throwable) {
        sendEmail(
                errorsEmail,
                ExceptionUtils.getRootCauseMessage(throwable),
                ExceptionUtils.getStackTrace(throwable),
                false
        );
    }
}
