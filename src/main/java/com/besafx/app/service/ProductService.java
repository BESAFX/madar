package com.besafx.app.service;

import com.besafx.app.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProductService extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Product findTopByOrderByCodeDesc();

    Product findByCodeAndIdIsNot(Integer code, Long id);

    List<Product> findByParentIsNull();

    List<Product> findByParentId(Long id);
}
