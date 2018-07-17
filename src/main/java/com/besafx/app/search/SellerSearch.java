package com.besafx.app.search;

import com.besafx.app.entity.Seller;
import com.besafx.app.service.SellerService;
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
public class SellerSearch {

    private static final Logger LOG = LoggerFactory.getLogger(SellerSearch.class);

    @Autowired
    private SellerService sellerService;

    public Page<Seller> filter(
            final Integer codeFrom,
            final Integer codeTo,
            final Long registerDateFrom,
            final Long registerDateTo,
            final String name,
            final String mobile,
            final String phone,
            final String nationality,
            final String identityNumber,
            final String qualification,
            final String filterCompareType,
            Pageable pageRequest
                              ) {
        List<Specification<Seller>> predicates = new ArrayList<>();
        Optional.ofNullable(codeFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(codeTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(registerDateFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("registerDate"),
                                                                                                                          new DateTime(value)
                                                                                                                                  .withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(registerDateTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("registerDate"), new
                DateTime(value).plusDays(1).withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(name).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("contact").get("name"), "%" + value + "%")));
        Optional.ofNullable(mobile).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("contact").get("mobile"), "%" + value +
                "%")));
        Optional.ofNullable(phone).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("contact").get("phone"), "%" + value + "%")));
        Optional.ofNullable(nationality).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("contact").get("nationality"), "%" +
                value + "%")));
        Optional.ofNullable(identityNumber).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("contact").get("identityNumber"),
                                                                                                        "%" + value + "%")));
        Optional.ofNullable(qualification).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("contact").get("qualification"),
                                                                                                       "%" + value + "%")));

        if (!predicates.isEmpty()) {
            Specification result = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                if (filterCompareType == null) {
                    result = Specifications.where(result).and(predicates.get(i));
                    continue;
                }
                result = filterCompareType.equalsIgnoreCase("and") ? Specifications.where(result).and(predicates.get(i)) : Specifications.where
                        (result).or(predicates.get(i));
            }
            return sellerService.findAll(result, pageRequest);
        } else {
            return sellerService.findAll(pageRequest);
        }
    }
}
