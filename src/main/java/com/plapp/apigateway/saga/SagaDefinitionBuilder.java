package com.plapp.apigateway.saga;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SagaDefinitionBuilder {
    private SagaDefinition saga = new SagaDefinition();

    public interface Collector<T> {
        T collect() throws SagaExecutionException;
    }

    @Setter
    @RequiredArgsConstructor
    public static class SagaTransactionImpl<R, T> implements SagaTransaction{
        @Setter(AccessLevel.PRIVATE)
        private SagaExecutionEngine.SagaArgumentResolver argumentResolver;

        private Collector<T> argumentCollector = () -> null;

        private R result;
        private String resultPlaceholder;

        private SagaRunnable<R, T> action;
        private SagaRunnableRollback<R> rollback = (a) -> {};
        private Logger logger = LoggerFactory.getLogger(SagaTransactionImpl.class);

        private final SagaDefinitionBuilder builder;

        @Override
        public SagaTransaction withArgumentResolver(SagaExecutionEngine.SagaArgumentResolver argumentResolver) {
            setArgumentResolver(argumentResolver);
            return this;
        }

        @Override
        public void run() throws SagaExecutionException {
           result = action.wrappedRun(argumentCollector.collect());

           if (resultPlaceholder != null)
               argumentResolver.add(resultPlaceholder, result);
        }

        @Override
        public void rollback() throws SagaExecutionException {
            rollback.wrappedRun(result);
        }

        public SagaTransactionImpl<R, T> withArg(String argument) {
            this.argumentCollector = () -> (T) argumentResolver.get(argument);
            return this;
        }

        public SagaTransactionImpl<R, T> saveTo(String resultPlaceholder) {
            this.resultPlaceholder = resultPlaceholder;
            return this;
        }

        public SagaTransactionImpl<R,T> withCompensation(SagaRunnableRollback<R> compensation) {
            this.rollback = compensation;
            return this;
        }

        public SagaDefinitionBuilder step() {
            return builder.addTransaction(this);
        }

        public SagaDefinition build() {
            return builder.addTransaction(this).build();
        }
    }

    public <R, T> SagaDefinitionBuilder addTransaction(SagaTransactionImpl<R, T> transaction) {
        saga.add(transaction);
        return this;
    }

    public <R,T> SagaTransactionImpl<R,T> invoke(SagaRunnable<R,T> action) {
        SagaTransactionImpl<R, T> transaction = new SagaTransactionImpl<>(this);
        transaction.setAction(action);
        return transaction;
    }

    public SagaDefinition build() {
        return saga;
    }
}
