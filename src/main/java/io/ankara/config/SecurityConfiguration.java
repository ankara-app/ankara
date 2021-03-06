package io.ankara.config;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/8/16.
 */

import io.ankara.domain.User;
import io.ankara.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.http.HttpService;
import org.vaadin.spring.security.annotation.EnableVaadinSharedSecurity;
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration;
import org.vaadin.spring.security.shared.VaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.shared.VaadinSessionClosingLogoutHandler;
import org.vaadin.spring.security.shared.VaadinUrlAuthenticationSuccessHandler;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;

import javax.inject.Inject;

/**
 * Configure Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
@EnableVaadinSharedSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String APP_KEY = "io.ankara.spring.security";

    @Inject
    private UserService userService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Use Vaadin's built-in CSRF protection instead
        http.authorizeRequests()
                .antMatchers("/app/**").authenticated()
                .antMatchers("/print/**").authenticated()
                .antMatchers("/welcome/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/vaadinServlet/UIDL/**").permitAll()
                .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.httpBasic().disable();
        http.formLogin().disable();
        // Remember to add the VaadinSessionClosingLogoutHandler
        http.logout().addLogoutHandler(new VaadinSessionClosingLogoutHandler()).logoutUrl("/logout")
                .logoutSuccessUrl("/welcome?logout").permitAll();
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/welcome"));
        // Instruct Spring Security to use the same RememberMeServices as Vaadin4Spring. Also remember the key.
        http.rememberMe().rememberMeServices(rememberMeServices()).key(APP_KEY);
        // Instruct Spring Security to use the same authentication strategy as Vaadin4Spring
        http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/VAADIN/**");
    }

    /**
     * The {@link AuthenticationManager} must be available as a Spring bean for Vaadin4Spring.
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * The {@link RememberMeServices} must be available as a Spring bean for Vaadin4Spring.
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices(APP_KEY, userDetailsService());
    }

    /**
     * The {@link SessionAuthenticationStrategy} must be available as a Spring bean for Vaadin4Spring.
     */
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new SessionFixationProtectionStrategy();
    }

    @Bean(name = VaadinSharedSecurityConfiguration.VAADIN_AUTHENTICATION_SUCCESS_HANDLER_BEAN)
    VaadinAuthenticationSuccessHandler vaadinAuthenticationSuccessHandler(HttpService httpService,
                                                                          VaadinRedirectStrategy vaadinRedirectStrategy) {
        return new VaadinUrlAuthenticationSuccessHandler(httpService, vaadinRedirectStrategy, "/app");
    }

    @Bean
    @PrototypeScope
    User currentUser(){
        return userService.getCurrentUser();
    }
}