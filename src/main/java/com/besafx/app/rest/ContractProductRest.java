package com.besafx.app.rest;

import com.besafx.app.entity.ContractProduct;
import com.besafx.app.service.ContractProductService;
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

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/contractProduct/")
public class ContractProductRest {

    private final static Logger LOG = LoggerFactory.getLogger(ContractProductRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "productPurchase[id,unitPurchasePrice,product[id,name]]," +
            "-contract";

    @Autowired
    private ContractProductService contractProductService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_PRODUCT_CREATE')")
    @Transactional
    public String create(@RequestBody ContractProduct contractProduct) {
        contractProduct = contractProductService.save(contractProduct);
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم اضافة سلعة للعقد بنجاح")
                                              .type(NotificationDegree.success).build());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), contractProduct);
    }

    @PostMapping(value = "createBatch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_PRODUCT_CREATE')")
    @Transactional
    public String createBatch(@RequestBody List<ContractProduct> contractProducts) {
        contractProducts = Lists.newArrayList(contractProductService.save(contractProducts));
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم اضافة عدد من السلع للعقد بنجاح")
                                              .type(NotificationDegree.success).build());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), contractProducts);
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_PRODUCT_DELETE')")
    @Transactional
    public void delete(@PathVariable Long id) {
        ContractProduct contractProduct = contractProductService.findOne(id);
        if (contractProduct != null) {
            contractProductService.delete(id);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم حذف سلع من العقد بنجاح")
                                                  .type(NotificationDegree.error).build());
        }
    }

    @GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAll() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       Lists.newArrayList(contractProductService.findAll()));
    }

    @GetMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       contractProductService.findOne(id));
    }

    @GetMapping(value = "findByContract/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findByContract(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       contractProductService.findByContractId(id));
    }
}
