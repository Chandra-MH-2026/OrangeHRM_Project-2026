package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtility {
	
	//Method to send the get request to the API and return the response
	public static Response sendGetRequest(String endpoint) {
		
		return RestAssured.given().header("x-api-key", "free_user_3F7DyR6lmXJIxYVQNGHwQ1xKlcT").get(endpoint);
	}
	
	//Method to send the post request to the API and return the response
	public static Response sendPostRequest(String endpoint, String requestBody) {
		
		return RestAssured.given().header("Content-Type", "application/json").body(requestBody).post(endpoint);
	}
	
	//Method to validate the response status code
	public static boolean validateStatusCode(Response response, int expectedStatusCode) {
		
		return response.getStatusCode() == expectedStatusCode;
	}
	
	//Method to extract the value from the response body using JSON path
	public static String extractValueFromResponse(Response response, String jsonPath)
	{
		
		return response.jsonPath().getString(jsonPath);
	}

}
