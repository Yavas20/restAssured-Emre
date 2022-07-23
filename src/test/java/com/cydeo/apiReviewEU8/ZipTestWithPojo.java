package com.cydeo.apiReviewEU8;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class ZipTestWithPojo extends ZipBase {

    @Test
    public void pojoTest() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("zip", 22031)
                .when().get("/{zip}");

        PostCodePojo postCodePojo = response.as(PostCodePojo.class);
        System.out.println(postCodePojo.getPlaces());
        System.out.println("postCodePojo.getPlaces().get(0).getPlaceName() = "
                + postCodePojo.getPlaces().get(0).getPlaceName());

        System.out.println("postCodePojo.getPostCode() = " + postCodePojo.getPostCode());
        System.out.println("postCodePojo.getPlaces().get(0).getLongitude() = "
                + postCodePojo.getPlaces().get(0).getLongitude());

        assertEquals("Virginia", postCodePojo.getPlaces().get(0).getState());

    }
}
