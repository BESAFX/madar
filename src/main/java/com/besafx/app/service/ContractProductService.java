package com.besafx.app.service;

import com.besafx.app.entity.ContractProduct;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ContractProductService extends PagingAndSortingRepository<ContractProduct, Long>, JpaSpecificationExecutor<ContractProduct> {
    List<ContractProduct> findByContractId(Long id);
}
