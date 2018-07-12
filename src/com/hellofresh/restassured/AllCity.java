package com.hellofresh.restassured;

import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AllCity {
	
	private RequestSpecification httpRequest;
	private Response response;
	private String responseBody;
	
	@Test(priority=0,description="Response of ALL")
	public void response( ){   
		// Base URL to the RESTful web service
		RestAssured.baseURI = "http://services.groupkt.com/country/get";
		
		//Sending Request for country US
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET, "/all");
		responseBody = response.getBody().asString();
		
		int statusCode = response.getStatusCode();
		String statusLine = response.getStatusLine();
		System.out.println("Response Body is =>  " + responseBody);
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
		Assert.assertEquals(statusLine, "HTTP/1.1 200 200", "Correct status line returned");
	}
	
	@Test(priority=1,description="validation of US,DE and GB country existance in response",dataProvider="CityDataProvider")
	public void CountryValidation(String City){   
		
		if(responseBody.contains("US")) {
		System.out.println("**********\n"+City+" exist in response body"+"\n**********");	
		Assert.assertEquals(responseBody.contains("US"), true, "Response body contains "+City);
		}else if(responseBody.contains("DE")) {
		System.out.println("**********\n"+City+" exist in response body"+"\n**********");
		Assert.assertEquals(responseBody.contains("DE"), true, "Response body contains "+City);
		}else if(responseBody.contains("GB")) {
		System.out.println("**********\n"+City+" exist in response body"+"\n**********");
		Assert.assertEquals(responseBody.contains("GB"), true, "Response body contains "+City);
		}
	}
	
	@DataProvider(name="CityDataProvider",parallel=false)
	public Object[][] dataProviderMethod(ITestNGMethod ts){
		 return new Object[][] {{"US"},{"DE"},{"GB"}};
	}

}
