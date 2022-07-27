package com.cydeo.apiReviewEU8;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PetSwaggerTest {

    String url = "https://petstore.swagger.io/v2";

    @Test
    public void test(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .queryParam("status","available")
                .get(url+"/pet/findByStatus");

        response.prettyPrint();

        // status code
        Assertions.assertEquals(200,response.statusCode());
        // content type
        Assertions.assertEquals("application/json",response.contentType());

        // when id is = 611112 , the name should be "Alexis"
        JsonPath jsonPath = response.jsonPath();

        String actualName = jsonPath.getString("name[0]");

        System.out.println("actualName = " + actualName);

    }
}
/*
PI Testing  : Request and Response

        Mobile App + Webb App : Front End

        API + DB : Back-End

* Business Layer

RestAssured Library

Postman

Pet Store What would be possible scenarios?
    LifeCycle of PET API
    * See all animals (with their status) and inventory
    * Posting a new Pet
    * Searching for the pet that we posted
    * changing the status of the pet we post with PUT or PATCH request
    * Deleting the pet we posted

 */