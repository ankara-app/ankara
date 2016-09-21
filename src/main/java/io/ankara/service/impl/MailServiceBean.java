package io.ankara.service.impl;

import io.ankara.domain.Token;
import io.ankara.domain.User;
import io.ankara.service.MailService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

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
        ctx.setVariable("appAddress", getApplicationAddress());
        String htmlContent = this.templateEngine.process(template, ctx);

        mailSender.sendEmail(to, subject, htmlContent,true);
    }

    //This method can not be invoked asynchronous
    private String getApplicationAddress() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String appAddress = request.getRequestURL().toString().replace("/vaadinServlet/UIDL/","");

        return appAddress;
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
        mailSender.sendEmail(
                errorsEmail,
                ExceptionUtils.getRootCauseMessage(throwable),
                ExceptionUtils.getStackTrace(throwable),
                false
        );
    }
}
