package com.hellofresh.restassured;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostMethod {
	
	@SuppressWarnings("unchecked")
	@Test(priority=0,description="Post Method",dataProvider="CityDataProvider")
	public void ValidCountryDetails(String City,String alphasTwoCode,String alphaThreeCode){   
		/*Assuming following is the URL for new country registration*/
		RestAssured.baseURI ="http://services.groupkt.com/country/get";
		RequestSpecification request = RestAssured.given();
	 
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", City);
		requestParams.put("alpha2_code", alphasTwoCode);
		requestParams.put("alpha3_code", alphaThreeCode);
		request.body(requestParams.toJSONString());
		Response response = request.post("/register");
	 
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, "201");
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
	}
	
	
	@DataProvider(name="CityDataProvider",parallel=true)
	public Object[][] dataProviderMethod(ITestNGMethod ts){
		return new Object[][] {{"Test Country One","TC","TCO"},{"Test Country Two","TD","TCT"},{"Test Country Three","TE","TCF"}};
	}

}
