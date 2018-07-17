package com.besafx.app.controller;

import com.besafx.app.auditing.PersonAwareUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PartialController {

    private final static Logger log = LoggerFactory.getLogger(PartialController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView navToHome() {
        PersonAwareUserDetails userDetails = (PersonAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Trying to log in with user name: " + userDetails.getUsername());
        if (userDetails.getUsername().equals("anonymousUser")) {
            return new ModelAndView("redirect:/login");
        } else {
            return new ModelAndView("index");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String navToLogin() {
        return "login";
    }


    @RequestMapping(value = {"/login", "/menu"})
    public ModelAndView navToView() {
        return new ModelAndView("forward:/");
    }
}
