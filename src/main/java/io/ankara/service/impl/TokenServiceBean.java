package io.ankara.service.impl;

import io.ankara.domain.Token;
import io.ankara.domain.User;
import io.ankara.repository.TokenRepository;
import io.ankara.service.TokenService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/12/16.
 */
@Service
public class TokenServiceBean implements TokenService {

    @Inject
    private TokenRepository tokenRepository;

    @Transactional
    @Override
    public Token create(User user) {
        Token token = new Token(user);
        return tokenRepository.save(token);
    }

    @Override
    public Token getToken(String tokenID) {
        return tokenRepository.findById(tokenID).orElseThrow(() -> new IllegalArgumentException("There is no token with id "+tokenID));
    }

    @Transactional
    @Override
    public boolean delete(Token token) {
        tokenRepository.delete(token);
        return true;
    }
}
