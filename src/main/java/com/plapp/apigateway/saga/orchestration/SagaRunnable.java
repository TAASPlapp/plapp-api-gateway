package com.plapp.apigateway.saga.orchestration;

import org.slf4j.LoggerFactory;

public interface SagaRunnable<R, T> {
    R run(T arg) throws SagaExecutionException ;

    default R wrappedRun(T arg) throws SagaExecutionException {
        try {
            return run(arg);
        } catch (Exception e) {
            LoggerFactory.getLogger(SagaRunnable.class).error("Exception running saga transaction: "  + e.getMessage());
            throw new SagaExecutionException(e.getMessage(), e);
        }
    }
}
