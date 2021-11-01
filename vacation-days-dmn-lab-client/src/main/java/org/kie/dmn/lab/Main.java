package org.kie.dmn.lab;

import org.kie.api.builder.KieScannerFactoryService;
import org.kie.api.internal.weaver.KieWeaverService;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.credentials.EnteredCredentialsProvider;

/**
 * Vacation Days DMN Client
 */
public class Main {

    private static final String DECISION_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";

    private static final String CONTAINER_ID = "vacation-days-decisions-<unique-id>_1.0.0-SNAPSHOT";

    private static final String USERNAME = "******";

    private static final String PASSWORD = "******";

    public static void main(String[] args) {
        CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(USERNAME, PASSWORD);
        KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(DECISION_SERVER_URL, credentialsProvider);
        KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);

        DMNServicesClient dmnServicesClient = kieServicesClient.getServicesClient(DMNServicesClient.class);

        DMNContext dmnContext = dmnServicesClient.newContext();
        dmnContext.set("Age", 16);
        dmnContext.set("Years of Service", 1);

        ServiceResponse<DMNResult> dmnResultResponse = dmnServicesClient.evaluateAll(CONTAINER_ID, dmnContext);

        DMNDecisionResult decisionResult = dmnResultResponse.getResult().getDecisionResultByName("Total Vacation Days");
        System.out.println("Total vacation days: " + decisionResult.getResult());
    }
}
