package com.plapp.apigateway.saga;

public interface SagaTransaction {
    void run() throws SagaExecutionException;
    void rollback() throws SagaExecutionException;
}