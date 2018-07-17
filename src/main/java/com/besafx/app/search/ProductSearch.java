package com.besafx.app.search;

import com.besafx.app.entity.Product;
import com.besafx.app.service.ProductService;
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
import java.util.List;
import java.util.Optional;

@Component
public class ProductSearch {

    private static final Logger LOG = LoggerFactory.getLogger(ProductSearch.class);

    @Autowired
    private ProductService productService;

    public Page<Product> filter(
            final Integer codeFrom,
            final Integer codeTo,
            final Long registerDateFrom,
            final Long registerDateTo,
            final String name,
            final Long parentId,
            Pageable pageRequest) {
        List<Specification<Product>> predicates = new ArrayList<>();
        Optional.ofNullable(codeFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(codeTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(registerDateFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("registerDate"), new DateTime(value).withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(registerDateTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("registerDate"), new DateTime(value).plusDays(1).withTimeAtStartOfDay().toDate())));
        Optional.ofNullable(name).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("name"), "%" + value + "%")));
        Optional.ofNullable(parentId).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("parent").get("id"), value)));
        //تستخدم هنا للتأكد من عرض كل السلع فقط بدون التصنيفات
        predicates.add((root, cq, cb) -> cb.isNotNull(root.get("parent").get("id")));

        if (!predicates.isEmpty()) {
            Specification result = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                result = Specifications.where(result).and(predicates.get(i));
            }
            return productService.findAll(result, pageRequest);
        } else {
            return productService.findAll(pageRequest);
        }
    }
}
