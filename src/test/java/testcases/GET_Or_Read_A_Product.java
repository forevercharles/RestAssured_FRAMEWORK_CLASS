package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GET_Or_Read_A_Product {
	SoftAssert softAssert = new SoftAssert(); //so softAssert is the reference object or just object.
	                                          //Term reference variable is valid when there is something that i've created out of interface.
	                                          //Here,softAssert is out of class. So its object or reference object 

	@Test
	public void read_A_Product() {
//		https://techfios.com/api-prod/api/product/read_one.php?id=2133
//		HashMap<String, String> queryParams = new HashMap<String, String>();
		Response response =
				given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.queryParam("id", "2133") //this is for single query parameter but if you have multiple then you can use queryParams below
//					.queryParams(null)
				.when()
					.get("/read_one.php")
				.then().extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status code: " + statusCode);
		Assert.assertEquals(statusCode, 200); //This is hard assert
//		System.out.println("Status code: " + statusCode);
//		softAssert.assertEquals(statusCode, 201, "Status code assertion is failing");
//		System.out.println("Status code: " + statusCode);


		
//		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
////	long expectedResponseTime = 
//		System.out.println("ActualResponseTime: " + actualResponseTime);
////		Assert.assertEquals(actualResponseTime, <=200);
//		if(actualResponseTime<=200) {
//			System.out.println("Response time is within range.");
//		}else {
//			System.out.println("Response time is out of range.");
//		}
		
//		response.getBody().prettyPrint();
		
		String responseBody = response.getBody().asString();
		System.out.println("Response body: " + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String productName = jp.getString("name");
		System.out.println("Product Name: " + productName);
		Assert.assertEquals(productName, "Iphone 13.0");
//		softAssert.assertEquals(productName, "Iphone 13.0");
//		System.out.println("Product Name: " + productName);

		
		String productDescription = jp.getString("description");
		System.out.println("productDescription: " + productDescription);
		Assert.assertEquals(productDescription, "best Iphone for cheap price."); // This is hard assert too
//     	softAssert.assertEquals(productDescription, "best Iphone for cheap price EVER", "ProductDescription assertion failed");
//		System.out.println("productDescription: " + productDescription);

		
		String productPrice = jp.getString("price");
		System.out.println("productPrice: " + productPrice);
		Assert.assertEquals(productPrice, "1294");
//		softAssert.assertEquals(productPrice, "1294");
//		System.out.println("productPrice: " + productPrice);

//		softAssert.assertAll(); //if you put this, then this will check if there are any assertions failing,
		                        //and will tell you exactly where the assertion is failing
		
	}
	
	
	
}
