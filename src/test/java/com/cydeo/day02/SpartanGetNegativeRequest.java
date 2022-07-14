package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanGetNegativeRequest {

    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://52.90.200.128:8000";

    }

    @Test
    public void Test1(){

        Response response = given().accept(ContentType.XML).when().get("/api/spartans/10");
        assertEquals(406, response.statusCode());
        assertEquals("application/xml;charset=UTF-8", response.contentType());

    }

}
