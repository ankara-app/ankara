package io.ankara.service;

import io.ankara.domain.Token;
import io.ankara.domain.User;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/12/16.
 */
public interface TokenService {
    Token create(User user);

    Token getToken(String tokenID);

    boolean delete(Token token);
}
