package com.plapp.apigateway.saga;

import java.util.LinkedList;

public class SagaDefinition {
    LinkedList<SagaTransaction> transactions = new LinkedList<>();

    public void add(SagaTransaction transaction) {
        transactions.add(transaction);
    }
}