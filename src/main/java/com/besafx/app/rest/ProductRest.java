package com.besafx.app.rest;

import com.besafx.app.auditing.PersonAwareUserDetails;
import com.besafx.app.config.CustomException;
import com.besafx.app.entity.Person;
import com.besafx.app.entity.Product;
import com.besafx.app.search.ProductSearch;
import com.besafx.app.service.ProductService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/product/")
public class ProductRest {

    private final static Logger LOG = LoggerFactory.getLogger(ProductRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "person[id,contact[id,shortName]]," +
            "parent[id,name]," +
            "childs[id,name]," +
            "-productPurchases";

    private final String FILTER_PARENT = "" +
            "id," +
            "code," +
            "name";

    private final String FILTER_CHILD = "" +
            "id," +
            "code," +
            "name," +
            "parent[id,name]";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSearch productSearch;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_PRODUCT_CREATE')")
    @Transactional
    public String create(@RequestBody Product product) {
        Product topProduct = productService.findTopByOrderByCodeDesc();
        if (topProduct == null) {
            product.setCode(1);
        } else {
            product.setCode(topProduct.getCode() + 1);
        }
        Person caller = ((PersonAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson();
        product.setEnabled(true);
        product.setRegisterDate(new DateTime().toDate());
        product.setPerson(caller);
        product = productService.save(product);
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم انشاء التصنيف / السلعة بنجاح")
                                              .type("success").build());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), product);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_PRODUCT_UPDATE')")
    @Transactional
    public String update(@RequestBody Product product) {
        if (productService.findByCodeAndIdIsNot(product.getCode(), product.getId()) != null) {
            throw new CustomException("هذا الكود مستخدم سابقاً، فضلاً قم بتغير الكود.");
        }
        Product object = productService.findOne(product.getId());
        if (object != null) {
            Person caller = ((PersonAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson();
            product.setEnabled(true);
            product.setRegisterDate(new DateTime().toDate());
            product.setPerson(caller);
            product = productService.save(product);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم تعديل بيانات التصنيف / السلعة بنجاح")
                                                  .type("success").build());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), product);
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_PRODUCT_DELETE')")
    @Transactional
    public void delete(@PathVariable Long id) {
        Product product = productService.findOne(id);
        if (product != null) {
            productService.delete(id);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم حذف التصنيف / السلعة بنجاح")
                                                  .type("error").build());
        }
    }

    @GetMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       productService.findOne(id));
    }

    @GetMapping(value = "findParents", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findParents() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_PARENT),
                                       productService.findByParentIsNull());
    }

    @GetMapping(value = "findChilds/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findChilds(@PathVariable(value = "parentId") Long parentId) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_CHILD),
                                       productService.findByParentId(parentId));
    }

    @GetMapping(value = "filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filter(
            //Product Filters
            @RequestParam(value = "codeFrom", required = false) final Integer codeFrom,
            @RequestParam(value = "codeTo", required = false) final Integer codeTo,
            @RequestParam(value = "registerDateFrom", required = false) final Long registerDateFrom,
            @RequestParam(value = "registerDateTo", required = false) final Long registerDateTo,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "parentId", required = false) final Long parentId,
            Pageable pageable) {
        return SquigglyUtils.stringify(
                Squiggly.init(
                        new ObjectMapper(),
                        "**,".concat("content[").concat(FILTER_TABLE).concat("]")),
                productSearch.filter(
                        codeFrom,
                        codeTo,
                        registerDateFrom,
                        registerDateTo,
                        name,
                        parentId,
                        pageable));
    }
}
