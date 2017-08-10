package io.ankara.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/22/16.
 */
@Component
public class AnkaraMailSender {

    @Inject
    private JavaMailSender mailSender;

    @Value("${ankara.mail.from}")
    private String from;

    @Async
    public void sendEmail(String to, String subject, String content, boolean includeLogo) {
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
}
