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

        public Object getObject(String name) throws SagaExecutionException {
            if (!arguments.containsKey(name))
                throw new SagaExecutionException("Could not resolve argument placeholder: " + name);

            return arguments.get(name);
        }

        public <T> T get(String name) throws SagaExecutionException {
            return (T)getObject(name);
        }

        public void update(Map<String, Object> args) {
            arguments.putAll(args);
        }
    }

    SagaArgumentResolver argumentResolver = new SagaArgumentResolver();
    SagaDefinition saga;

    public SagaExecutionEngine withSaga(SagaDefinition saga) {
        this.saga = saga;
        return this;
    }

    public SagaExecutionEngine withArgs(Map<String, Object> args) {
        argumentResolver.update(args);
        return this;
    }

    public SagaExecutionEngine withArg(String name, Object value) {
        argumentResolver.add(name, value);
        return this;
    }

    public SagaExecutionEngine run() throws SagaExecutionException {
        execute(saga.transactions.listIterator(), saga.transactions.listIterator());
        return this;
    }

    public SagaArgumentResolver collect() {
        return argumentResolver;
    }

    private void execute(ListIterator<SagaTransaction> commandsIterator,
                         ListIterator<SagaTransaction> rollbackIterator) throws SagaExecutionException {
        if (!commandsIterator.hasNext())
            return;

        try {
            SagaTransaction transaction = commandsIterator.next();
            System.out.println("Executing saga step " + transaction);
            transaction
                    .withArgumentResolver(argumentResolver)
                    .run();

            rollbackIterator.next();
            execute(commandsIterator, rollbackIterator);

        } catch (SagaExecutionException e){
            if (rollbackIterator.hasPrevious())
                rollbackIterator.previous().rollback();
            throw e;
        }
    }
}
