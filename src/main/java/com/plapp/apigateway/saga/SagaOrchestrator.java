package com.plapp.apigateway.saga;

public abstract class SagaOrchestrator {
    private final SagaDefinition sagaDefinition;

    public SagaOrchestrator() {
        sagaDefinition = buildSaga(new SagaDefinitionBuilder());
    }

    public abstract SagaDefinition buildSaga(SagaDefinitionBuilder builder);

    protected SagaExecutionEngine getExecutor() {
        return new SagaExecutionEngine().withSaga(sagaDefinition);
    }
}
