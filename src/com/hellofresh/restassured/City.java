package com.hellofresh.restassured;

import org.apache.http.StatusLine;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class City {
	
	@Test(priority=0,description="validation of US,DE and GB",dataProvider="CityDataProvider",groups="A")
	public void ValidCountryDetails(String City){   
		// Base URL to the RESTful web service
		RestAssured.baseURI = "http://services.groupkt.com/country/get/iso2code";
		
		//Sending Request for country US
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/"+City);
		
		//Receiving response from Server and validating response-body
		String responseBody = response.getBody().asString();
		int statusCode = response.getStatusCode();
		String statusLine = response.getStatusLine();
		
		System.out.println("Response Body is =>  " + responseBody);
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
		Assert.assertEquals(statusLine, "HTTP/1.1 200 200", "Correct status line returned");
	}
	
	@Test(priority=1,description="validation of invalid country codes LL,us,FFG",dataProvider="CityDataProvider",groups="B")
	public void InValidCountryDetails(String City){   
		// Base URL to the RESTful web service
		RestAssured.baseURI = "http://services.groupkt.com/country/get/iso2code";
		
		//Sending Request for country US
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/"+City);
		
		//Receiving response from Server and validating response-body
		String responseBody = response.getBody().asString();
	    System.out.println("*********\n"+"<<<<<<<<"+City+">>>>>>>"+responseBody+"*********\n");
	    Assert.fail(City+" is not a valid country code");

	}
	
	@DataProvider(name="CityDataProvider",parallel=true)
	public Object[][] dataProviderMethod(ITestNGMethod ts){
		Object[][] cityArray = null;
		for(String str:ts.getGroups()) {
			if(str.equalsIgnoreCase("A")) {
				cityArray = new Object[][] {{"US"},{"DE"},{"GB"}};
				break;
			}else if(str.equalsIgnoreCase("B")) {
				cityArray = new Object[][] {{"LL"},{"us"},{"FFG"}};
				break;
			}
		}
		return cityArray;
	}

}
