package io.ankara.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

/**
 * Configure the authentication manager.
 */
@Configuration
public class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }
}
