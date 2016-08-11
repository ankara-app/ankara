package io.ankara.service;

import io.ankara.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 6:26 PM
 */
public interface UserService extends UserDetailsService {
    User create(User user);
}
