package com.plapp.apigateway.saga;

import org.slf4j.LoggerFactory;

public interface SagaRunnableRollback<T> {
    void run(T arg) throws Exception;

    default void wrappedRun(T arg) throws SagaExecutionException {
        try {
            run(arg);
        } catch (Exception e) {
            LoggerFactory.getLogger(SagaRunnableRollback.class).error("Exception running saga transaction", e);
            throw new SagaExecutionException(e);
        }
    }
}
