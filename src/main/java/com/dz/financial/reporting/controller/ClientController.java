package com.dz.financial.reporting.controller;

import com.dz.financial.reporting.model.db.Transaction;
import com.dz.financial.reporting.model.rest.CustomerInfoQueryRequest;
import com.dz.financial.reporting.model.rest.CustomerInfoQueryResponse;
import com.dz.financial.reporting.service.TransactionService;
import com.dz.financial.reporting.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClientController {


    private TransactionService transactionService;

    @Autowired
    public ClientController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/client")
    public ResponseEntity<?> queryCustomerInfoWithTransactionId(@RequestBody CustomerInfoQueryRequest request) {
        String transactionId = request.getTransactionId();
        Optional<Transaction> queriedTransaction = transactionService.queryTransactionWithTransactionId(transactionId);
        if (queriedTransaction.isPresent()) {
            Optional<CustomerInfoQueryResponse> convert = ModelConverter.convertToQueryCustomerInfoResponse(
                    queriedTransaction.get().getCustomerInfo());
            if (convert.isPresent()) {
                return ResponseEntity.ok(convert.get());
            }
        }

        return ResponseEntity.ok("");
    }
}