package com.plapp.apigateway;

import com.plapp.apigateway.saga.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testSagaExecution() {
        SagaDefinitionBuilder builder = new SagaDefinitionBuilder();
        SagaDefinition sagaDefinition = builder
                .<Integer, Void>step()
                    .invoke((arg) -> { System.out.println("Eseguo azione 1"); return 1; })
                    .withCompensation((arg) -> {System.out.println("Rollback azione 1 " + arg);})
                .<Integer, Void>step()
                    .invoke((arg) -> { System.out.println("Eseguo azione 2"); return 2; })
                    .withCompensation((arg) -> {System.out.println("Rollback azione 2 " + arg);})
                .<Integer, Void>step()
                    .invoke((arg) -> { System.out.println("Eseguo azione 3"); return 3; })
                    .  withCompensation((arg) -> {System.out.println("Rollback azione 3 " + arg);})
                .<Integer, Void>step()
                    .invoke((arg) -> { System.out.println("Qua succede un casino"); throw new Exception("rompo tutto"); })
                .build();


        SagaExecutionEngine executor = new SagaExecutionEngine();
        try {
            executor.run(sagaDefinition);
        } catch (SagaExecutionException e){
            System.out.println(e);
        }
    }
}
