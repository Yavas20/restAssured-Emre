package com.cydeo.apiReviewEU8;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ZipJsonPathTest extends ZipBase {

    /*
    Exercise with JsonPath Method
Given Accept application/json
And "city" path is ma/belmont
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And Server header is cloudflare
And Report-To header exists
And body should contains following information
   post codes are existing : "02178","02478"
   country  is United States
   state abbreviation is MA
   place name is Belmont
   state is Massachusetts
   latitudes are 42.4464,42.4128

    */

    @Test
    public void pathTest() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParams("state", "ma", "city", "Belmont")
                .when().get("/{state}/{city}");

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertEquals("cloudflare", response.header("Server"));
        assertTrue(response.headers().hasHeaderWithName("Report-To"));

        JsonPath jsonPath = response.jsonPath();
        assertEquals("United States", jsonPath.getString("country"));

        assertThat(response.path("places.\'post code\'"), hasItems("02178","02478"));
        assertEquals("[42.4464, 42.4128]", jsonPath.getString("places.latitude"));

        String expectedLatitude = "[42.4464]";
        String actualLatitude = jsonPath.getString("places.findAll{it.\'post code'== \"02178\"}.latitude");
        System.out.println(actualLatitude);

        assertEquals(expectedLatitude, actualLatitude);


    }
}