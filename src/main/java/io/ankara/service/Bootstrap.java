package io.ankara.service;

import io.ankara.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;


/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 7:12 PM
 */
@Service
public class Bootstrap implements InitializingBean {
    private final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Inject
    private UserService userService;


    @Override
    @Transactional()
    public void afterPropertiesSet() throws Exception {
        LOG.info("Bootstrapping data...");

        createSystemUser();

        LOG.info("Bootstrapping completed");
    }

    private void createSystemUser() {
        try {
            userService.loadUserByUsername("bonifacechacha@gmail.com");
        } catch (UsernameNotFoundException e) {
            LOG.info("... initialise users");
            userService.create(new User("bonifacechacha@gmail.com", "ankara", "Boniface Chacha",true, false));
        }
    }
}
