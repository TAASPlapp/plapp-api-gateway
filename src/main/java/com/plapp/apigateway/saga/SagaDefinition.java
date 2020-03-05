package com.plapp.apigateway.saga;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SagaDefinition {
    LinkedList<SagaTransaction> transactions = new LinkedList<>();

    public void add(SagaTransaction transaction) {
        transactions.add(transaction);
    }
}