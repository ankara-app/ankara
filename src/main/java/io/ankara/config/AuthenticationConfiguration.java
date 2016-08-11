package io.ankara.config;

import io.ankara.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

import javax.inject.Inject;

/**
 * Configure the authentication manager.
 */
@Configuration
public class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

    @Inject
    private UserService userService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
