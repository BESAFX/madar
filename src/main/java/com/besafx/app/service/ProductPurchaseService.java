package com.besafx.app.service;

import com.besafx.app.entity.ProductPurchase;
import com.besafx.app.entity.Seller;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProductPurchaseService extends PagingAndSortingRepository<ProductPurchase, Long>, JpaSpecificationExecutor<ProductPurchase> {

    ProductPurchase findTopByOrderByCodeDesc();

    ProductPurchase findByCodeAndIdIsNot(Integer code, Long id);

    List<ProductPurchase> findBySeller(Seller seller);

    List<ProductPurchase> findBySellerId(Long sellerId);
}
