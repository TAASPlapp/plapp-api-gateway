package com.plapp.apigateway.saga;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class SagaExecutionEngine {
    public static class SagaArgumentResolver {
        private Map<String, Object> arguments = new HashMap<>();

        public void add(String name, Object arg) {
            arguments.put(name, arg);
        }

        public Object get(String name) throws SagaExecutionException {
            if (!arguments.containsKey(name))
                throw new SagaExecutionException("Could not resolve argument placeholder: " + name);

            return arguments.get(name);
        }

        public void update(Map<String, Object> args) {
            arguments.putAll(args);
        }
    }

    SagaArgumentResolver argumentResolver = new SagaArgumentResolver();

    public SagaExecutionEngine withArgs(Map<String, Object> args) {
        argumentResolver.update(args);
        return this;
    }

    public SagaExecutionEngine withArg(String name, Object value) {
        argumentResolver.add(name, value);
        return this;
    }

    public void run(SagaDefinition saga) throws SagaExecutionException {
        execute(saga.transactions.listIterator(), saga.transactions.listIterator());
    }

    private void execute(ListIterator<SagaTransaction> commandsIterator,
                         ListIterator<SagaTransaction> rollbackIterator) throws SagaExecutionException {
        if (!commandsIterator.hasNext())
            return;

        try {
            SagaTransaction transaction = commandsIterator.next();
            transaction.setArgumentResolver(argumentResolver);
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
