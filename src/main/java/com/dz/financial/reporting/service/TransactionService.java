package com.dz.financial.reporting.service;

import com.dz.financial.reporting.model.db.Transaction;
import com.dz.financial.reporting.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {


    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<Transaction> queryTransactionWithTransactionId(String transactionId) {
        String[] split = transactionId.split("-");
        if (split.length == 3) {
            try {
                long id = Long.parseLong(split[0]);
                long date = Long.parseLong(split[1]);
                long merchantId = Long.parseLong(split[2]);
                return transactionRepository.findOneByIdAndDateAndMerchantId(id, date, merchantId);
            } catch (NumberFormatException e) {
                log.error("transactionId[" + transactionId + "] is not well formatted", e);
            }
        }
        return Optional.empty();
    }

    public List<Transaction> queryForReporting(Date startDate, Date toDate, Long merchantId, Long acquirerId) {
        return transactionRepository.queryAllForReporting(startDate, toDate,
                Optional.ofNullable(merchantId), Optional.ofNullable(acquirerId));
    }
}