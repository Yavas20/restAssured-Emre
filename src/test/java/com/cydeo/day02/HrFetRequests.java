package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class HrFetRequests {

    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://52.90.200.128:1000/ords/hr";

    }

    @DisplayName("Get request to /regions")
    @Test
    public void Test1(){

        Response response = get("/regions");
        System.out.println(response.statusCode());

    }
    @DisplayName("Get request to /regions/2")
    @Test
    public void Test2(){

        Response response = given().accept(ContentType.JSON).when().get("/regions/2");

       assertEquals(200, response.statusCode() );

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Americas"));

    }






}
