package io.ankara.service.impl;

import io.ankara.domain.Token;
import io.ankara.domain.User;
import io.ankara.repository.UserRepository;
import io.ankara.service.MailService;
import io.ankara.service.TokenService;
import io.ankara.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 6:51 PM
 */

@Service
public class UserServiceBean implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder encoder;

    @Inject
    private MailService mailService;

    @Inject
    private TokenService tokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUser(email);
        if (user == null) throw new UsernameNotFoundException("There is no user with username " + email);
        return user;
    }

    @Override
    @Transactional
    public boolean create(User user) {
        User existingUser = getUser(user.getEmail());
        if (existingUser != null)
            throw new IllegalArgumentException("There is already another user registered with email " + user.getEmail());

        user.setTimeCreated(new Date());
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);

        return requestVerification(user.getEmail());
    }

    @Override
    @Transactional
    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean requestVerification(String email) {
        User user = getUser(email);
        if (user == null)
            throw new IllegalArgumentException("There is no user with email " + email);
        else if (user.isEnabled())
            throw new IllegalArgumentException("User account is already verified");

        Token token = tokenService.create(user);
        mailService.sendConfirmationEmail(token);

        return true;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) return null;

        User user = (User) authentication.getPrincipal();
        return getUser(user.getEmail());
    }

    public boolean isCurrentUserAuthenticated() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return false;
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        } else return authentication.isAuthenticated();

    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser(String userEmail) {
        return userRepository.findById(userEmail).orElseThrow(() -> new IllegalArgumentException("There is no user with email "+userEmail));
    }

    @Override
    @Transactional
    public User verify(String tokenID) {
        Token token = tokenService.getToken(tokenID);

        if (token != null) {
            User user = token.getUser();
            if (user.isEnabled())
                throw new IllegalArgumentException("User account is already verified");

            user.setEnabled(true);
            save(user);
            tokenService.delete(token);
            return user;
        } else throw new IllegalArgumentException("Invalid verification token ");
    }

    @Override
    @Transactional
    public boolean resetPassword(String email) {
        User user = getUser(email);
        if (user == null)
            throw new IllegalArgumentException("There is no user with email " + email);

        String password = KeyGenerators.string().generateKey();
        changePassword(user, password);
        mailService.sendPasswordResetEmail(user, password);
        return true;
    }

    @Override
    @Transactional
    public boolean changePassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        return save(user);
    }
}
