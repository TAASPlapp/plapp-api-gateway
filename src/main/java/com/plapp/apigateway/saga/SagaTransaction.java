package com.plapp.apigateway.saga;

public interface SagaTransaction {
    void setArgumentResolver(SagaExecutionEngine.SagaArgumentResolver argumentResolver);
    void run() throws SagaExecutionException;
    void rollback() throws SagaExecutionException;
}