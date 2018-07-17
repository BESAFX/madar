package com.besafx.app.service;

import com.besafx.app.entity.Contract;
import com.besafx.app.entity.ContractAttach;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ContractAttachService extends PagingAndSortingRepository<ContractAttach, Long>, JpaSpecificationExecutor<ContractAttach> {
    List<ContractAttach> findByContract(Contract id);
    List<ContractAttach> findByContractId(Long id);
}
