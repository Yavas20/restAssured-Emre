package com.cydeo.day04;

import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSApiTestWithPath extends HRTestBase {

    @DisplayName("GET request to countries with Path method")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\": 2}")
                .when().get("/countries/");

        assertEquals(200, response.statusCode());
        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        String firstCountryId = response.path("items[0].country_id");
        System.out.println(firstCountryId);

        String secondCountryName = response.path("items[1].country_name");
        System.out.println(secondCountryName);

        System.out.println(response.path("items[2].links[0].href").toString());

        String thirdHref = response.path("items[2].links[0].href");
        System.out.println(thirdHref);

        List<String > countryNames = response.path("items.country_name");
        System.out.println(countryNames);

        List<Integer> regionIds = response.path("items.region_id");
        for (Integer regionId : regionIds) {
            System.out.println(regionId);
            assertEquals(2, regionId);
        }

    }

    @DisplayName("Get request to /employees with Query param")
    @Test
    public void Test2() {

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"IT_PROG\"}")
                .get("/employees");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        // assertTrue(response.body().asString().contains("IT_PROG"));

        List<String> allJodIDs = response.path("items.job_id");
        for (String allJodID : allJodIDs) {
            System.out.println(allJodID);
            assertEquals("IT_PROG", allJodID);

        }

    }


}
