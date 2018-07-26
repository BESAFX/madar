package com.besafx.app.rest;

import com.besafx.app.auditing.EntityHistoryListener;
import com.besafx.app.auditing.PersonAwareUserDetails;
import com.besafx.app.entity.Company;
import com.besafx.app.entity.Person;
import com.besafx.app.init.Initializer;
import com.besafx.app.service.CompanyService;
import com.besafx.app.util.CompanyOptions;
import com.besafx.app.util.JSONConverter;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @Autowired
    private EntityHistoryListener entityHistoryListener;

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

    @GetMapping(value = "updateOptions")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_COMPANY_UPDATE')")
    @Transactional
    public void updateOptions(
            @RequestParam(value = "yamamahUserName", required = false) String yamamahUserName,
            @RequestParam(value = "yamamahPassword", required = false) String yamamahPassword,
            @RequestParam(value = "vatFactor", required = false) Double vatFactor,
            @RequestParam(value = "logo", required = false) String logo,
            @RequestParam(value = "background", required = false) String background,
            @RequestParam(value = "reportTitle", required = false) String reportTitle,
            @RequestParam(value = "reportSubTitle", required = false) String reportSubTitle,
            @RequestParam(value = "reportFooter", required = false) String reportFooter) {
        Company company = Initializer.company;
        if (company.getOptions() != null) {
            CompanyOptions options = JSONConverter.toObject(company.getOptions(), CompanyOptions.class);
            Optional.ofNullable(yamamahUserName).ifPresent(value -> options.setYamamahUserName(yamamahUserName));
            Optional.ofNullable(yamamahPassword).ifPresent(value -> options.setYamamahPassword(yamamahPassword));
            Optional.ofNullable(vatFactor).ifPresent(value -> options.setVatFactor(vatFactor));
            Optional.ofNullable(logo).ifPresent(value -> options.setLogo(logo));
            Optional.ofNullable(background).ifPresent(value -> options.setBackground(background));
            Optional.ofNullable(reportTitle).ifPresent(value -> options.setReportTitle(reportTitle));
            Optional.ofNullable(reportSubTitle).ifPresent(value -> options.setReportSubTitle(reportSubTitle));
            Optional.ofNullable(reportFooter).ifPresent(value -> options.setReportFooter(reportFooter));
            company.setOptions(JSONConverter.toString(options));
            companyService.save(company);

            Person caller = ((PersonAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson();
            StringBuilder builder = new StringBuilder();
            builder.append("تعديل خيارات البرنامج ");
            builder.append("، بواسطة ");
            builder.append(caller.getContact().getShortName());
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .title("العمليات على المؤسسة")
                                                  .message(builder.toString())
                                                  .type(NotificationDegree.warning)
                                                  .icon("edit")
                                                  .build());
            entityHistoryListener.perform(builder.toString());
        }
    }

}
