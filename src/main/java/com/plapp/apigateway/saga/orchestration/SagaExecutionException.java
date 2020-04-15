package com.plapp.apigateway.saga.orchestration;

public class SagaExecutionException extends Exception {
    public SagaExecutionException(String message) {
        super(message);
    }

    public SagaExecutionException(Throwable e) {
        super(e);
    }

    public SagaExecutionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
