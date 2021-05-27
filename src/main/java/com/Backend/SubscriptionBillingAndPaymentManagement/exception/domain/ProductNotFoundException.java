package com.Backend.SubscriptionBillingAndPaymentManagement.exception.domain;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }
}
