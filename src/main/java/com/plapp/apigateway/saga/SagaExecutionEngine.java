package com.plapp.apigateway.saga;

import java.util.ListIterator;

public class SagaExecutionEngine {
    public void run(SagaDefinition saga) throws SagaExecutionException {
        execute(saga.transactions.listIterator(), saga.transactions.listIterator());
    }

    private void execute(ListIterator<SagaTransaction> commandsIterator,
                         ListIterator<SagaTransaction> rollbackIterator) throws SagaExecutionException {
        if (!commandsIterator.hasNext())
            return;

        try {
            SagaTransaction transaction = commandsIterator.next();
            transaction.run();

            rollbackIterator.next();
            execute(commandsIterator, rollbackIterator);

        } catch (SagaExecutionException e){
            if (rollbackIterator.hasPrevious())
                rollbackIterator.previous().rollback();
            throw e;
        }
    }
}
