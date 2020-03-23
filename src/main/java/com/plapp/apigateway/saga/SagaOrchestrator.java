package com.plapp.apigateway.saga;

public abstract class SagaOrchestrator {
    private SagaDefinition sagaDefinition;
    protected abstract SagaDefinition buildSaga(SagaDefinitionBuilder builder);

    protected SagaExecutionEngine getExecutor() {
        if (sagaDefinition == null)
            sagaDefinition = buildSaga(new SagaDefinitionBuilder());

        return new SagaExecutionEngine().withSaga(sagaDefinition);
    }
}
