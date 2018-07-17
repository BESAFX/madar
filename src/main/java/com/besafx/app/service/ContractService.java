package com.besafx.app.service;

import com.besafx.app.entity.Contract;
import com.besafx.app.entity.Seller;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ContractService extends PagingAndSortingRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

    Contract findTopByOrderByCodeDesc();

    Contract findByCode(Long code);

    Contract findByCodeAndIdIsNot(Long code, Long id);

    List<Contract> findBySeller(Seller seller);
}
