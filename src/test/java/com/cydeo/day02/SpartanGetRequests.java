package com.cydeo.day02;

import groovy.lang.DelegatesTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpartanGetRequests {

    String baseUrl = "http://52.90.200.128:8000";

    @Test
    public  void  test1(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when()
                .get(baseUrl + "/api/spartans");

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.contentType() = " + response.contentType());
        response.prettyPrint();

        Assertions.assertEquals(response.statusCode(), 200);
        Assertions.assertEquals(response.contentType(), "application/json");


    }


    @DisplayName("Get one spartan api/spartans/3" )
    @Test
    public void test2(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when()
                .get(baseUrl + "/api/spartans/3");

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("application/json", response.contentType());
        Assertions.assertTrue(response.body().asString().contains("Fidole"));


    }

    @DisplayName("Get request to /api/hello")
    @Test
    public void test3(){

        Response response = RestAssured.when().get(baseUrl + "/api/hello");

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("text/plain;charset=UTF-8", response.contentType());
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

        System.out.println("response.header(\"Content-Length\") = " + response.header("Content-Length"));
        System.out.println("response.header(\"Date\") = " + response.header("Date"));

        Assertions.assertEquals("17", response.header("Content-Length"));
        Assertions.assertEquals("Hello from Sparta", response.body().asString());

    }




}
