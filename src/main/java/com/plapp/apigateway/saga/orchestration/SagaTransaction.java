package com.plapp.apigateway.saga.orchestration;

public interface SagaTransaction {
    SagaTransaction withArgumentResolver(SagaExecutionEngine.SagaArgumentResolver argumentResolver);
    void run() throws SagaExecutionException;
    void rollback() throws SagaExecutionException;
}