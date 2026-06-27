package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.utilities.ApiUtility;
import com.orangehrm.utilities.ExtentManger;
import com.orangehrm.utilities.RetryAnalyzer;

import io.restassured.response.Response;

public class ApiTest {
	
	@Test()
	public void verifyGetUserAPI()
	{
		
		//Step 1: Define API endpoint
		String endPoint = "https://reqres.in/api/users/2";
		ExtentManger.logStep("Defined API endpoint: " + endPoint);
		
		//Step 2: Send GET request and capture response
		ExtentManger.logStep("Sending GET request to the API endpoint...");
		Response response = ApiUtility.sendGetRequest(endPoint);
		
		
		//Step 3: Validate response status code
		ExtentManger.logStep("Validating response status code...");
		int expectedStatusCode = 200;
		boolean isStatusCodeValid = ApiUtility.validateStatusCode(response, expectedStatusCode);
		Assert.assertTrue(isStatusCodeValid, "Expected status code: " + expectedStatusCode + " but got: " + response.getStatusCode());
		
		if(isStatusCodeValid) {
			ExtentManger.logStepValidationForAPI("Response status code is as expected: " + expectedStatusCode);
		}else {
			ExtentManger.logFailureForAPI("Expected status code: " + expectedStatusCode + " but got: " + response.getStatusCode());
		}
		
		//Step 4: Validate response body content i.e name of the user
		ExtentManger.logStep("Validating response body content i.e name of the user...");
		String expectedFirstName = "Janet";
		String actualFirstName = ApiUtility.extractValueFromResponse(response, "data.first_name");
		boolean isFirstNameValid = expectedFirstName.equals(actualFirstName);
		Assert.assertEquals(actualFirstName, expectedFirstName, "Expected first name: " + expectedFirstName + " but got: " + actualFirstName);
		
		if(isFirstNameValid) {
			ExtentManger.logStepValidationForAPI("Expected first name: " + expectedFirstName + " is equal to: " + actualFirstName);
		}else {
			ExtentManger.logFailureForAPI("Expected first name: " + expectedFirstName + " but got: " + actualFirstName);
		}
		
		//Step 4: Validate response body content i.e email of the user
				ExtentManger.logStep("Validating response body content i.e email of the user...");
				String expectedEmailId = "janet.weaver@reqres.in";
				String actualEmailId = ApiUtility.extractValueFromResponse(response, "data.email");
				boolean isEmailIdValid = expectedEmailId.equals(actualEmailId);
				Assert.assertEquals(actualEmailId, expectedEmailId, "Expected first name: " + expectedEmailId + " but got: " + actualEmailId);
				if(isEmailIdValid) {
					ExtentManger.logStepValidationForAPI("Expected first name: " + expectedFirstName + " is equal to: " + actualFirstName);
				}else {
					ExtentManger.logFailureForAPI("Expected first name: " + expectedFirstName + " but got: " + actualFirstName);
				}
		
	}

}
