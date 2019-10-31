package com.dz.financial.reporting.util;

import com.dz.financial.reporting.model.db.CustomerInfo;
import com.dz.financial.reporting.model.db.Transaction;
import com.dz.financial.reporting.model.rest.CustomerInfoJson;
import com.dz.financial.reporting.model.rest.CustomerInfoQueryResponse;
import com.dz.financial.reporting.model.rest.TransactionQueryResponse;

import java.util.Optional;

public final class ModelConverter {
    private ModelConverter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Optional<TransactionQueryResponse> convertToQueryTransactionResponse(Transaction transaction) {
        if (transaction == null) {
            return Optional.empty();
        }
        TransactionQueryResponse tqr = new TransactionQueryResponse();

        tqr.setCustomerInfo(convertToCustomerInfoJson(transaction.getCustomerInfo()));

        TransactionQueryResponse.Fx fx = new TransactionQueryResponse.Fx();
        TransactionQueryResponse.Fx.FxMerchant fxMerchant = new TransactionQueryResponse.Fx.FxMerchant();
        fxMerchant.setOriginalAmount(transaction.getFxTransaction().getOriginalAmount().toString());
        fxMerchant.setOriginalCurrency(transaction.getFxTransaction().getOriginalCurrency());
        fx.setFxMerchant(fxMerchant);
        tqr.setFx(fx);

        TransactionQueryResponse.Transaction respTran = new TransactionQueryResponse.Transaction();
        TransactionQueryResponse.Transaction.TransactionMerchant respTranMerchant =
                new TransactionQueryResponse.Transaction.TransactionMerchant();
        respTranMerchant.setId(transaction.getId());
        respTranMerchant.setReferenceNo(transaction.getReferenceNo());
        respTranMerchant.setMerchantId(transaction.getMerchant().getId());
        respTranMerchant.setFxTransactionId(transaction.getFxTransaction().getFxTransactionId());
        respTranMerchant.setAcquirerTransactionId(transaction.getAcquirerTransaction().getAcquirerTransactionId());
        respTranMerchant.setChainId(transaction.getChainId());
        respTranMerchant.setAgentInfoId(transaction.getAgent().getId());
        respTranMerchant.setReturnUrl(transaction.getReturnUrl());
        respTranMerchant.setStatus(transaction.getStatus());
        respTranMerchant.setOperation(transaction.getOperation());
        respTranMerchant.setCreatedAt(transaction.getCreatedAt());
        respTranMerchant.setModifiedAt(transaction.getModifiedAt());
        respTranMerchant.setDeletedAt(transaction.getDeletedAt());
        respTranMerchant.setCode(transaction.getCode());
        respTranMerchant.setMessage(transaction.getMessage());
        respTranMerchant.setChannel(transaction.getChannel());
        respTranMerchant.setCustomData(transaction.getCustomData());
        respTranMerchant.setParentId(null);
        respTranMerchant.setType(transaction.getType());
        respTranMerchant.setTransactionId(transaction.getTransactionId());

        TransactionQueryResponse.Transaction.TransactionMerchant.Agent respTranMerchantAgent =
                new TransactionQueryResponse.Transaction.TransactionMerchant.Agent();
        respTranMerchantAgent.setId(transaction.getAgent().getId());
        respTranMerchantAgent.setCustomerIp(transaction.getAgent().getCustomerIp());
        respTranMerchantAgent.setCustomerUserAgent(transaction.getAgent().getCustomerUserAgent());
        respTranMerchantAgent.setMerchantIp(transaction.getAgent().getMerchantIp());
        respTranMerchantAgent.setMerchantUserAgent(transaction.getAgent().getMerchantUserAgent());
        respTranMerchantAgent.setCreatedAt(transaction.getAgent().getCreatedAt());
        respTranMerchantAgent.setModifiedAt(transaction.getAgent().getModifiedAt());
        respTranMerchantAgent.setDeletedAt(transaction.getAgent().getDeletedAt());

        respTranMerchant.setAgent(respTranMerchantAgent);
        respTran.setTransactionMerchant(respTranMerchant);
        tqr.setTransaction(respTran);

        TransactionQueryResponse.Merchant respMerchant = new TransactionQueryResponse.Merchant();
        respMerchant.setName(transaction.getMerchant().getName());
        tqr.setMerchant(respMerchant);

        return Optional.of(tqr);
    }

    public static Optional<CustomerInfoQueryResponse> convertToQueryCustomerInfoResponse(CustomerInfo customerInfo) {
        if (customerInfo == null) {
            return Optional.empty();
        }

        CustomerInfoQueryResponse ciqr = new CustomerInfoQueryResponse();
        CustomerInfoJson ci = convertToCustomerInfoJson(customerInfo);
        ciqr.setCustomerInfo(ci);

        return Optional.of(ciqr);
    }

    private static CustomerInfoJson convertToCustomerInfoJson(CustomerInfo customerInfo) {
        CustomerInfoJson ci = new CustomerInfoJson();
        ci.setNumber(customerInfo.getCustomerNumber());
        ci.setCreatedAt(customerInfo.getCreatedAt());
        ci.setModifiedAt(customerInfo.getModifiedAt());
        ci.setDeletedAt(customerInfo.getDeletedAt());
        ci.setExpiryMonth(customerInfo.getExpiryMonth());
        ci.setExpiryYear(customerInfo.getExpiryYear());
        ci.setStartMonth(customerInfo.getStartMonth());
        ci.setStartYear(customerInfo.getStartYear());
        ci.setIssueNumber(customerInfo.getIssueNumber());
        ci.setEmail(customerInfo.getEmail());
        ci.setBirthday(customerInfo.getBirthday());
        ci.setGender(customerInfo.getGender());
        ci.setBillingTitle(customerInfo.getBillingTitle());
        ci.setBillingFirstName(customerInfo.getBillingFirstName());
        ci.setBillingLastName(customerInfo.getBillingLastName());
        ci.setBillingCompany(customerInfo.getBillingCompany());
        ci.setBillingAddress1(customerInfo.getBillingAddress1());
        ci.setBillingAddress2(customerInfo.getBillingAddress2());
        ci.setBillingCity(customerInfo.getBillingCity());
        ci.setBillingPostcode(customerInfo.getBillingPostcode());
        ci.setBillingState(customerInfo.getBillingState());
        ci.setBillingCountry(customerInfo.getBillingCountry());
        ci.setBillingPhone(customerInfo.getBillingPhone());
        ci.setBillingFax(customerInfo.getBillingFax());
        ci.setShippingTitle(customerInfo.getShippingTitle());
        ci.setShippingFirstName(customerInfo.getShippingFirstName());
        ci.setShippingLastName(customerInfo.getShippingLastName());
        ci.setShippingCompany(customerInfo.getShippingCompany());
        ci.setShippingAddress1(customerInfo.getShippingAddress1());
        ci.setShippingAddress2(customerInfo.getShippingAddress2());
        ci.setShippingCity(customerInfo.getShippingCity());
        ci.setShippingPostcode(customerInfo.getShippingPostcode());
        ci.setShippingState(customerInfo.getShippingState());
        ci.setShippingCountry(customerInfo.getShippingCountry());
        ci.setShippingPhone(customerInfo.getShippingPhone());
        ci.setShippingFax(customerInfo.getShippingFax());
        ci.setToken(customerInfo.getToken());

        return ci;
    }
}
