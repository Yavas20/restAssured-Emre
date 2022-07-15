package com.cydeo.day03;

import com.cydeo.utilities.HRTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrdsTestWithParameters extends HRTestBase {



    @DisplayName("Get request to /countries with Query param")
    @Test
    public void Test1() {

        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\": 2}")
                .when().get("/countries/");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("United States of America"));
        response.prettyPrint();

    }

    @DisplayName("Get request to /employees with Query param")
    @Test
    public void Test2() {

        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"IT_PROG\"}")
                .get("/employees");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("IT_PROG"));

        response.prettyPrint();

    }
}