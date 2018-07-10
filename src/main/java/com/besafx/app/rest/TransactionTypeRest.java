package com.besafx.app.rest;

import com.besafx.app.entity.TransactionType;
import com.besafx.app.service.TransactionTypeService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationDegree;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/transactionType/")
public class TransactionTypeRest {

    private final static Logger LOG = LoggerFactory.getLogger(TransactionTypeRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "transactionType[id]";

    private TransactionType transactionType;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private NotificationService notificationService;

    @PostConstruct
    public void init() {
        this.transactionType = transactionTypeService.findByCode("Withdraw_Cash");
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TRANSACTION_TYPE_CREATE')")
    @Transactional
    public String create(@RequestBody TransactionType transactionType) {
        transactionType.setTransactionType(this.transactionType);
        transactionType = transactionTypeService.save(transactionType);
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم انشاء بند مصروفات بنجاح")
                                              .type(NotificationDegree.success).build());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), transactionType);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TRANSACTION_TYPE_UPDATE')")
    @Transactional
    public String update(@RequestBody TransactionType transactionType) {
        TransactionType object = transactionTypeService.findOne(transactionType.getId());
        if (object != null) {
            transactionType.setTransactionType(this.transactionType);
            transactionType = transactionTypeService.save(transactionType);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم تعديل بيانات البند بنجاح")
                                                  .type(NotificationDegree.success).build());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), transactionType);
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TRANSACTION_TYPE_DELETE')")
    @Transactional
    public void delete(@PathVariable Long id) {
        TransactionType transactionType = transactionTypeService.findOne(id);
        if (transactionType != null) {
            transactionTypeService.delete(id);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم حذف البند بنجاح")
                                                  .type(NotificationDegree.error).build());
        }
    }

    @GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAll() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       Lists.newArrayList(transactionTypeService.findAll()));
    }

    @GetMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       transactionTypeService.findOne(id));
    }
}
