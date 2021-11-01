package org.kie.dmn.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.credentials.EnteredCredentialsProvider;

/**
 * Call Centre DMN Client
 */
public class Main {

    private static final String DECISION_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";

    private static final String USERNAME = "******";

	private static final String PASSWORD = "******";
	
	private static final String DMN_MODEL_NAMESPACE = "https://kiegroup.org/dmn/_2E9DCCE2-8C2B-496E-AC37-103694E51940";//Your DMN Model Namespace would be different. So, open the DNM model in Business Central, select the DMN properties and copy the value from there. 
	
	private static final String DMN_MODEL_NAME = "call-centre";
	
	private static final String CONTAINER_ID = "call-centre-decision-<unique-id>_1.0.0-SNAPSHOT";
	
	
	public static void main(String[] args) {
		
		CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(USERNAME, PASSWORD);
		
		KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(DECISION_SERVER_URL, credentialsProvider);
		kieServicesConfig.setMarshallingFormat(MarshallingFormat.JSON);
		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
		
		// Get the DMN Services Client.
		DMNServicesClient dmnServicesClient = kieServicesClient.getServicesClient(DMNServicesClient.class);
		
		/*
		 *  Create a DMNContext and set the "Age" and "had previous incidents"
		 *  The "Age" should be set to 23, "had previous incidents" should be set to false.
		 */
		DMNContext dmnContext = dmnServicesClient.newContext();
		dmnContext.set("incoming call", getIncomingCall());
		dmnContext.set("employees", getEmployees());
		dmnContext.set("office", getOffice());
		
		// Retrieve the DMNResult by evaluating the DMN Model.
		ServiceResponse<DMNResult> dmnResultResponse = dmnServicesClient.evaluateDecisionByName(CONTAINER_ID, DMN_MODEL_NAMESPACE, DMN_MODEL_NAME, "Accept Call", dmnContext);
		
		// Retrieve the DMNDecisionResult "Insurance Total Price"
		DMNDecisionResult decisionResult = dmnResultResponse.getResult().getDecisionResultByName("Accept Call");
		
		// Print the result:
		System.out.println("Is the call accepted?: " + decisionResult.getResult());
	}
	
	private static Map<String, Object> getIncomingCall() {
	    Map<String, Object> incomingCall = new HashMap<>();
	    Map<String, Object> phone = new HashMap<>();
	    phone.put("country prefix", "+420");
	    phone.put("phone number", "1234");
	    incomingCall.put("phone", phone);
	    incomingCall.put("purpose", "help");
	    return incomingCall;
	}
	
	private static List<Map<String, Object>> getEmployees() {
	    List<Map<String,Object>> employees = new ArrayList<>();
	    Map<String, Object> employee = new HashMap<>();
	    employee.put("name", "Bala");
	    employee.put("office location", "Singapore");
	    employees.add(employee);
	    return employees;
	}
	
	private static Map<String, Object> getOffice() {
	    Map<String, Object> office = new HashMap<>();
	    office.put("location", "Singapore");
	    return office;
	}
	
}
