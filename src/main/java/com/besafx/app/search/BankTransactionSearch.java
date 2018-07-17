package com.besafx.app.search;

import com.besafx.app.entity.BankTransaction;
import com.besafx.app.service.BankTransactionService;
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
public class BankTransactionSearch {

    private static final Logger LOG = LoggerFactory.getLogger(BankTransactionSearch.class);

    @Autowired
    private BankTransactionService bankTransactionService;

    public Page<BankTransaction> filter(
            final Integer codeFrom,
            final Integer codeTo,
            final Long dateFrom,
            final Long dateTo,
            final String sellerName,
            final String sellerMobile,
            final String sellerIdentityNumber,
            final List<String> transactionTypeCodes,
            Pageable pageRequest) {

        List<Specification<BankTransaction>> predicates = new ArrayList<>();

        Optional.ofNullable(codeFrom)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("code"), value)));

        Optional.ofNullable(codeTo)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("code"), value)));

        Optional.ofNullable(dateFrom)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("date"), new DateTime(value).withTimeAtStartOfDay().toDate())));

        Optional.ofNullable(dateTo)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("date"), new DateTime(value).plusDays(1).withTimeAtStartOfDay().toDate())));

        Optional.ofNullable(sellerName)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.like(root.get("seller").get("contact").get("name"), "%" + value + "%")));

        Optional.ofNullable(sellerMobile)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.like(root.get("seller").get("contact").get("mobile"), "%" + value + "%")));

        Optional.ofNullable(sellerIdentityNumber)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> cb.like(root.get("seller").get("contact").get("identityNumber"), "%" + value + "%")));

        Optional.ofNullable(transactionTypeCodes)
                .ifPresent(value -> predicates.add(
                        (root, cq, cb) -> root.get("transactionType").get("code").in(transactionTypeCodes)));


        if (!predicates.isEmpty()) {
            Specification result = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                result = Specifications.where(result).and(predicates.get(i));
            }
            return bankTransactionService.findAll(result, pageRequest);
        } else {
            return bankTransactionService.findAll(pageRequest);
        }
    }
}
