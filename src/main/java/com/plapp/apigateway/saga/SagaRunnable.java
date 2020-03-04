package com.plapp.apigateway.saga;

public interface SagaRunnable<R, T> {
    R run(T arg) throws Exception ;

    default R wrappedRun(T arg) throws SagaExecutionException {
        try {
            return run(arg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SagaExecutionException("Exception running saga transaction " + this.getClass() + ": " + e.getMessage(), e);
        }
    }
}
