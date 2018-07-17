package com.besafx.app.search;

import com.besafx.app.entity.ProductPurchase;
import com.besafx.app.service.ProductPurchaseService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ProductPurchaseSearch {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPurchaseSearch.class);

    @Autowired
    private ProductPurchaseService productPurchaseService;

    public Page<ProductPurchase> filter(
            //ProductPurchase Filters
            final Integer codeFrom,
            final Integer codeTo,
            final Long dateFrom,
            final Long dateTo,
            //Product Filters
            final Integer productCodeFrom,
            final Integer productCodeTo,
            final Long productRegisterDateFrom,
            final Long productRegisterDateTo,
            final String productName,
            final Long productParentId,
            //Seller Filters
            final String sellerName,
            final String sellerMobile,
            final String sellerIdentityNumber,
            Pageable pageRequest) {

        List<Specification<ProductPurchase>> predicates = new ArrayList<>();

        //ProductPurchase Specifications
        Optional.ofNullable(codeFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(codeTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(dateFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("date"), new DateTime
                (value).withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(dateTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("date"), new DateTime(value)
                .plusDays(1).withTimeAtStartOfDay().toDate())));

        //Product Specifications
        Optional.ofNullable(productCodeFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("product").get
                ("code"), value)));
        Optional.ofNullable(productCodeTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("product").get("code")
                , value)));
        Optional.ofNullable(productRegisterDateFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get
                ("product").get("registerDate"), new DateTime(value).withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(productRegisterDateTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("product").get
                ("registerDate"), new DateTime(value).plusDays(1).withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(productName).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("product").get("name"), "%" + value +
                "%")));
        Optional.ofNullable(productParentId).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("product").get("parent").get
                ("id"), value)));

        Optional.ofNullable(sellerName).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("seller").get("contact").get("name"),
                                                                                                    "%" + value + "%")));
        Optional.ofNullable(sellerMobile).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("seller").get("contact").get
                ("mobile"), "%" + value + "%")));
        Optional.ofNullable(sellerIdentityNumber).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("seller").get("contact").get
                ("identityNumber"), "%" + value + "%")));

        if (!predicates.isEmpty()) {
            Specification result = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                result = Specifications.where(result).and(predicates.get(i));
            }
            return productPurchaseService.findAll(result, pageRequest);
        } else {
            return productPurchaseService.findAll(pageRequest);
        }
    }
}
