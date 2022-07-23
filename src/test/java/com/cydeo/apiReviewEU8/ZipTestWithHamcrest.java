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


public class ZipTestWithHamcrest extends ZipBase {

    @Test
    public void pathTest() {

        given().accept(ContentType.JSON)
                .and().pathParam("zip", 22031)
                .when().get("/{zip}")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json")
                .header("Server", is("cloudflare"))
                .header("Report-To", notNullValue())
                .body("country", is("United States"),
                        "'post code'", is("22031"), "places[0].state", is("Virginia"),
                        "'country abbreviation'", is("US"), "places[0].'place name'", is("Fairfax"),
                        "places[0].latitude", is("38.8604")).log().body();




    }
}