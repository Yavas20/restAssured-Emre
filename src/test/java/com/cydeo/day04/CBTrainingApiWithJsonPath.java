package com.cydeo.day04;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class CBTrainingApiWithJsonPath {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://api.cybertektraining.com";

    }

    @DisplayName("GET Request to individual student")
    @Test
    public void test1(){
        //send a get request to student id 23401 as a path parameter and accept header application/json
        //verify status code=200 /content type=application/json;charset=UTF-8 /Content-Encoding = gzip
        //verify Date header exists
        //assert that
            /*
                firstName Vera
                batch 14
                section 12
                emailAddress aaa@gmail.com
                companyName Cybertek
                state IL
                zipCode 60606
                using JsonPath
             */

//send a get request to student id 32881 as a path parameter and accept header
// application/json
            Response response = given().accept(ContentType.JSON).pathParams("id", 32881)
                    .when()
                    .get("/student/{id}");

//verify status code=200 /content type=application/json;charset=UTF-8 /Content-Encoding = gzip
            assertEquals(200, response.statusCode() );
            assertEquals("application/json;charset=UTF-8",response.contentType());
            assertEquals("334",response.header("Content-Length"));
            assertEquals("gzip", response.header("Content-Encoding"));

//verify Date header exists
            assertTrue(response.headers().hasHeaderWithName("Date"));

//assert that
            /*
               firstName Vera
                batch 14
                section 12
                emailAddress aaa@gmail.com
                companyName Cybertek
                state IL
                zipCode 60606
                using JsonPath
             */
            assertEquals("Vera",response.jsonPath().getString("students[0].firstName"));
            assertEquals(14,response.jsonPath().getInt("students[0].batch"));
            assertEquals(12,response.jsonPath().getInt("students[0].section"));
            assertEquals("aaa@gmail.com", response.jsonPath().getString("students[0].contact.emailAddress"));
            assertEquals("Cybertek",response.jsonPath().getString("students[0].company.companyName"));
            assertEquals("IL",response.jsonPath().getString("students[0].company.address.state"));
            assertEquals(60606, response.jsonPath().getInt("students[0].company.address.zipCode"));

        }


    }

