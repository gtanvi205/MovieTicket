package com.movei.model;

import java.util.List;

public class TransactioRequest {

private int transactionId;
private List<CustomerDetails> customerDetails;

    public TransactioRequest(int transactionId, List<CustomerDetails> customers) {
        this.transactionId = transactionId;
        this.customerDetails = customers;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails) {
        this.customerDetails = customerDetails;
    }
}
