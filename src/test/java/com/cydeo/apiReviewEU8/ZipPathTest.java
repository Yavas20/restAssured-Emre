package com.cydeo.apiReviewEU8;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class ZipPathTest extends ZipBase {

    @Test
    public void pathTest() {

       Response response = given().accept(ContentType.JSON)
                .and().pathParam("zip", 22031)
                .when().get("/{zip}");

        response.prettyPrint();
         /*
    Exercise with Path Method
Given Accept application/json
And path zipcode is 22031
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And Server header is cloudflare
And Report-To header exists
And body should contains following information
   post code is 22031
   country  is United States
   country abbreviation is US
   place name is Fairfax
   state is Virginia
   latitude is 38.8604
    */

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.header("Server").equalsIgnoreCase("cloudflare"));

        final Headers headers = response.getHeaders();
        for (Header header : headers) {
            System.out.println(header.getName());
        }

        assertTrue(response.headers().hasHeaderWithName("Report-To"));
        assertEquals("22031", response.path("\'post code\'"));
        assertEquals("United States", response.path("country"));
        assertEquals("US", response.path("\'country abbreviation\'"));
        assertEquals("Fairfax", response.path("places[0].\'place name\'"));
        assertEquals("Virginia", response.path("places[0].state"));
        assertEquals("38.8604", response.path("places.latitude[0]"));

        JsonPath jsonPathRorResponse = response.jsonPath();
        assertEquals("United States", jsonPathRorResponse.getString("country"));



    }
}