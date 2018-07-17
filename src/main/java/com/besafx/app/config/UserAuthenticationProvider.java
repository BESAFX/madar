package com.besafx.app.config;

import com.besafx.app.auditing.PersonAwareUserDetails;
import com.besafx.app.entity.Person;
import com.besafx.app.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class UserAuthenticationProvider implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    @Autowired
    private PersonService personService;

    public UserAuthenticationProvider() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personService.findByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PROFILE_UPDATE"));

        PersonAwareUserDetails personAwareUserDetails = null;

        if (person != null) {

            log.info("BUILD USER DETAILS.");
            Optional.ofNullable(person.getTeam().getAuthorities())
                    .ifPresent(value -> Arrays.asList(value.split(",")).stream()
                                              .forEach(s -> authorities.add(new SimpleGrantedAuthority(s))));
            personAwareUserDetails = new PersonAwareUserDetails(person, authorities);

            log.info("SAVE LOGIN INFO.");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            person.setLastLoginDate(new Date());
            person.setActive(true);
            person.setIpAddress(request.getRemoteAddr());
            personService.save(person);

        } else {
            throw new UsernameNotFoundException("USER NOT FOUND.");
        }

        return personAwareUserDetails;
    }
}
