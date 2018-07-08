package com.besafx.app.config;

import com.besafx.app.entity.Person;
import com.besafx.app.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthenticationProvider).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeRequests()
            .antMatchers("/ui/**").permitAll()
            .antMatchers("/api/**").permitAll()
            .anyRequest().authenticated();
        http.httpBasic();
        http.formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/menu")
            .permitAll();
        http.logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        http.rememberMe();
        http.csrf().disable();
        http.sessionManagement()
            .maximumSessions(1)
            .sessionRegistry(sessionRegistry());
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> sessionEventPublisherServletListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher() {
            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                SecurityContextImpl securityContext = (SecurityContextImpl) event.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                if (securityContext != null) {
                    UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
                    Person person = personService.findByEmail(userDetails.getUsername());
                    person.setActive(false);
                    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                    person.setIpAddress(request.getRemoteAddr());
                    personService.save(person);
                }
                super.sessionDestroyed(event);
            }
        });
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
