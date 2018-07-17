package com.besafx.app.rest;

import com.besafx.app.entity.Company;
import com.besafx.app.init.Initializer;
import com.besafx.app.service.CompanyService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationDegree;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/company/")
public class CompanyRest {

    private final static Logger LOG = LoggerFactory.getLogger(CompanyRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "seller[id,contact[id,shortName]]," +
            "persons[id]";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String get() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), companyService.findFirstBy());
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_COMPANY_UPDATE')")
    @Transactional
    public String update(@RequestBody Company company) {
        Company object = companyService.findOne(company.getId());
        if (object != null) {
            company = companyService.save(company);
            Initializer.company = company;
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم تعديل بيانات الشركة بنجاح")
                                                  .type(NotificationDegree.warning)
                                                  .build());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), company);
        } else {
            return null;
        }
    }

}
