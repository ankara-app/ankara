package io.ankara.service;

import io.ankara.domain.Token;
import io.ankara.domain.User;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
public interface MailService {
    void sendConfirmationEmail(Token token);

    void sendPasswordResetEmail(User user, String password);
}
