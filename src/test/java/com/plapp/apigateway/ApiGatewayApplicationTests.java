package com.plapp.apigateway;

import com.plapp.apigateway.saga.orchestration.SagaDefinition;
import com.plapp.apigateway.saga.orchestration.SagaDefinitionBuilder;
import com.plapp.apigateway.saga.orchestration.SagaExecutionEngine;
import com.plapp.apigateway.saga.orchestration.SagaExecutionException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

    @RequiredArgsConstructor
    public static class Task {
        private final Integer id;

        public Integer execute(Integer arg) {
            System.out.println("Eseguo azione " + id + " con arg " + arg);
            return id;
        }

        public void rollback(Integer i) {
            System.out.println("Rollback azione: " + i);
        }
    }

    @Test
    void testSagaExecution() {
        Task task1 = new Task(1);
        Task task2 = new Task(2);
        Task task3 = new Task(3);

        SagaDefinitionBuilder builder = new SagaDefinitionBuilder();
        SagaDefinition sagaDefinition = builder
                    .invoke(task1::execute).withArg("param").saveTo("result1")
                    .withCompensation(task1::rollback)
                .step()
                    .invoke(task2::execute).withArg("result1").saveTo("result2")
                    //.withCompensation(task2::rollback)
                .step()
                    .invoke(task3::execute).withArg("result2").saveTo("result3")
                    . withCompensation(task3::rollback)
                .step()
                    .invoke((arg) -> { System.out.println("Qua succede un casino"); throw new SagaExecutionException("rompo tutto"); })
                .build();


        SagaExecutionEngine executor = new SagaExecutionEngine();
        try {
            executor
               .withSaga(sagaDefinition)
               .withArg("param", 1313)
               .run();
        } catch (Throwable e){
            System.out.println(e);
        }
    }
}
