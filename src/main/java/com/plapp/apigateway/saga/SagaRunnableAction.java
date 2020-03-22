package com.plapp.apigateway.saga;

import org.slf4j.LoggerFactory;

public interface SagaRunnableAction<T> {
    T run() throws Exception;

    default T wrappedRun() throws SagaExecutionException {
        try {
            return run();
        } catch (Exception e) {
            LoggerFactory.getLogger(SagaRunnableAction.class).error("Exception running saga transaction", e);
            throw new SagaExecutionException(e);
        }
    }
}
