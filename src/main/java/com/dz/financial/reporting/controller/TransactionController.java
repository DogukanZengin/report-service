package com.dz.financial.reporting.controller;

import com.dz.financial.reporting.model.db.Transaction;
import com.dz.financial.reporting.model.db.enums.Status;
import com.dz.financial.reporting.model.rest.ReportRequest;
import com.dz.financial.reporting.model.rest.ReportResponse;
import com.dz.financial.reporting.model.rest.TransactionQueryRequest;
import com.dz.financial.reporting.model.rest.TransactionQueryResponse;
import com.dz.financial.reporting.service.TransactionService;
import com.dz.financial.reporting.util.ModelConverter;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> queryTransactionWithTransactionId(@RequestBody TransactionQueryRequest request) {
        String transactionId = request.getTransactionId();
        Optional<Transaction> queriedTransaction = transactionService.queryTransactionWithTransactionId(transactionId);
        if (queriedTransaction.isPresent()) {
            Optional<TransactionQueryResponse> convert = ModelConverter.convertToQueryTransactionResponse(queriedTransaction.get());
            if (convert.isPresent()) {
                return ResponseEntity.ok(convert.get());
            }
        }

        return ResponseEntity.ok("");
    }

    @PostMapping("/transactions/report")
    public ResponseEntity<?> getReport(@Valid @RequestBody ReportRequest request) {
        List<Transaction> transactions = transactionService.queryForReporting(request.getFromDate(), request.getToDate(),
                request.getMerchantId(), request.getAcquirerId());
        List<ReportResponse.ReportData> reportDataList = new ArrayList<>();
        if (transactions.size() > 0) {
            transactions.stream().collect(
                    Collectors.groupingBy((t) -> t.getFxTransaction().getOriginalCurrency(),
                            Collectors.mapping((t) -> t.getFxTransaction().getOriginalAmount(),
                                    Collectors2.summarizingBigDecimal(each -> each)))).
                    forEach((currency, stat) -> {
                        ReportResponse.ReportData reportData = new ReportResponse.ReportData();
                        reportData.setCurrency(currency);
                        reportData.setCount(stat.getCount());
                        reportData.setTotal(stat.getSum().toString());
                        reportDataList.add(reportData);
                    });
            if (reportDataList.size() > 0) {
                ReportResponse response = new ReportResponse();
                response.setStatus(Status.APPROVED);
                response.setReportData(reportDataList);
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.ok("");
    }
}