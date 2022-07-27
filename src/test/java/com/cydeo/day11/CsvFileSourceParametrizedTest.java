package com.cydeo.day11;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvFileSourceParametrizedTest {

    // Write a parameterized test for this request
    // Get the data from csv source
    // GET https://api.zippopotam.us/us/{state}/{city}

    @ParameterizedTest
    @CsvFileSource(resources = "/postalcode.csv", numLinesToSkip = 1)
    public void testWithCsvFile(String state, String city, int zipCount){

        System.out.println("state = " + state);
        System.out.println("city = " + city);
        System.out.println("zipCount = " + zipCount);

        //send a request and verify place number matches with zipCount

        JsonPath jsonPath = given().accept(ContentType.JSON).pathParams("state", state, "city", city)
                .when().get("https://api.zippopotam.us/us/{state}/{city}").jsonPath();

        List<String> names = jsonPath.getList("places.'place name'");
        List<String> postCodes = jsonPath.getList("places.'post code'");
        assertThat(names.size(), is(zipCount));

        /*
        given()
                .baseUri("https://api.zippopotam.us")
                .accept(ContentType.JSON)
                .pathParam("state", stateArg)
                .pathParam("city", cityArg)
                .log().uri()
                .when()
            .get("/us/{state}/{city}")
            .then()
                .statusCode(200)
                .body("places",hasSize(zipCountArg));
         */

    }


}
