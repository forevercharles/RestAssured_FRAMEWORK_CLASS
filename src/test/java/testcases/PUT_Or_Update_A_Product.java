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

public class PUT_Or_Update_A_Product {

	@Test
	public void update_A_Product() {
//		String payloadPath = ".\\src\\main\\java\\data\\payload.json";
//		String payloadPath = ".//src//main//java//data//payload.json";
		String payloadPath = "src/main/java/data/updatePayload_2146.json";
		
//		https://techfios.com/api-prod/api/product/update.php
		
		Response response =
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json;")
					.body(new File (payloadPath))
				.when()
					.log().all()
					.put("/update.php")
				.then()
					.log().all()   // prints out all the info for you to see
					.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status code: " + statusCode);
		Assert.assertEquals(statusCode, 200);
		
		
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
		Assert.assertEquals(successMessage, "Product was updated.");
				
		
	}
	
}
