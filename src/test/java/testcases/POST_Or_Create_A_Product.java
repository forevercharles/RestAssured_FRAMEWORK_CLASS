package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class POST_Or_Create_A_Product {

	@Test
	public void create_A_Product() {
//		https://techfios.com/api-prod/api/product/create.php
		
		/*
		{
		    "name" : "Iphone 13.0",
		    "price" : "1394",
		    "description" : "The best Iphone for amazing programmers.",
		    "category_id" : 2
		}
		*/
		
		String payloadPath = ".\\src\\main\\java\\data\\payload.json";
		
//		HashMap<String, String> payload = new HashMap<String, String>();
//		payload.put("name", "Fundamentals for QA People");
//		payload.put("price", "149");
//		payload.put("description", "You have to buy this book!!!");
//		payload.put("category_id", "6");
		
		Response response =
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json;")
					.body(new File (payloadPath))
				.when()
					.log().all()
					.post("/create.php")
				.then()
					.log().all()   // prints out all the info for you to see
					.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status code: " + statusCode);
		Assert.assertEquals(statusCode, 201);
		
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS); 
		System.out.println("ActualResponseTime: " + actualResponseTime);		
//		Assert.assertEquals(actualResponseTime, <=200);
//		in the assertion we cannot write <=200 like that(above) so we do it in the if and else way below.		
		if(actualResponseTime<=2000) {
			System.out.println("Response time is within range.");
		}else {
			System.out.println("Response time is out of range.");
		}
		
//		response.getBody().prettyPrint();
		
		String responseBody = response.getBody().asString();
		System.out.println("Response body: " + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String successMessage = jp.getString("message");
		System.out.println("successMessage: " + successMessage);
		Assert.assertEquals(successMessage, "Product was created.");
				
		
	}
	
}
