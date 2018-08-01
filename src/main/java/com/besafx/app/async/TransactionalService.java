package com.besafx.app.async;

import com.besafx.app.entity.Contract;
import com.besafx.app.entity.Seller;
import com.besafx.app.service.BankTransactionService;
import com.besafx.app.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionalService {

    @Autowired
    private BankTransactionService bankTransactionService;

    @Autowired
    private ContractService contractService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void setBankTransactionsSellerToNull(Seller seller) {
        seller.getBankTransactions().stream().forEach(bankTransaction -> {
            bankTransaction.setSeller(null);
            bankTransactionService.save(bankTransaction);
        });
    }

    @Transactional
    public Long getNextContractCode(){
        Contract topContract = contractService.findTopByOrderByCodeDesc();
        if(topContract == null){
            return Long.valueOf(1);
        }else{
            return topContract.getCode() + 1;
        }
    }

}
