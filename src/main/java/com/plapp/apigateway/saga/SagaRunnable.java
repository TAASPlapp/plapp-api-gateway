package com.plapp.apigateway.saga;

public interface SagaRunnable<R, T> {
    R run(T arg) throws SagaExecutionException ;

    default R wrappedRun(T arg) throws SagaExecutionException {
        try {
            return run(arg);
        } catch (SagaExecutionException e) {
            System.err.print("Exception running saga transaction");
            e.printStackTrace();
            throw new SagaExecutionException(e.getMessage(), e);
        }
    }
}
