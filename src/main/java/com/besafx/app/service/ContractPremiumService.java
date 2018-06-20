package com.besafx.app.service;

import com.besafx.app.entity.ContractPremium;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ContractPremiumService extends PagingAndSortingRepository<ContractPremium, Long>, JpaSpecificationExecutor<ContractPremium> {

    List<ContractPremium> findByContractId(Long id, Sort sort);
}
