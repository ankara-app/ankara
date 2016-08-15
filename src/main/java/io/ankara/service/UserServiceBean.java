package io.ankara.service;

import io.ankara.domain.User;
import io.ankara.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

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
    private Authentication authentication;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOne(email);
        if (user == null) throw new UsernameNotFoundException("There is no user with username " + email);
        return user;
    }

    @Override
    @Transactional
    public boolean create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    @Transactional
    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public User getCurrentUser() {
        User user = (User) authentication.getPrincipal();
        return userRepository.findOne(user.getEmail());
    }

    @Override
    @Transactional
    public boolean changePassword(User user,String password){
        user.setPassword(encoder.encode(password));
        return save(user);
    }
}
