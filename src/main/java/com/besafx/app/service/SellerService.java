package com.besafx.app.service;

import com.besafx.app.entity.Seller;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface SellerService extends PagingAndSortingRepository<Seller, Long>, JpaSpecificationExecutor<Seller> {

    Seller findTopByOrderByCodeDesc();

    Seller findByCodeAndIdIsNot(Integer code, Long id);
}
