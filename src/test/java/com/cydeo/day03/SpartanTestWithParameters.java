package com.cydeo.day03;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanTestWithParameters {

    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://52.90.200.128:8000";

    }

    @DisplayName("Get request to /api/spartans/{id}, id with 5")
    @Test
    public void Test1(){

        Response response = given(). accept(ContentType.JSON) .and()
                .pathParam("id", 5).when() .get("/api/spartans/{id}");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Blythe"));

    }

    @DisplayName("Get request to /api/spartans/{id}, id with 500")
    @Test
    public void Test2(){

        Response response = given().accept(ContentType.JSON).and().pathParam("id", 500)
                .when().get("/api/spartans/{id}");

        assertEquals(404, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Not Found"));

    }

    @DisplayName("Get request to /api/spartans/search, search with Query Param")
    @Test
    public void Test3(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .queryParam("gender", "Female").get("/api/spartans/search");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));

    }

    @DisplayName("Get request to /api/spartans/search, search with Query Params")
    @Test
    public void Test4(){

        Map<String, Object > queryParams = new HashMap<>();
        queryParams.put("gender", "Female");
        queryParams.put("nameContains", "e");

        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParams(queryParams).get("/api/spartans/search");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));

    }




}
