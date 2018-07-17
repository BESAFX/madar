package com.besafx.app.async;

import com.besafx.app.entity.Seller;
import com.besafx.app.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionalService {

    @Autowired
    private BankTransactionService bankTransactionService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void setBankTransactionsSellerToNull(Seller seller) {
        seller.getBankTransactions().stream().forEach(bankTransaction -> {
            bankTransaction.setSeller(null);
            bankTransactionService.save(bankTransaction);
        });
    }

}
