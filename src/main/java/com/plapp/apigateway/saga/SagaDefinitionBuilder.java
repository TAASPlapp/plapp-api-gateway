package com.plapp.apigateway.saga;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SagaDefinitionBuilder {
    private SagaDefinition saga = new SagaDefinition();

    @Setter
    @RequiredArgsConstructor
    private static class SagaTransactionImpl<R, T> implements SagaTransaction{
        private T arg;
        private R result;
        private SagaRunnable<R, T> action;
        private SagaRunnableRollback<R> rollback;
        private Logger logger = LoggerFactory.getLogger(SagaTransactionImpl.class);

        private final SagaDefinitionBuilder builder;

        @Override
        public void run() throws SagaExecutionException {
           result = action.wrappedRun(arg);
        }

        @Override
        public void rollback() throws SagaExecutionException {
            rollback.wrappedRun(result);
        }

        public SagaTransactionImpl<R,T> invoke(SagaRunnable<R,T> action) {
            this.action = action;
            return this;
        }

        public SagaTransactionImpl<R, T> withArg(T arg) {
            this.arg = arg;
            return this;
        }

        public SagaTransactionImpl<R,T> withCompensation(SagaRunnableRollback<R> compensation) {
            this.rollback = compensation;
            return this;
        }

        public <R2, T2> SagaTransactionImpl<R2, T2> step() {
            return builder.addTransaction(this).step();
        }

        public SagaDefinition build() {
            return builder.addTransaction(this).build();
        }
    }

    public <R, T> SagaDefinitionBuilder addTransaction(SagaTransactionImpl<R, T> transaction) {
        saga.add(transaction);
        return this;
    }

    public <R, T> SagaTransactionImpl<R,T> step() {
        return new SagaTransactionImpl<>(this);
    }

    public SagaDefinition build() {
        return saga;
    }
}
