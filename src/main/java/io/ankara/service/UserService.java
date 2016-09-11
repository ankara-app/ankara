package io.ankara.service;

import io.ankara.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 6:26 PM
 */
public interface UserService extends UserDetailsService {
    boolean create(User user);

    boolean save(User user);

    User getCurrentUser();

    boolean changePassword(User user, String password);

    boolean isCurrentUserAuthenticated();

    Authentication getAuthentication();
}
